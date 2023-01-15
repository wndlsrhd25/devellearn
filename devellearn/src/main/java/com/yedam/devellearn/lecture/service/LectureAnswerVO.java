package com.yedam.devellearn.lecture.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class LectureAnswerVO {
    private int answerNo;
    private int qnaNo;
    private String memberId;
    private String content;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
}
