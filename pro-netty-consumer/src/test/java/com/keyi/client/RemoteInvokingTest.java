package com.keyi.client;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.keyi.client.annotation.RemoteInvoke;
import com.keyi.client.core.TcpClient;
import com.keyi.client.param.ClientRequest;
import com.keyi.client.param.Response;
import com.keyi.user.bean.User;
import com.keyi.user.remote.UserRemote;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RemoteInvokingTest.class)
@ComponentScan("com.keyi")
public class RemoteInvokingTest {

	@RemoteInvoke
	private UserRemote userRemote;

	@Test
	public void testSaveUser() {
		User u = new User();
		u.setId(1);
		u.setName("张三");
		Response response = userRemote.saveUser(u);
		System.out.println(JSONObject.toJSONString(response));
	}

	@Test
	public void testSaveUsers() {
		List<User> users = new ArrayList<User>();
		User u = new User();
		u.setId(1);
		u.setName("张三");
		users.add(u);
		userRemote.saveUsers(users);
	}

}
