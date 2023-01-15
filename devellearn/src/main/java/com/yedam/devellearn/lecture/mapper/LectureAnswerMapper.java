package com.yedam.devellearn.lecture.mapper;

import java.util.List;

import com.yedam.devellearn.lecture.service.LectureAnswerVO;

public interface LectureAnswerMapper {
	// 등록
	public void insert(LectureAnswerVO lectureAnswerVO);
	// 수정
	public int update(LectureAnswerVO lectureAnswerVO);
	// 삭제
	public int delete(int answerNo);
	// 고유번호 조회
	public int getNo();
	// 전체조회 - 질문번호로 조회
	public List<LectureAnswerVO> selectAllByQnaNo(int qnaNo);
	// 단건조회
	public LectureAnswerVO selectOne(int answerNo);
}
