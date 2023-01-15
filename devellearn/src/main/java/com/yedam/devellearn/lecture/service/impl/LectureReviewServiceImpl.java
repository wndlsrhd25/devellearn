package com.yedam.devellearn.lecture.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.lecture.mapper.LectureReviewMapper;
import com.yedam.devellearn.lecture.service.LectureReviewService;
import com.yedam.devellearn.lecture.service.LectureReviewVO;

@Service
public class LectureReviewServiceImpl implements LectureReviewService{

	@Autowired
	LectureReviewMapper mapper;
	
	// 고유번호 조회
	@Override
	public int getNo() {
		return mapper.getNo();
	}

	// 삽입
	@Override
	public void reviewRegist(LectureReviewVO lectureReviewVO) {
		mapper.insert(lectureReviewVO);
	}

	// 수정
	@Override
	public int reviewUpdate(LectureReviewVO lectureReviewVO) {
		return mapper.update(lectureReviewVO);
	}

	// 삭제
	@Override
	public int reviewDelete(int reviewNo) {
		return mapper.delete(reviewNo);
	}

	// 전체조회 - 해당 강의번호로 조회
	@Override
	public Page<LectureReviewVO> getReviewListByLectureNo(int lectureNo) {
		return mapper.selectAllByLectureNo(lectureNo);
	}

	// 단건조회
	@Override
	public LectureReviewVO getReview(int reviewNo) {
		return mapper.selectOne(reviewNo);
	}

}
