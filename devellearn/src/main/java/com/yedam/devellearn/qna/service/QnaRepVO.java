package com.yedam.devellearn.qna.service;
import java.util.Date;

import lombok.Data;

@Data
public class QnaRepVO {
    private int qnarepNo;
    private String memberId;
    private String content;
    private Date regDate;
    private String choice;
    private int qnaNo;
    private int commParent;
}
