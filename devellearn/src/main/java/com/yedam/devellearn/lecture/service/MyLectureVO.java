package com.yedam.devellearn.lecture.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MyLectureVO {
    private int myLectureNo;
    private String memberId;
    private int lectureNo;
    private int latestWatchedCurr;
    private int latestWatchedTime;
    private int totalWatchedTime;
    private int totalPlayTime;
    private String completeStatus;
    // 학습하기 및 마이페이지에서 사용
    private String likeStatus; // 좋아요 유무
    private double avgScore; // 평점
    private int learnerNum; // 수강생 수
    private String lecturer;
    private String title;
    private String introduce;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
    private String category;
    private int price;
    private String difficulty;
    private String status;
    private String skillTag;
    private String imgSrc;
}
