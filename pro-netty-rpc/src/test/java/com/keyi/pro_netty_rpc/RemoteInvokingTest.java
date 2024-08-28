//package com.keyi.pro_netty_rpc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import com.keyi.netty.annotation.RemoteInvoke;
//import com.keyi.netty.client.ClientRequest;
//import com.keyi.netty.client.TcpClient;
//import com.keyi.netty.util.Response;
//import com.keyi.user.bean.User;
//import com.keyi.user.remote.UserRemote;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = RemoteInvokingTest.class)
//@ComponentScan("com.keyi")
//public class RemoteInvokingTest {
//
//	@RemoteInvoke
//	private UserRemote userRemote;
//
//	@Test
//	public void testSaveUser() {
//		User u = new User();
//		u.setId(1);
//		u.setName("张三");
//		userRemote.saveUser(u);
//	}
//	@Test
//	public void testSaveUsers() {
//		List<User> users = new ArrayList<User>();
//		User u = new User();
//		u.setId(1);
//		u.setName("张三");
//		users.add(u);
//		userRemote.saveUsers(users);
//	}
//
//	@Test
//	public void testGetServerUser() {
//		ClientRequest request = new ClientRequest();
//		User u = new User();
//		u.setId(1);
//		u.setName("张三");
//		request.setCommand("com.keyi.user.controller.UserController.saveUser");
//		request.setContent(u);
//		Response response = TcpClient.send(request);
//		System.out.println(response.getResult());
//
//	}
//
//	@Test
//	public void testGetServerUsers() {
//		ClientRequest request = new ClientRequest();
//		List<User> users = new ArrayList<User>();
//		User u = new User();
//		u.setId(1);
//		u.setName("张三");
//		users.add(u);
//		request.setCommand("com.keyi.user.controller.UserController.saveUsers");
//		request.setContent(users);
//		Response response = TcpClient.send(request);
//		System.out.println(response.getResult());
//
//	}
//
//}
