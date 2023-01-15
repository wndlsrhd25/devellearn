package com.yedam.devellearn.blog.service;

import com.github.pagehelper.Page;

public interface BlogLikeService {

	// 등록
	public int insertBlogLike(BlogLikeVO blogLikeVO);

	// 삭제
	public int deleteBlogLike(int likeNo);

	// 전체조회
	//public List<BlogLikeVO> getBlogLikeList(String memberId);
	
	
	// 전체조회 - CARD형식
	public Page<BlogPostVO> getLikeCard(String memberId);
	
	// 좋아요 수 카운트 - 강의질문번호로 조회 - 마이바티스 조건문 활용
	public int selectLikeCnt(int PostNo);

	// 단건조회 - 로그인된 회원이 좋아요 했는지 확인 - 파라미터 : {postNo, memberId}
	public BlogLikeVO blogLike(BlogLikeVO blogLikeVO);


}
