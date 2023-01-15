package com.yedam.devellearn.lecture.mapper;

import com.github.pagehelper.Page;
import com.yedam.devellearn.lecture.service.LectureReviewVO;

public interface LectureReviewMapper {
	// 고유번호조회
	public int getNo();
	// 삽입
	public void insert(LectureReviewVO lectureReviewVO);
	// 수정
	public int update(LectureReviewVO lectureReviewVO);
	// 삭제
	public int delete(int reviewNo);
	// 전체조회
	public Page<LectureReviewVO> selectAllByLectureNo(int lectureNo);
	// 단건조회
	public LectureReviewVO selectOne(int reviewNo);
}
