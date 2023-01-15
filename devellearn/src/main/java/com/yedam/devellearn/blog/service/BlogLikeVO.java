package com.yedam.devellearn.blog.service;

import lombok.Data;

@Data
public class BlogLikeVO {

	private int likeNo;
	private String memberId;
	private int postNo;
	private String changeNoneHtml;
}
