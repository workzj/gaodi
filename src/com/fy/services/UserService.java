package com.fy.services;

import java.util.List;
import com.fy.database.UserFy;

public interface UserService {
	//身份认证
	public String login(String userName,String password);
	public List GetRoleuser(String rolename);
	public List GetAlluser();
	public String AddUser(UserFy user);
	public String DelUser(Integer userId);
	public String GetNikeName(String name);
	public String GetUserName(Integer userId);
}
