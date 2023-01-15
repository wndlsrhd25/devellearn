package com.yedam.devellearn.board.service;
import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
    private int boardNo;
    private String memberId;
    private String title;
    private String content;
    private Date regDate;
    private int count;
    private String boardType;
}
