package com.keyi.user.remote;

import java.util.List;

import javax.annotation.Resource;

import com.keyi.netty.annotation.Remote;
import com.keyi.netty.util.ResponseUtil;
import com.keyi.user.model.User;
import com.keyi.user.service.UserService;

@Remote
public class UserRemoteImpl implements UserRemote {
	@Resource
	private UserService userService;

	public Object saveUser(User user) {
		userService.save(user);
		return ResponseUtil.createSuccessResult(user);
	}

	public Object saveUsers(List<User> users) {
		userService.saveList(users);
		return ResponseUtil.createSuccessResult(users);
	}

}
