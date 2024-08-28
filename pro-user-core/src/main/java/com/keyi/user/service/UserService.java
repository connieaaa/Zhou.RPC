package com.keyi.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.keyi.user.model.User;


@Service
public class UserService {


	public void save(User user) {
		// TODO Auto-generated method stub
		// 访问mysql
		System.out.println("访问数据库");
	}

	public void saveList(List<User> users) {
		// TODO Auto-generated method stub
		
	}

}
