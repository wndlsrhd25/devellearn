package com.yedam.devellearn.blog.service;

import lombok.Data;

@Data
public class MyBlogVO {
	
	private String memberId;
	private String blogId;
	private String folderName;
	private int folderNo;
	private int myblogNo;
	private int blogType;
	private int bookmarkNo;

}
