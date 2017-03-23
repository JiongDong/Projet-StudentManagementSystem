package com.polytech.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.polytech.domain.User;

public class UserFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		//1.得到session
		//2.判断session中是否有用户,如果有，放行
		//3.否则转到登录页面
		HttpServletRequest req  = (HttpServletRequest)request;
		User user = (User)req.getSession().getAttribute("sessionUser");
		if(user != null){
			chain.doFilter(request, response);
		}else{
			HttpServletResponse resp = (HttpServletResponse)response;
			resp.sendRedirect(req.getContextPath() +  "/index.jsp");
		}		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
}
