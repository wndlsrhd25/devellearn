package com.yedam.devellearn.admin.service;

import lombok.Data;

@Data
public class NotificationVO {
    private int notiNo;
    private String recvId;
    private String notiCode;
    private int postNo;
    private String postCode;
    private String isread;
}
