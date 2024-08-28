package com.keyi.user.remote;

import java.util.List;

import com.keyi.client.param.Response;
import com.keyi.user.bean.User;

public interface UserRemote {
	public Response saveUser(User user);

	public Response saveUsers(List<User> users);

}
