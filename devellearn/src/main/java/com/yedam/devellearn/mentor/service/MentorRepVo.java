package com.yedam.devellearn.mentor.service;
import java.util.Date;

import lombok.Data;

@Data
public class MentorRepVo {
    private int mentorrepNo;
    private String memberId;
    private String content;
    private Date regDate;
    private int mentorNo;
    private int commParent;

}
