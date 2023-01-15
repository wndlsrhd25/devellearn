package com.yedam.devellearn.lecture.service;

import lombok.Data;

@Data
public class CartVO {
    private int cartNo;
    private String memberId;
    private int lectureNo;
    // 강의 사진 경로
    private String imgSrc;
    // 강의자
    private String lecturer;
    // 강의제목
    private String title;
    // 수강생
    private int learnerNum;
    // 가격
    private int price;
    
}
