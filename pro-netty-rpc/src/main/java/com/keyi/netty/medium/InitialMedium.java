package com.keyi.netty.medium;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import com.keyi.netty.annotation.Remote;

@Component
public class InitialMedium implements BeanPostProcessor {

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		if (bean.getClass().isAnnotationPresent(Remote.class)) {
			Method[] methods = bean.getClass().getDeclaredMethods();
			for (Method m : methods) {
				String key = bean.getClass().getInterfaces()[0].getName() + "." + m.getName();
				Map<String, BeanMethod> beanMap = Media.beanMap;
				beanMap.put(key, new BeanMethod(bean, m));
			}
		}
		return bean;
	}

}
