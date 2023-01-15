package com.yedam.devellearn.recruit.service;

import java.util.List;

import lombok.Data;

@Data
public class Filter {
	private List<String> skillList;
	private List<String> careerList;
	private List<String> jobTypeList;
	private List<String> empTypeList;
	private double avgRate;
	private String order;
}
