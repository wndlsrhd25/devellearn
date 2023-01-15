package com.yedam.devellearn.resume.service;

import java.util.Date;

import lombok.Data;

@Data
public class CertificationsVO {
    private int certiNo;
    private String memberId;
    private String certiName;
    private String score;
    private String institution;
    private String acqDate;
    private String certiFile;
    private String status;
    private String rejection;
    private Date applyDate;
}
