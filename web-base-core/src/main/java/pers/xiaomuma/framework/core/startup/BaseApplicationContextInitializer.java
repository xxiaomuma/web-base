package pers.xiaomuma.framework.core.startup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertiesPropertySource;
import pers.xiaomuma.framework.core.global.ApplicationContextHolder;
import java.util.Map;
import java.util.Properties;


public class BaseApplicationContextInitializer implements SpringApplicationRunListener {

    private final Logger logger  = LoggerFactory.getLogger(BaseApplicationContextInitializer.class);
    //刷新配置的时候, spring cloud会构造context加载配置, 需要给这种临时的context加个配置属性
    //来判断当前context到底是不是临时的context
    public static final String TEMP_CONTEXT_NAME = "CONFIG_CONTEXT";
    private String[] args;
    private SpringApplication application;

    public BaseApplicationContextInitializer(SpringApplication application, String[] args) {
        this.application = application;
        this.args = args;
    }

    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        MutablePropertySources m = environment.getPropertySources();
        Properties p = new Properties();
        // 允许覆盖原有的bean
        p.put("spring.main.allow-bean-definition-overriding", "true");
        m.addFirst(new PropertiesPropertySource("baseProperties", p));//d
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        if (context.getParent() != null) {
            logger.info("BaseApplicationContextInitializer is initializing, and application context now is the root of the context hierarchy ... ");
            //刷新配置的时候, spring cloud会构造context加载配置, 需要把这种临时的context过滤掉
            String configContext = context.getEnvironment().getProperty(TEMP_CONTEXT_NAME);
            if(configContext == null || !configContext.equalsIgnoreCase("true")) {
                context.getBeanFactory().registerSingleton("applicationContextHolder",
                        new ApplicationContextHolder(context));
            }
        }
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        if (context.getParent() == null) {
            return;
        }
        boolean doStartup = false;
        Map<String, BaseApplicationInitializer> initializerMap = null;

        try {
            if(StartupStatusHolder.tryStartup()) {
                doStartup =  true;
                logger.info("BaseApplicationInitializer开始执行 ...");
                initializerMap =
                        ApplicationContextHolder.getApplicationContext().getBeansOfType(
                                BaseApplicationInitializer.class, true, false);
            }
        } catch (Exception e) {
            logger.error("", e);
        }

        if(doStartup && initializerMap != null && !initializerMap.isEmpty()) {
            initializerMap.values().forEach(BaseApplicationInitializer::init);
        }
    }

    @Override
    public void running(ConfigurableApplicationContext applicationContext) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
