package com.yedam.devellearn.blog.mapper;

import com.github.pagehelper.Page;
import com.yedam.devellearn.blog.service.BlogFollowVO;

public interface BlogFollowMapper {

	//팔로우 신청
	public int insertFollow(BlogFollowVO blogFollowVO);
	
	//팔로우 취소
	public int deleteFollow(int followNo);
	
	//팔로우 상태 판단(단건조회)
	public BlogFollowVO selectFollow(BlogFollowVO blogFollowVO);

	//블로그 팔로우 전체 조회
	public Page<BlogFollowVO> getFollowList(String memberId);
			
	
}
