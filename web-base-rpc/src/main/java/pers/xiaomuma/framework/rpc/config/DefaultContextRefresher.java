package pers.xiaomuma.framework.rpc.config;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.cloud.bootstrap.BootstrapApplicationListener;
import org.springframework.cloud.context.refresh.ContextRefresher;
import org.springframework.cloud.context.scope.refresh.RefreshScope;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.*;
import org.springframework.web.context.support.StandardServletEnvironment;
import pers.xiaomuma.framework.core.startup.BaseApplicationContextInitializer;
import java.util.*;


public class DefaultContextRefresher extends ContextRefresher {

    private static final String REFRESH_ARGS_PROPERTY_SOURCE = "refreshArgs";

    private Set<String> standardSources = new HashSet<>(
            Arrays.asList(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME,
                    StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME,
                    StandardServletEnvironment.JNDI_PROPERTY_SOURCE_NAME,
                    StandardServletEnvironment.SERVLET_CONFIG_PROPERTY_SOURCE_NAME,
                    StandardServletEnvironment.SERVLET_CONTEXT_PROPERTY_SOURCE_NAME));

    private ConfigurableApplicationContext context;
    private RefreshScope scope;

    public DefaultContextRefresher(ConfigurableApplicationContext context, RefreshScope scope) {
        super(context, scope);
        this.context = context;
        this.scope = scope;
    }

    @Override
    public synchronized Set<String> refresh() {
        Map<String, Object> before = extract(
                this.context.getEnvironment().getPropertySources());
        addConfigFilesToEnvironment();
        Set<String> keys = changes(before,
                extract(this.context.getEnvironment().getPropertySources())).keySet();
        //不发送EnvironmentChangeEvent事件, 避免ConfigurationPropertiesRebinder.rebind方法调用
//		this.context.publishEvent(new EnvironmentChangeEvent(keys));
        keys.remove(BaseApplicationContextInitializer.TEMP_CONTEXT_NAME);
        this.scope.refreshAll();
        return keys;
    }

    private void addConfigFilesToEnvironment() {
        ConfigurableApplicationContext capture = null;
        try {
            StandardEnvironment environment = copyEnvironment(this.context.getEnvironment());
            SpringApplicationBuilder builder = new SpringApplicationBuilder(Empty.class)
                    .bannerMode(Banner.Mode.OFF).web(WebApplicationType.NONE).properties(BaseApplicationContextInitializer.TEMP_CONTEXT_NAME + "=true").environment(environment);
            // Just the listeners that affect the environment (e.g. excluding logging
            // listener because it has side effects)
            builder.application()
                    .setListeners(Arrays.asList(new BootstrapApplicationListener(),
                            new ConfigFileApplicationListener()));
            capture = builder.run();
            if (environment.getPropertySources().contains(REFRESH_ARGS_PROPERTY_SOURCE)) {
                environment.getPropertySources().remove(REFRESH_ARGS_PROPERTY_SOURCE);
            }
            MutablePropertySources target = this.context.getEnvironment()
                    .getPropertySources();
            String targetName = null;
            for (PropertySource<?> source : environment.getPropertySources()) {
                String name = source.getName();
                if (target.contains(name)) {
                    targetName = name;
                }
                if (!this.standardSources.contains(name)) {
                    if (target.contains(name)) {
                        target.replace(name, source);
                    }
                    else {
                        if (targetName != null) {
                            target.addAfter(targetName, source);
                        }
                        else {
                            if (target.contains("defaultProperties")) {
                                target.addBefore("defaultProperties", source);
                            }
                            else {
                                target.addLast(source);
                            }
                        }
                    }
                }
            }
        }
        finally {
            ConfigurableApplicationContext closeable = capture;
            // refer to https://github.com/spring-cloud/spring-cloud-commons/commit/abe5374c792094826bc7c96d36d44473757f4c91
            while (closeable != null) {
                try {
                    closeable.close();
                }
                catch (Exception e) {
                    // Ignore;
                }
                if (closeable.getParent() instanceof ConfigurableApplicationContext) {
                    closeable = (ConfigurableApplicationContext) closeable.getParent();
                }
                else {
                    break;
                }
            }

        }

    }

    // Don't use ConfigurableEnvironment.merge() in case there are clashes with property
    // source names
    private StandardEnvironment copyEnvironment(ConfigurableEnvironment input) {
        StandardEnvironment environment = new StandardEnvironment();
        MutablePropertySources capturedPropertySources = environment.getPropertySources();
        for (PropertySource<?> source : capturedPropertySources) {
            capturedPropertySources.remove(source.getName());
        }
        for (PropertySource<?> source : input.getPropertySources()) {
            capturedPropertySources.addLast(source);
        }
        environment.setActiveProfiles(input.getActiveProfiles());
        environment.setDefaultProfiles(input.getDefaultProfiles());
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("spring.jmx.enabled", false);
        map.put("spring.main.sources", "");
        capturedPropertySources
                .addFirst(new MapPropertySource(REFRESH_ARGS_PROPERTY_SOURCE, map));
        return environment;
    }

    private Map<String, Object> changes(Map<String, Object> before,
                                        Map<String, Object> after) {
        Map<String, Object> result = new HashMap<String, Object>();
        for (String key : before.keySet()) {
            if (!after.containsKey(key)) {
                result.put(key, null);
            }
            else if (!equal(before.get(key), after.get(key))) {
                result.put(key, after.get(key));
            }
        }
        for (String key : after.keySet()) {
            if (!before.containsKey(key)) {
                result.put(key, after.get(key));
            }
        }
        return result;
    }

    private boolean equal(Object one, Object two) {
        if (one == null && two == null) {
            return true;
        }
        if (one == null || two == null) {
            return false;
        }
        return one.equals(two);
    }

    private Map<String, Object> extract(MutablePropertySources propertySources) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<PropertySource<?>> sources = new ArrayList<PropertySource<?>>();
        for (PropertySource<?> source : propertySources) {
            sources.add(0, source);
        }
        for (PropertySource<?> source : sources) {
            if (!this.standardSources.contains(source.getName())) {
                extract(source, result);
            }
        }
        return result;
    }

    private void extract(PropertySource<?> parent, Map<String, Object> result) {
        if (parent instanceof CompositePropertySource) {
            try {
                List<PropertySource<?>> sources = new ArrayList<PropertySource<?>>();
                for (PropertySource<?> source : ((CompositePropertySource) parent)
                        .getPropertySources()) {
                    sources.add(0, source);
                }
                for (PropertySource<?> source : sources) {
                    extract(source, result);
                }
            } catch (Exception e) {
                return;
            }
        } else if (parent instanceof EnumerablePropertySource) {
            for (String key : ((EnumerablePropertySource<?>) parent).getPropertyNames()) {
                result.put(key, parent.getProperty(key));
            }
        }
    }

    @Configuration
    protected static class Empty {

    }

}
