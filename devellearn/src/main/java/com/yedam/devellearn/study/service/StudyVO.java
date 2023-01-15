package com.yedam.devellearn.study.service;
import java.util.Date;

import lombok.Data;

@Data
public class StudyVO {
    private int studyNo;
    private String memberId;
    private String title;
    private String content;
    private String category;
    private String open;
    private Date regDate;
    private int count;
    private String img;

}
