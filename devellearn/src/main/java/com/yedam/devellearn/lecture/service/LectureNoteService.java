package com.yedam.devellearn.lecture.service;

import java.util.List;

public interface LectureNoteService {
	// 고유번호조회
	public int getNo();
	// 등록
	public void insertNote(LectureNoteVO lectureNoteVO);
	// 수정
	public LectureNoteVO updateNote(LectureNoteVO lectureNoteVO);
	// 삭제
	public int deleteNote(int lectureNoteNo);
	// 전체조회 - 회원아이디 + 커리큘럼번호로 조회
	public List<LectureNoteVO> getNotesByMemberIdAndCurrNo(LectureNoteVO lectureNoteVO);
	// 전체조회 - 회원아이디로 조회
	public List<LectureNoteVO> getNotesByMemberId(String memberId);
	// 단건조회 
	public LectureNoteVO getNote(int lectureNoteNo);
}
