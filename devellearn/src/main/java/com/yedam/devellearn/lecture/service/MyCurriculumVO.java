package com.yedam.devellearn.lecture.service;

import lombok.Data;

@Data
public class MyCurriculumVO {
    private String memberId;
    private int lectureNo;
    private int currNo;
    private String watchedAll;
    private String latestWatchedTime;
    // 학습하기 페이지에서 사용
    private String lectureTitle;
    private String currName;
    private String videoSrc;
    private int playTime;
}
