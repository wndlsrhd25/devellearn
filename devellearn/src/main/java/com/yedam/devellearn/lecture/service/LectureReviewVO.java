package com.yedam.devellearn.lecture.service;

import java.util.Date;

import lombok.Data;

@Data
public class LectureReviewVO {
    private int reviewNo;
    private String memberId;
    private int lectureNo;
    private String content;
    private int rate;
    private Date regDate;
}
