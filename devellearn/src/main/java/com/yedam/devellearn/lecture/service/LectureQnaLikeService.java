package com.yedam.devellearn.lecture.service;

import java.util.List;

public interface LectureQnaLikeService {
	// 고유번호조회
	public int getNo();

	// 등록
	public int insertLectureQnaLike(LectureQnaLikeVO lectureQnaLikeVO);

	// 삭제
	public int deleteLectureQnaLike(int likeNo);

	// 전체조회 - 회원아이디로 조회(내가 좋아요한 질문) / 강의질문번호로 조회(좋아요 수 카운트) - 마이바티스 조건문 활용
	public List<LectureQnaLikeVO> getLectureQnaLikeListByMemberId(String memberId);
	
	// 좋아요 수 카운트 - 강의질문번호로 조회 - 마이바티스 조건문 활용
	public int getLectureQnaLikeCntByQnaNo(int QnaNo);

	// 단건조회 - 로그인된 회원이 좋아요 했는지 확인 - 파라미터 : {qnaNo, memberId}
	public LectureQnaLikeVO getLectureQnaLike(LectureQnaLikeVO lectureQnaLikeVO);
}
