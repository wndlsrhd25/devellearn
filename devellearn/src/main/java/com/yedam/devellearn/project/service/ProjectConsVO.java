package com.yedam.devellearn.project.service;

import java.util.Date;

import lombok.Data;

@Data
public class ProjectConsVO {
    private int projectconsNo;
    private int projectNo;
    private String memberId;
    private String status;
    private Date applyDate;
    private Date approvalDate;
    private String withdraw;
    private String projectId;
    private String comememberId;
    private String projectName;
    private String category;
    private String img;
    //nhj추가
    private int myblogNo;

}
