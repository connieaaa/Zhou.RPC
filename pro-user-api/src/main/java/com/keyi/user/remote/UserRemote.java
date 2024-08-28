package com.keyi.user.remote;

import java.util.List;

import com.keyi.user.model.User;

public interface UserRemote {
	public Object saveUser(User user);

	public Object saveUsers(List<User> users);

}
