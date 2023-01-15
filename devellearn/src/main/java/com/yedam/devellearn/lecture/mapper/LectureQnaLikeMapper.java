package com.yedam.devellearn.lecture.mapper;

import java.util.List;

import com.yedam.devellearn.lecture.service.LectureQnaLikeVO;

public interface LectureQnaLikeMapper {
	// 고유번호조회
	public int getNo();

	// 등록
	public int insert(LectureQnaLikeVO lectureQnaLikeVO);

	// 삭제
	public int delete(int likeNo);

	// 전체조회 - 회원아이디로 조회(내가 좋아요한 질문)
	public List<LectureQnaLikeVO> selectAllByMemberId(String memberId);
	
	// 좋아요 수 카운트 - 강의질문번호로 조회
	public int selectAllCntByQnaNo(int QnaNo);

	// 단건조회 - 로그인된 회원이 좋아요 했는지 확인 - 파라미터 : {qnaNo, memberId}
	public LectureQnaLikeVO selectOne(LectureQnaLikeVO lectureQnaLikeVO);

}
