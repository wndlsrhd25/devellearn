package com.yedam.devellearn.blog.service;

import com.github.pagehelper.Page;

public interface BlogFollowService {

	//팔로우 신청
	public void insertFollow(BlogFollowVO blogFollowVO);
	
	//팔로우 취소
	public int deleteFollow(int followNo);
	
	//팔로우 상태 판단(단건조회)
	public BlogFollowVO selectFollow(BlogFollowVO blogFollowVO);

	//블로그 팔로우 전체 조회
	public Page<BlogFollowVO> getFollowList(String memberId);
	
}
