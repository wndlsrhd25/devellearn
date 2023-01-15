package com.yedam.devellearn.company.service;

import java.util.Date;

import lombok.Data;

@Data
public class CompanyVO {
    private String name;
    private String jobType;
    private String comSize;
    private int empSize;
    private Date estDate;
    private String ceo;
    private String sales;
    private String addr;
    private String url;
    private String introduce;
    private String skill;
    private int comNo;
    private double avgRate;
    private double avgSalrate;
    private double avgWlbrate;
    private String img;
    private String bookmarkId;
}
