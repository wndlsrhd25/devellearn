package com.yedam.devellearn.blog.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.blog.service.BlogFollowService;
import com.yedam.devellearn.blog.service.BlogFollowVO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*", maxAge = 3600)
public class BlogFollowController {

	@Autowired
	BlogFollowService service;
	
	//팔로우 신청
	@PostMapping("/insertFollow")
	public BlogFollowVO insertFollow(@RequestBody BlogFollowVO blogFollowVO) {
		service.insertFollow(blogFollowVO);
		return blogFollowVO;
	}
	
	//팔로우 취소
	@DeleteMapping("/deleteFollow/{followNo}")
	public int deleteFollow(@PathVariable int followNo) {
		return service.deleteFollow(followNo);
	}
	
	
	//팔로우 상태 판단(단건조회)
	@PostMapping("/follow")
	public BlogFollowVO selectFollow(@RequestBody BlogFollowVO blogFollowVO) {
		System.out.println("단건 조회" +blogFollowVO);
		return service.selectFollow(blogFollowVO);
		 
		 
	}
	
	
	//블로그 팔로우 전체 조회
	@PostMapping("/followList")
	public PageInfo<BlogFollowVO> getFollowList(@RequestBody BlogFollowVO blogFollowVO){
		blogFollowVO.setMemberId(blogFollowVO.getMemberId());
		PageHelper.startPage(blogFollowVO.getPageNum(),10);
		return PageInfo.of(service.getFollowList(blogFollowVO.getMemberId()));
	}
	
}
