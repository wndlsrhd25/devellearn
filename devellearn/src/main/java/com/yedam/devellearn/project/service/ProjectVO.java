package com.yedam.devellearn.project.service;

import java.util.Date;

import lombok.Data;

@Data
public class ProjectVO {

    private int projectNo;
    private String memberId;
    private String projectName;
    private String content;
    private String category;
    private String open;
    private int human;
    private String how;
    private Date startMon;
    private Date endMon;
    private Date regDate;
    private int count;
    private String img;
    private String projectId;
    private int folderNo;
}
