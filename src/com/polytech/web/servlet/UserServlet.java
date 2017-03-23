package com.polytech.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;

import com.polytech.domain.User;
import com.polytech.service.UserService;
import com.polytech.utils.BaseServlet;


public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserService();
	
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		try {
			User user = userService.query(form);
			request.getSession().setAttribute("sessionUser", user);
			return "r:/users/main.jsp";
		} catch (Exception e) {
			request.setAttribute("msg",e.getMessage());
			return "/index.jsp";
		}
	
	}
}
