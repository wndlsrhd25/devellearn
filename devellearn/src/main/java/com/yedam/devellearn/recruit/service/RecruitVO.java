package com.yedam.devellearn.recruit.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RecruitVO {
    private int recruitNo;
    private int comNo;
    private String title;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String jobType;
    private String career;
    private String empType;
    private String salary;
    private String introduce;
    private String jobDetail;
    private String advantage;
    private String etc;
    private String contact;
    @DateTimeFormat(pattern = "yyyy-MM-dd") 
    private Date regDate;
    private String skill;
    private String img;
    private double avgRate;
    private double avgSalrate;
    private double avgWlbrate;
    private String name;
    private String bookmarkId;
}
