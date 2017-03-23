package com.polytech.domain;

import java.util.List;

public class PageBean <T>{
	private Integer pageCode;//当前页码
	private Integer pageSize;//每页数据大小
	private Integer totalRecord;//总记录
	private List<T> beanList;//当前页的记录，定义成泛型为了以后直接用
	private String url;//多条件组合查询时url后的条件
		
	//返回总页数
	public Integer getTotalPage(){
		int tp  = totalRecord/pageSize;
		return totalRecord%pageSize==0 ? tp : tp+1;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}	
	
	public Integer getPageCode() {
		return pageCode;
	}
	public void setPageCode(Integer pageCode) {
		this.pageCode = pageCode;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
	}
	public List<T> getBeanList() {
		return beanList;
	}
	public void setBeanList(List<T> beanList) {
		this.beanList = beanList;
	}
	
	
}
