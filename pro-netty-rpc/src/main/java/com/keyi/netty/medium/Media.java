package com.keyi.netty.medium;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.keyi.netty.handler.param.ServerRequest;
import com.keyi.netty.util.Response;

public class Media {

	public static Map<String, BeanMethod> beanMap;

	static {
		beanMap = new HashMap<String, BeanMethod>();
	}

	private static Media media = null;

	public static Media newInstance() {
		if (media == null) {
			media = new Media();
		}
		return media;
	}

	// 非常关键
	// 反射处理业务
	public Response process(ServerRequest request) {
		Response result = null;
		try {
			String command = request.getCommand();
			BeanMethod beanMethod = beanMap.get(command);
			if (beanMethod == null) {
				return null;
			}
			Object bean = beanMethod.getBean();
			Method m = beanMethod.getMethod();
			Class<?> paramType = m.getParameterTypes()[0];
			Object content = request.getContent();
			Object args = JSONObject.parseObject(JSONObject.toJSONString(content), paramType);

			result = (Response)m.invoke(bean, args);
			result.setId(request.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

}
