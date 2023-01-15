package com.yedam.devellearn.resume.service;

import java.util.Date;

import lombok.Data;

@Data
public class CareerVO {

	
	private int careerNo; 
	private String memberId; 
	private String company; 
	private String dept; 
	private String position; 
	private String jobType;  
	private String jobStatus; 
	private String jobPeriod;
	private String task; 
	private String status; //null
	private String certiFile; 
	private String rejection;  //null
	private Date applyDate;  //null
}
