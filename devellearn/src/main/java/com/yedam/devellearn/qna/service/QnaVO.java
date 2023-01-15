package com.yedam.devellearn.qna.service;
import java.util.Date;

import lombok.Data;

@Data
public class QnaVO {
    private int qnaNo;
    private String title;
    private String content;
    private Date regDate;
    private int count;
    private String memberId;
    private String open;
    private String img;
    private String category;
}
