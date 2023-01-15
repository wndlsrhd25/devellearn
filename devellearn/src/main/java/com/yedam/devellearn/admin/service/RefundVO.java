package com.yedam.devellearn.admin.service;

import java.util.Date;

import lombok.Data;

@Data
public class RefundVO {
    private int refundNo;
    private String memberId;
    private String reason;
    private Date regDate;
    private int price;
    private String account;
    private String bank;
    private String name;
    private int payNo;
    private String type;
    private String status;
    private Date depositDate;
}
