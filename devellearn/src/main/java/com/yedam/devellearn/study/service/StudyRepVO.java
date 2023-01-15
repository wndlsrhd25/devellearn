package com.yedam.devellearn.study.service;
import java.util.Date;

import lombok.Data;

@Data
public class StudyRepVO {
    private int studyrepNo;
    private String memberId;
    private String content;
    private Date regDate;
    private int studyNo;
    private int commParent;

}
