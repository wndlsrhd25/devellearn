package com.yedam.devellearn.project.service;

import java.util.Date;

import lombok.Data;

@Data
public class ProjectRepVO {
    private int projectrepNo;
    private String memberId;
    private String content;
    private Date regDate;
    private int projectNo;
    private int commParent;

}
