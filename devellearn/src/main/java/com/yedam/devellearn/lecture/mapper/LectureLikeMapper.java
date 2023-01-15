package com.yedam.devellearn.lecture.mapper;

import java.util.List;

import com.yedam.devellearn.lecture.service.LectureLikeVO;

public interface LectureLikeMapper {

	// 고유번호조회
	public int getNo();
	
	// 등록
	public int insert(LectureLikeVO lectureLikeVO);
	
	// 삭제
	public int delete(int likeNo);
	
	// 단건조회
	public LectureLikeVO selectOne(LectureLikeVO lectureLikeVO);
	
	// 전체조회 - 회원아이디로 조회
	public List<LectureLikeVO> selectAllByMemberId(String memberId);
	
}
