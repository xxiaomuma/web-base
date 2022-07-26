package pers.xiaomuma.framework.exception;

public class PropertyBindingFailureException extends RuntimeException {

	private String propertyName;

	public PropertyBindingFailureException(String propertyName) {
		super("绑定属性失败, 属性名: " + propertyName + " , 请检查配置");
		this.propertyName =  propertyName;
	}

}
