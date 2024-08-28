//package com.keyi.pro_netty_rpc;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//
//import com.keyi.netty.client.ClientRequest;
//import com.keyi.netty.client.TcpClient;
//import com.keyi.netty.util.Response;
//import com.keyi.user.bean.User;
//
//public class TestTcp {
//	@Test
//	public void testGetResponse() {
//		ClientRequest request = new ClientRequest();
//		request.setContent("测试tcp长连接");
//		Response response = TcpClient.send(request);
//		System.out.println(response.getResult());
//		
////		ClientRequest request2 = new ClientRequest();
////		request2.setContent("测试tcp长连接2");
////		Response response2 = TcpClient.send(request2);
////		System.out.println(response2.getResult());
//		
//	}
//	
//	@Test
//	public void testGetServerUser() {
//		ClientRequest request = new ClientRequest();
//		User u= new User();
//		u.setId(1);
//		u.setName("张三");
//		request.setCommand("com.keyi.user.controller.UserController.saveUser");
//	    request.setContent(u);
//		Response response = TcpClient.send(request);
//		System.out.println(response.getResult());
//		
//	}
//	
//	@Test
//	public void testGetServerUsers() {
//		ClientRequest request = new ClientRequest();
//		List<User> users = new ArrayList<User>();
//		User u= new User();
//		u.setId(1);
//		u.setName("张三");
//		users.add(u);
//		request.setCommand("com.keyi.user.controller.UserController.saveUsers");
//	    request.setContent(users);
//		Response response = TcpClient.send(request);
//		System.out.println(response.getResult());
//		
//	}
//
//}
