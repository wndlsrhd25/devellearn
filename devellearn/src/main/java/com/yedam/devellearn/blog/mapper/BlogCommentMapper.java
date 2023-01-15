package com.yedam.devellearn.blog.mapper;

import java.util.List;

import com.yedam.devellearn.blog.service.BlogCommentVO;

public interface BlogCommentMapper {


	
	//댓글 전체 조회(post_no)
	public List<BlogCommentVO> selectComment(BlogCommentVO blogCommentVO);
	
	//블로그 댓글 입력
	public void insertComment(BlogCommentVO blogCommentVO);
	
	//블로그 댓글 수정 
	public int updateBlogComment(BlogCommentVO blogCommentVO);

	//블로그 댓글 삭제 
	public int deleteBlogComment(int commentNo);

	//블로그 댓글 단건 조회(수정시 필요함)
	public BlogCommentVO selectCommentOne(BlogCommentVO blogCommentVO);
	
	//블로그 대댓글
	public void insertCommentReply(BlogCommentVO blogCommentVO);
}
