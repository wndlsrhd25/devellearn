package com.yedam.devellearn.admin.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MasterReqVO {
    private String memberId;
    private String content;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date applyDate;
    private String status;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date acceptDate;
    private String rejection;
    private String fileName;
    private String bank;
    private String account;
}
