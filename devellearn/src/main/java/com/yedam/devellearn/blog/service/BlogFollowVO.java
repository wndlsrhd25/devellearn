package com.yedam.devellearn.blog.service;

import lombok.Data;

@Data
public class BlogFollowVO {
    private int followNo;
    private String followContent;
    private String follower;
    private String following;
    private String memberId;
    private String blogId;
    private String profile;
    private int PageNum;
}
