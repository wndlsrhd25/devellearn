package com.yedam.devellearn.company.service;

import java.util.Date;

import lombok.Data;

@Data
public class ComReviewsVO {
    private int reviewNo;
    private int comNo;
    private String writer;
    private String category;
    private String career;
    private int rate;
    private String content;
    private String advantage;
    private String disadvantage;
    private int salaryRate;
    private int wlbRate;
    private int atmosphere;
    private int potential;
    private String aboutCeo;
    private String recommend;
    private Date regDate;
    private String name;
    
}
