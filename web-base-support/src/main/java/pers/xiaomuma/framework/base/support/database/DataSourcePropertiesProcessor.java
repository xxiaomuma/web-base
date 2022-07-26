package pers.xiaomuma.framework.base.support.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pers.xiaomuma.framework.exception.PropertyBindingFailureException;

@Component
public class DataSourcePropertiesProcessor implements BeanFactoryAware,
		EnvironmentAware, InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(DataSourcePropertiesProcessor.class);

	private ConversionService conversionService;

	private BeanFactory beanFactory;

	private Environment environment;

	public void postProcessBeforeInitialization(Object bean, String prefix) {
		Object target = bean;

		Binder binder = Binder.get(environment);

		Bindable<?> bindAble = Bindable.ofInstance(target);

		BindResult<?> bindResult = binder.bind(prefix, bindAble);

		if (!bindResult.isBound()) {
			throw new PropertyBindingFailureException(prefix);
		}
	}

	@Override
	public void afterPropertiesSet() {
		if (this.conversionService == null) {
			this.conversionService = getOptionalBean(
					ConfigurableApplicationContext.CONVERSION_SERVICE_BEAN_NAME,
					ConversionService.class);
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	private <T> T getOptionalBean(String name, Class<T> type) {
		try {
			return this.beanFactory.getBean(name, type);
		} catch (NoSuchBeanDefinitionException ex) {
			return null;
		}
	}




}
