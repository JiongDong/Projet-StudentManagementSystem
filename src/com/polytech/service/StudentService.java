package com.polytech.service;


import com.polytech.dao.StudentDao;
import com.polytech.domain.PageBean;
import com.polytech.domain.Student;

public class StudentService {
	private StudentDao studentDao = new StudentDao();
	
	/**
	 * 添加学生
	 * @date 2016-4-10
	 * @author Jiong Dong
	 * @param student
	 *
	 */
	public void add(Student student){
		studentDao.add(student);
	}
	
	/**
	 * 查询所有学生
	 * @date 2016-4-10
	 * @author Jiong Dong
	 * @return
	 *
	 */
	public PageBean<Student> findAll(int pc,int ps){
		return studentDao.findAll(pc,ps);
	}
	
	/**
	 * 删除一个学生
	 * @date 2016-4-10
	 * @author Jiong Dong
	 * @param s
	 *
	 */
	public void delete(String sid){
		studentDao.delete(sid);
	}
	
	/**
	 * 根据sid字段查询一个学生
	 * @date 2016-4-10
	 * @author Jiong Dong
	 * @param sid
	 * @return
	 *
	 */
	public Student findbySid(String sid){
		return studentDao.findBySid(sid);
	}
	
	/**
	 * 编辑学生（更改学生信息）
	 * @date 2016-4-10
	 * @author Jiong Dong
	 * @param s
	 *
	 */
	public void edit(Student s){
		studentDao.edit(s);
	}
	
	/**
	 * 高级搜索学生
	 * @date 2016-4-11
	 * @author Jiong Dong
	 * @param s
	 * @return
	 *
	 */
	public PageBean<Student>query(Student s,int pc,int ps){
		return studentDao.query(s,pc,ps);
	}
}
