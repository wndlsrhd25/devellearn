package com.yedam.devellearn.blog.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.devellearn.blog.service.BlogCommentService;
import com.yedam.devellearn.blog.service.BlogCommentVO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class BlogCommentController {

	
	@Autowired
	BlogCommentService service;
	
	
	
	//댓글 전체조회
	@PostMapping("/blogComment")
	public List<BlogCommentVO> selectComment(@RequestBody BlogCommentVO blogCommentVO){
		return service.selectComment(blogCommentVO);
		
	}
	
	
	//댓글 등록
	@PostMapping("/blogComment/write")
	public BlogCommentVO insertComment(@RequestBody BlogCommentVO blogCommentVO) {
		service.insertComment(blogCommentVO);
		return blogCommentVO;
	}
	
	//댓글 수정
	@PostMapping("/blogComment/update")
	public int updateBlogComment(@RequestBody BlogCommentVO blogCommentVO) {
		return service.updateBlogComment(blogCommentVO);
		 
	}
	
	//댓글 삭제
	@DeleteMapping("/blogComment/{commentNo}")
	public int deleteBlogComment(@PathVariable int commentNo) {
		return service.deleteBlogComment(commentNo);
		
	}
	
	//댓글 단건조회
	@PostMapping("/blogComment/select")
	public BlogCommentVO selectCommentOne(@RequestBody BlogCommentVO blogCommentVO) {
		return service.selectCommentOne(blogCommentVO);
	}
	
	//대댓글
	@PostMapping("/blogComment/write/reply")
	public BlogCommentVO insertCommentReply(@RequestBody BlogCommentVO blogCommentVO) {
		service.insertCommentReply(blogCommentVO);
		return blogCommentVO;
	}
	
}
