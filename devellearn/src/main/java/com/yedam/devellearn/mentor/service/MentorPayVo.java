package com.yedam.devellearn.mentor.service;
import java.util.Date;

import lombok.Data;

@Data
public class MentorPayVo {
    private int payNo;
    private String kind;
    private String bank;
    private Date payDate;
    private int approval;
    private int total;
    private int dateCount;
    private int mentorconsNo;
    private String status;

}
