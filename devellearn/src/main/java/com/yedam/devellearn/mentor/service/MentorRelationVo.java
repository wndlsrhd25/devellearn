package com.yedam.devellearn.mentor.service;
import java.util.Date;

import lombok.Data;

@Data
public class MentorRelationVo {
    private int mentorconsNo;
    private Date startDate;
    private Date endDate;
    private String status;
    private String memberId;
    private String mentiId;
    private int mentorNo;
    private String countDate;
    private String title;
    private String img;
    private String category;

}
