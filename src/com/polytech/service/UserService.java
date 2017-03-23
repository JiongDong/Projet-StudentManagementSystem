package com.polytech.service;

import com.polytech.dao.UserDao;
import com.polytech.domain.User;

public class UserService {
	private UserDao userDao = new UserDao();
	
	public User query(User form) throws Exception{
		User user = userDao.query(form);
		
		//没找到用户
		if(user == null){
			throw new Exception("用户名不存在");
		}
		
		//找到用户了，但密码不匹配
		if(!form.getUpassword().equals(user.getUpassword())){
			throw new Exception("密码错误");
		}
		
		return user;
	}
}
