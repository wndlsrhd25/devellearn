package com.yedam.devellearn.lecture.service;

import lombok.Data;

@Data
public class CurriculumVO {
    private int currNo;
    private int lectureNo;
    private String lectureTitle;
    private String currName;
    private String videoSrc;
    private int playTime;
}
