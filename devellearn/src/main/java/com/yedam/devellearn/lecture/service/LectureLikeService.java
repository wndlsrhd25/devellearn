package com.yedam.devellearn.lecture.service;

import java.util.List;

public interface LectureLikeService {
	// 고유번호조회
	public int getNo();
	
	// 등록
	public int insertLikedLecture(LectureLikeVO lectureLikeVO);
	
	// 삭제
	public int deleteLikedLecture(int likeNo);
	
	// 단건조회
	public LectureLikeVO getLikedLecture(LectureLikeVO lectureLikeVO);
	
	// 전체조회 - 회원아이디로 조회
	public List<LectureLikeVO> getLikedLectureListByMemberId(String memberId);
}
