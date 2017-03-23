package com.polytech.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.polytech.domain.PageBean;
import com.polytech.domain.Student;
import com.polytech.utils.TxQueryRunner;

public class StudentDao {
	private QueryRunner qr = new TxQueryRunner();
	
	/**
	 * 添加学生
	 * @date 2016-4-10
	 * @author Jiong Dong
	 * @param student
	 * @throws SQLException
	 *
	 */
	public void add(Student s){
		try {
			String sql = "INSERT INTO t_student VALUES(?,?,?,?,?,?,?)";
			Object[] params = {s.getSid(),s.getSname(),s.getGender(),
					s.getBirthday(),s.getTellphone(),s.getEmail(),s.getDescription()};			
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
 	}
	
	/**
	 * 查询所有学生
	 * @date 2016-4-10
	 * @author Jiong Dong
	 * @return
	 *
	 */
	public PageBean<Student> findAll(int pc,int ps){
		try{
			PageBean<Student> pb = new PageBean<Student>();
			pb.setPageCode(pc);
			pb.setPageSize(ps);
			
			//得到总记录
			String sql = "SELECT COUNT(*) FROM t_student";
			Number number = (Number)qr.query(sql, new ScalarHandler());
			int tr = number.intValue();
			pb.setTotalRecord(tr);
			
			//得到学生列表
			sql = "SELECT * FROM t_student ORDER BY sname limit ?,?";
			List<Student> beanList = qr.query(sql, 
					new BeanListHandler<Student>(Student.class),(pc-1)*ps,ps);
			pb.setBeanList(beanList);
			return pb;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	/**
	 * 删除一个学生
	 * @date 2016-4-10
	 * @author Jiong Dong
	 * @param s
	 *
	 */
	public void delete(String sid){
		try{
			String sql = "DELETE FROM t_student WHERE sid=?";
			qr.update(sql,sid);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 根据sid字段查询一个学生
	 * @date 2016-4-10
	 * @author Jiong Dong
	 * @param sid
	 * @return
	 *
	 */
	public Student findBySid(String sid){
		try{
			String sql = "SELECT * FROM t_student WHERE sid=?";
			return qr.query(sql,new BeanHandler<Student>(Student.class),sid);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 编辑学生（更改学生信息）
	 * @date 2016-4-10
	 * @author Jiong Dong
	 * @param s
	 *
	 */
	public void edit(Student s){
		try{
			String sql = "UPDATE t_student SET sname=?,gender=?,birthday=?," +
					"tellphone=?,email=?,description=? where sid=?";
			Object[] params = {s.getSname(),s.getGender(),s.getBirthday(),
					s.getTellphone(),s.getEmail(),s.getDescription(),s.getSid()};
			qr.update(sql,params);
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 高级搜索学生,多条件组合查询
	 * @date 2016-4-11
	 * @author Jiong Dong
	 * @param s
	 * @return
	 *
	 */	
	public PageBean<Student> query(Student s,int pc,int ps){
		try{
			PageBean<Student> pb = new PageBean<Student>();
			pb.setPageCode(pc);
			pb.setPageSize(ps);
			
			/*
			 * 查询出总记录
			 */
			StringBuilder numSql = new StringBuilder("SELECT COUNT(*) FROM t_student");
			StringBuilder whereSql = new StringBuilder(" WHERE 1=1");
			List<Object> params = new ArrayList<Object>();
			
			if(s.getSname() != null && !s.getSname().trim().isEmpty()){
				whereSql.append(" and sname like ?");
				params.add("%" + s.getSname() + "%");
			}
			
			if(s.getGender() != null && !s.getGender().trim().isEmpty()){
				whereSql.append(" and gender=?");
				params.add(s.getGender());
			}
			
			if(s.getTellphone() != null && !s.getTellphone().trim().isEmpty()){
				whereSql.append(" and tellphone like ?");
				params.add("%" + s.getTellphone() + "%");
			}
			
			if(s.getEmail() != null && !s.getEmail().trim().isEmpty()){
				whereSql.append(" and email like ?");
				params.add("%" + s.getEmail() + "%");
			}
			
			Number number = (Number)qr.query(numSql.append(whereSql).toString(), 
					new ScalarHandler(),params.toArray());
			int totalRecord = number.intValue();
			pb.setTotalRecord(totalRecord);
			
			/*
			 * 得到beanList结果集
			 */
			StringBuilder sql = new StringBuilder("SELECT * FROM t_student");
			StringBuilder limitSql = new StringBuilder(" limit ?,?");
			params.add((pc-1)*ps);
			params.add(ps);
			List<Student> students = qr.query(sql.append(whereSql).append(limitSql).toString(),
					new BeanListHandler<Student>(Student.class),params.toArray());
			pb.setBeanList(students);
			return pb;
		}catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
