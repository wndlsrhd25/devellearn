package com.yedam.devellearn.company.service;

import java.util.Date;

import lombok.Data;

@Data
public class InterviewReviewsVO {
    private int reviewNo;
    private int comNo;
    private String writer;
    private int difficulty;
    private String content;
    private String atmosphere;
    private String result;
    private Date regDate;
    private String question;
}
