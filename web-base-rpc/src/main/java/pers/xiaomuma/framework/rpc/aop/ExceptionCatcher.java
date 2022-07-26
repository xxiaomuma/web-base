package pers.xiaomuma.framework.rpc.aop;

import java.lang.annotation.*;

/**
 * 异常捕获器
 * 捕获某个方法内产生的异常
 * Warn: 不能用于同一个类两个方法之间的调用
 * 如 Class A的 method1() 调用 Class A的 method2()
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ExceptionCatcher {

	/**
	 * 捕获的异常，这里捕获的异常不仅包括异常本身
	 * 也包含继承自身的子类 (subclass)
	 */
	Class<? extends Throwable>[] catchFor() default {};

	CatchType catchType() default CatchType.RETHROW;

	Class<? extends Throwable> throwing() default RuntimeException.class;

	/**
	 * 向外抛出ServiceException的异常信息
	 * 被全局ExceptionTranslator捕获后将显示给用户
	 */
	String message() default "";

}
