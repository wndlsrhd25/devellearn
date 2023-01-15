package com.yedam.devellearn.blog.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.blog.service.BlogLikeService;
import com.yedam.devellearn.blog.service.BlogLikeVO;
import com.yedam.devellearn.blog.service.BlogPostVO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
public class BlogLikeController {

	@Autowired
	BlogLikeService service;
	
	
	//좋아요 등록
	@PostMapping("/insertBlogLike")
	public BlogLikeVO insertBlogLike(@RequestBody BlogLikeVO blogLikeVO) {
		service.insertBlogLike(blogLikeVO);
		return blogLikeVO;
	}
	
	//좋아요 삭제
	@DeleteMapping("/deleteBlogLike/{likeNo}")
	public int deleteBlogLike(@PathVariable int likeNo) {
		return service.deleteBlogLike(likeNo);
	}
	
	//좋아요 단건조회(로그인회원 좋아요 확인)
	@PostMapping("/blogLike")
	public BlogLikeVO blogLike(@RequestBody BlogLikeVO blogLikeVO) {
		return service.blogLike(blogLikeVO);
	}
	
	//좋아요 수
	@GetMapping("/blogLikeCnt/{postNo}")
	public int selectLikeCnt(@PathVariable int postNo) {
		return service.selectLikeCnt(postNo);
	}
	
	//좋아요 전체 조회(card)
	@PostMapping("/blogLikeCard")
	public PageInfo<BlogPostVO> getLikeCard(@RequestBody BlogPostVO blogPostVO){
		System.out.println("===================================================");
		System.out.println(blogPostVO.getMemberId());
		PageHelper.startPage(blogPostVO.getPageNum(),10);
		return PageInfo.of(service.getLikeCard(blogPostVO.getMemberId()));
	
	
	}
	
	
}
