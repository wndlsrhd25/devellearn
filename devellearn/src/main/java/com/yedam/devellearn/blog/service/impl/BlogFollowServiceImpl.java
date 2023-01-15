package com.yedam.devellearn.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.blog.mapper.BlogFollowMapper;
import com.yedam.devellearn.blog.service.BlogFollowService;
import com.yedam.devellearn.blog.service.BlogFollowVO;
import com.yedam.devellearn.blog.service.BlogPostVO;

@Service
public class BlogFollowServiceImpl implements BlogFollowService {

	
	@Autowired
	BlogFollowMapper mapper;
	
	//팔로우 신청
	@Override
	public void insertFollow(BlogFollowVO blogFollowVO) {
		mapper.insertFollow(blogFollowVO);
	}

	//팔로우 취소
	@Override
	public int deleteFollow(int followNo) {
		return mapper.deleteFollow(followNo);
	}

	//팔로우 상태 판단(단건조회)
	@Override
	public BlogFollowVO selectFollow(BlogFollowVO blogFollowVO) {
	     return mapper.selectFollow(blogFollowVO);
	}

	//블로그 팔로우 전체 조회
	@Override
	public Page<BlogFollowVO> getFollowList(String memberId) {
		Page<BlogFollowVO> list  = mapper.getFollowList(memberId);
		return list;
		
	}

}
