package com.yedam.devellearn.blog.service;

import java.util.Date;

import lombok.Data;

@Data
public class BlogCommentVO {
    private int commentNo;
    private int postNo;
    private String content;
    private Date createDate;
    private String memberId;
    private int commParent;
    private String profile;
    private String changeNoneHtml;
    private int commentCnt;
}
