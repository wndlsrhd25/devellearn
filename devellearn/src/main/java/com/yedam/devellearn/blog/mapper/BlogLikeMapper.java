package com.yedam.devellearn.blog.mapper;

import com.github.pagehelper.Page;
import com.yedam.devellearn.blog.service.BlogLikeVO;
import com.yedam.devellearn.blog.service.BlogPostVO;


public interface BlogLikeMapper {

	
	// 등록
	public int insertBlogLike(BlogLikeVO blogLikeVO);
	
	// 삭제
	public int deleteBlogLike(int likeNo);
	
	// 전체조회(card형식)
	public Page<BlogPostVO> getLikeCard(String memberId);
	//public List<BlogLikeVO> selectAllLikeList(String memberId);
		
	// 단건조회 - 로그인된 회원이 좋아요 했는지 확인
	public BlogLikeVO blogLike(BlogLikeVO blogLikeVO);
	
	//좋아요 수 카운트 - 게시글 번호로 조회
	public int selectLikeCnt(int postNO);
}
