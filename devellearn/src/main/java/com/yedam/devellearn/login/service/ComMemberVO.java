package com.yedam.devellearn.login.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ComMemberVO {

	private String comId;
	private String comNo;
	private String password;
	private String name;
	private String ceoName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date estDate;
	private String manager;
	private String phone;
	private String email;
	private String dept;
	private String position;
	private String addr;
}
