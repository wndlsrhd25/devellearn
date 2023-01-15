package com.yedam.devellearn.mentor.service;

import java.util.Date;

import lombok.Data;

@Data
public class MentorVO {
    private int mentorNo;
    private String memberId;
    private String title;
    private String content;
    private String category;
    private String open;
    private Date regDate;
    private int count;
    private String price;
    private String img;

}
