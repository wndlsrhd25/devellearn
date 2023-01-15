package com.yedam.devellearn.lecture.service;

import java.util.Date;
import lombok.Data;

@Data
public class PaymentVO {
    private int paymentNo;
    private String memberId;
    private int lectureNo;
    private String lectureTitle;
    private Date paymentDate;
    private String paymentType;
    private int price;
    private String merchantUid; // 주문코드
    private String status;
}
