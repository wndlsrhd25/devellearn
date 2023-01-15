package com.yedam.devellearn.admin.service;

import lombok.Data;

@Data
public class ReportsVO {
    private int reportNo;
    private String memberId;
    private String reportCode;
    private String content;
    private int postNo;
    private String postCode;
    private String result;
    private String colname;
}
