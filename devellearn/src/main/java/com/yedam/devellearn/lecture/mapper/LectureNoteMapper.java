package com.yedam.devellearn.lecture.mapper;

import java.util.List;

import com.yedam.devellearn.lecture.service.LectureNoteVO;

public interface LectureNoteMapper {
	// 고유번호조회
	public int getNo();
	// 등록
	public void insert(LectureNoteVO lectureNoteVO);
	// 수정
	public LectureNoteVO update(LectureNoteVO lectureNoteVO);
	// 삭제
	public int delete(int lectureNoteNo);
	// 전체조회 - 회원아이디 + 커리큘럼번호로 조회
	public List<LectureNoteVO> selectAllByMemberIdAndCurrNo(LectureNoteVO lectureNoteVO);
	// 전체조회 - 회원아이디로 조회
	public List<LectureNoteVO> selectAllByMemberId(String memberId);
	// 단건조회 
	public LectureNoteVO selectOne(int lectureNoteNo);
	
}
