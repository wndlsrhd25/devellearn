package com.yedam.devellearn.lecture.service;

import java.util.Date; 

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class LectureVO {
    private int lectureNo;
    private String memberId;
    private String title;
    private String introduce;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
    private String category;
    private int price;
    private String difficulty;
    private String status;
    private String skillTag;
    private int playTime;
    private String imgSrc;
    // 평점
    private double avgScore;
    // 수강생 수
    private int learnerNum;
    // 강사이름
    private String name;

}
