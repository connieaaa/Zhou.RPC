package com.keyi.netty.medium;

import java.lang.reflect.Method;

public class BeanMethod {

	private Object bean;
	private Method method;

	public Object getBean() {
		return bean;
	}

	public BeanMethod() {
	}

	public BeanMethod(Object bean, Method method) {
		super();
		this.bean = bean;
		this.method = method;
	}

	public void setBean(Object bean) {
		this.bean = bean;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

}
