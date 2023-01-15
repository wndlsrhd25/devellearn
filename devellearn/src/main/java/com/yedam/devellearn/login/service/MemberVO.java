package com.yedam.devellearn.login.service;

import lombok.Data;

@Data
public class MemberVO {

	private String memberId;
	private String password;
	private int loginType;
	private String token;
	private String name;
	private String email;
	private String phone;
	private String profile;
	private String address;
	private String addressD;
	private String grade;
	private String status;	
	private String introduction;
	private String skill;
	private String myResume;
	

}


