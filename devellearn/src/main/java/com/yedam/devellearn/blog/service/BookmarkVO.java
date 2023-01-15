package com.yedam.devellearn.blog.service;

import lombok.Data;

@Data
public class BookmarkVO {
    private int bookmarkNo;
    private String bookFolderName;
    private String memberId;
    private int postNo;
    private int cnt;
    private String newBookFolderName;
    private String changeNoneHtml;
    private int PageNum;
}
