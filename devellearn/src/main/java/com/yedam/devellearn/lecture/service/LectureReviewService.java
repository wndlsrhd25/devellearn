package com.yedam.devellearn.lecture.service;

import com.github.pagehelper.Page;

public interface LectureReviewService {
	// 고유번호 조회
	public int getNo();
	// 리뷰등록
	public void reviewRegist(LectureReviewVO lectureReviewVO);
	// 리뷰수정
	public int reviewUpdate(LectureReviewVO lectureReviewVO);
	// 리뷰삭제
	public int reviewDelete(int reviewNo);
	// 전체리뷰조회 - 해당 강의
	public Page<LectureReviewVO> getReviewListByLectureNo(int lectureNo);
	// 단건리뷰조회 - 리뷰넘버
	public LectureReviewVO getReview(int reviewNo);
	

}
