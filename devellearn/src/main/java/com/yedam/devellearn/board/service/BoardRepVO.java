package com.yedam.devellearn.board.service;
import java.util.Date;

import lombok.Data;

@Data
public class BoardRepVO {
    private int boardNo;
    private String memberId;
    private int repNo;
    private String content;
    private Date regDate;
    private String boardType;
}
