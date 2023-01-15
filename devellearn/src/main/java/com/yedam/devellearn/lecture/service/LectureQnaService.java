package com.yedam.devellearn.lecture.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

public interface LectureQnaService {
	// 고유번호 조회
	public int getNo();

	// 등록
	public void writeQna(LectureQnaVO lectureQnaVO);

	// 수정
	public void modifyQna(LectureQnaVO lectureQnaVO);

	// 삭제
	public int deleteQna(int qnaNo);

	// 전체조회 - 강의번호로 조회(객체 파라미터 전달)
	public Page<LectureQnaVO> getQnaListByLectureNo(LectureQnaVO lectureQnaVO);

	// 전체조회 - 커리큘럼번호로 조회(객체 파라미터 전달)
	public List<LectureQnaVO> getQnaListByCurrNo(LectureQnaVO lectureQnaVO);

	// 전체조회 - 회원아이디로 조회(수강자)
	public List<LectureQnaVO> getQnaListByLearner(Map<String, String> map);

	// 전체조회 - 회원아이디로 조회(강사)
	public List<LectureQnaVO> getQnaListByLecturer(LectureQnaVO lectureQnaVO);

	// 단건조회 - 고유번호로 조회
	public LectureQnaVO getQna(int qnaNo);

	// 질문검색
	public Map<String, Object> searchQna(LectureQnaVO vo);
}
