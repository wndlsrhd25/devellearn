package com.yedam.devellearn.recruit.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RecruitHistoryVO {
    private int historyNo;
    private int recruitNo;
    private String memberId;
    private String status;
    private String resume;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date applyDate;
    private String title;
    private String name;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date endDate;
    private String isRead;
}
