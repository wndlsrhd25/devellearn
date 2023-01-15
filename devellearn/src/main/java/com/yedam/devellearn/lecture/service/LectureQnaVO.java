package com.yedam.devellearn.lecture.service;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import lombok.Data;

@Data
public class LectureQnaVO {
    private int qnaNo;
    private String memberId;
    private int currNo;
    private String currName;
    private String lectureTitle;
    private String title;
    private String content;
    private String noneHtmlContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date regDate;
    private String status;
    // 답변 수
    private int replyNum;
    // 강의번호
    private int lectureNo;
    private int pageNum;
    private String keyword;
}
