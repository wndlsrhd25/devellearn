package com.yedam.devellearn.lecture.service;

import java.util.Date;
import lombok.Data;

@Data
public class LectureNoteVO {
    private int lectureNoteNo;
    private int currNo;
    private String memberId;
    private String content;
    private Date regDate;
    private String time;
}
