package com.yedam.devellearn.lecture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.devellearn.lecture.mapper.LectureQnaLikeMapper;
import com.yedam.devellearn.lecture.service.LectureQnaLikeService;
import com.yedam.devellearn.lecture.service.LectureQnaLikeVO;

@Service
public class LectureQnaLikeServiceImpl implements LectureQnaLikeService{

	@Autowired
	LectureQnaLikeMapper mapper;
	
	// 고유번호조회
	@Override
	public int getNo() {
		return mapper.getNo();
	}

	// 등록
	@Override
	public int insertLectureQnaLike(LectureQnaLikeVO lectureQnaLikeVO) {
		return mapper.insert(lectureQnaLikeVO);
	}

	// 삭제
	@Override
	public int deleteLectureQnaLike(int likeNo) {
		return mapper.delete(likeNo);
	}

	// 전체조회 - 회원아이디로 조회(내가 좋아요한 질문)
	@Override
	public List<LectureQnaLikeVO> getLectureQnaLikeListByMemberId(String memberId) {
		return mapper.selectAllByMemberId(memberId);
	}
	
	// 좋아요 수 카운트 - 강의질문번호로 조회
	@Override
	public int getLectureQnaLikeCntByQnaNo(int QnaNo) {
		return mapper.selectAllCntByQnaNo(QnaNo);
	}

	// 단건조회 - 로그인된 회원이 좋아요 했는지 확인 - 파라미터 : {qnaNo, memberId}
	@Override
	public LectureQnaLikeVO getLectureQnaLike(LectureQnaLikeVO lectureQnaLikeVO) {
		return mapper.selectOne(lectureQnaLikeVO);
	}

	

}
