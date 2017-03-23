package com.polytech.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.polytech.domain.User;

import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
	private QueryRunner qr = new TxQueryRunner();
	
	public User query(User user){
		String sql = "SELECT * FROM t_user WHERE uname = ?";
		User existUser;
		try {
			existUser = qr.query(sql, new BeanHandler<User>(User.class), 
					user.getUname());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("用户登录失败!");
		}
		return existUser;
	}
}
