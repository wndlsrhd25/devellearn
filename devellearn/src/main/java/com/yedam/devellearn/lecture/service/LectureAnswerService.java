package com.yedam.devellearn.lecture.service;

import java.util.List;

public interface LectureAnswerService {
	// 등록
	public void insertAnswer(LectureAnswerVO lectureAnswerVO);
	// 수정
	public int modifyAnswer(LectureAnswerVO lectureAnswerVO);
	// 삭제
	public int deleteAnswer(int answerNo);
	// 고유번호 조회
	public int getNo();
	// 전체조회 - 질문번호로 조회
	public List<LectureAnswerVO> getAnswerList(int qnaNo);
	// 단건조회
	public LectureAnswerVO getAnswer(int answerNo);
	
	
}
