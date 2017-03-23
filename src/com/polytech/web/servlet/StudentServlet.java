package com.polytech.web.servlet;


import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;

import com.polytech.domain.PageBean;
import com.polytech.domain.Student;
import com.polytech.service.StudentService;
import com.polytech.utils.BaseServlet;

public class StudentServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;
	
	private StudentService studentService = new StudentService();
	
	/**
	 * 添加一个学生
	 * @date 2016-4-11
	 * @author Jiong Dong
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 *
	 */
	public String add(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到Customer对象
		 * 2. 补全：cid，使用uuid
		 * 3. 使用service方法完成添加工作
		 * 4. 向request域中保存成功信息
		 * 5. 转发到msg.jsp
		 */
		Student s = CommonUtils.toBean(req.getParameterMap(), Student.class);
		s.setSid(CommonUtils.uuid());
		studentService.add(s);
		req.setAttribute("msg", "添加学生成功！");
		
		return "f:/users/msg.jsp";
	}
	
	/**
	 * 查询所有的学生
	 * @date 2016-4-11
	 * @author Jiong Dong
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 *
	 */
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获得pageCode
		 * 2. 给定pageSize
		 * 3. 调用service的方法，得到PageBean，保存到request域
		 * 4. 转发到list.jsp
		 */
		int pc = getPageCode(req);
		int ps = 12;//每页12行记录
		PageBean<Student> pb = studentService.findAll(pc,ps);
		
		//得到URL
		pb.setUrl(getURL(req));
				
		req.setAttribute("pb", pb);
		return "f:/users/list.jsp";
	}
	
	/**
	 * 得到pageCode
	 * @date 2016-4-12
	 * @author Jiong Dong
	 * @param request
	 * @return
	 *
	 */
	public int getPageCode(HttpServletRequest request){
		//如果pageCode参数不存在，pageCode=1
		//如果pageCode参数存在，pageCode转成int值返回
		String value = request.getParameter("pc");
		if(value == null || value.trim().isEmpty()){
			return 1;
		}
		return Integer.valueOf(value);
	}
	
	/**
	 * 编辑学生信息之前获得该学生的信息
	 * @date 2016-4-11
	 * @author Jiong Dong
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 *
	 */
	public String preEdit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		Student s = studentService.findbySid(req.getParameter("sid"));
		req.setAttribute("student",s);
		return "f:/users/edit.jsp";
	}
	
	/**
	 * 编辑学生信息
	 * @date 2016-4-11
	 * @author Jiong Dong
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 *
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {		
		Student s = CommonUtils.toBean(req.getParameterMap(), Student.class);		
		studentService.edit(s);
		req.setAttribute("msg", "编辑学生成功！");
		return "f:/users/msg.jsp";
	}
	
	/**
	 * 删除一个学生
	 * @date 2016-4-11
	 * @author Jiong Dong
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 *
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		studentService.delete(req.getParameter("sid"));
		req.setAttribute("msg", "删除学生成功！");
		return "f:/users/msg.jsp";
	}
	
	/**
	 * 高级搜索学生
	 * @date 2016-4-11
	 * @author Jiong Dong
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 *
	 */
	public String query(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//得到搜索条件
		Student s = CommonUtils.toBean(req.getParameterMap(), Student.class);
		
		//get请求处理编码问题
		s = encoding(s);
		
		int pc = getPageCode(req);
		int ps = 12;//每页12行记录
		PageBean<Student> pb = studentService.query(s,pc,ps);
		
		//得到URL
		pb.setUrl(getURL(req));
		
		req.setAttribute("pb", pb);
		return "f:/users/list.jsp";
	}
	/**
	 * 截取URL
	 * /项目名/servlet路径？参数字符串
	 * @date 2016-4-12
	 * @author Jiong Dong
	 * @return
	 *
	 */
	public String getURL(HttpServletRequest request){
		String contextPath = request.getContextPath();//项目名
		String servletPath = request.getServletPath();//servlet路径
		String queryString = request.getQueryString();//参数字符串
		
		//判断参数部分是否包含pc这个参数，如果有，去掉
		if(queryString.contains("&pc=")){
			int index = queryString.lastIndexOf("&pc=");
			queryString = queryString.substring(0, index);
		}
		
		return contextPath + servletPath + "?" + queryString;
	}
	
	public Student encoding(Student s) throws UnsupportedEncodingException{
		String sname = s.getSname();
		String gender = s.getGender();
		String tellphone = s.getTellphone();
		String email = s.getEmail();
		
		if(sname != null && !sname.trim().isEmpty()){
			sname = new String(sname.getBytes("ISO-8859-1"),"utf-8");
			s.setSname(sname);
		}
		if(gender != null && !gender.trim().isEmpty()){
			gender = new String(gender.getBytes("ISO-8859-1"),"utf-8");
			s.setGender(gender);
		}
		if(tellphone != null && !tellphone.trim().isEmpty()){
			tellphone = new String(tellphone.getBytes("ISO-8859-1"),"utf-8");
			s.setTellphone(tellphone);
		}
		if(email != null && !email.trim().isEmpty()){
			email = new String(email.getBytes("ISO-8859-1"),"utf-8");
			s.setEmail(email);
		}
		
		return s;
	}
}
