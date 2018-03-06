package com.swagger.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="User",description="用户对象")
public class User {

	@ApiModelProperty(value="主键")
	private int id;
	
	@ApiModelProperty(value="姓名")
	private String name;
	
	@ApiModelProperty(value="年龄",access="hidden")
	private int age;
	
	@ApiModelProperty(value="创建时间")
	private Date ctm;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	@JsonFormat(pattern="yyyy-MM-dd") 
	public Date getCtm() {
		return ctm;
	}
	public void setCtm(Date ctm) {
		this.ctm = ctm;
	}
	
}
