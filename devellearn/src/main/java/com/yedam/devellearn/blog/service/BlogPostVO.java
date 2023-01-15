package com.yedam.devellearn.blog.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BlogPostVO {
    private int postNo;
    private String blogId;
    private int folderNo;
    private String folderName;
    private int bookmarkNo;
    private String title;
    private String content;
    private String thumbnail;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createDate;
    private int hits;
    private String readStatus;
    private String commentSet;
    private int likes;
    private String tags;
    private String keyword;
    private String fileName;
    private int commentCnt;
    private String memberId;
    private String profile;
    private int allCnt;
    private int postCnt;
    private String noneHtmlContent;
    private String bookmarkId;
    private int pageNum;
    private int likeNo;
    private int book;
    private String bookFolderName;
    private String projectId;
    private String projectName;
    private String startMon;
    private String endMon;
    private String img;
    private int projectNo;
    private int comments;
    private String order;
    private String deleteNo;
    BlogLikeVO blogLikeVO;

    

}
