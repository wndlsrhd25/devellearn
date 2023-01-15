package com.yedam.devellearn.admin.service;

import java.util.Date;

import lombok.Data;

@Data
public class Mymoney {
    private int moneyNo;
    private String memberId;
    private String name;
    private String bank;
    private String account;
    private Date depositDate;
    private String status;
    private int price;
    private String period;
}
