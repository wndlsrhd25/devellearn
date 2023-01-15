package com.yedam.devellearn.lecture.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.yedam.devellearn.lecture.service.LectureQnaVO;

public interface LectureQnaMapper {
	// 고유번호 조회
	public int getNo();

	// 등록
	public void insert(LectureQnaVO lectureQnaVO);

	// 수정
	public void update(LectureQnaVO lectureQnaVO);

	// 삭제
	public int delete(int qnaNo);

	// 전체조회 - 강의번호로 조회(객체 파라미터 전달)
	public Page<LectureQnaVO> selectAllByLectureNo(LectureQnaVO lectureQnaVO);

	// 전체조회 - 커리큘럼번호로 조회(객체 파라미터 전달)
	public List<LectureQnaVO> selectAllByCurrNo(LectureQnaVO lectureQnaVO);

	// 검색
	public List<LectureQnaVO> searchLectureQna(LectureQnaVO lectureQnaVO);

	// 전체조회 - 회원아이디로 조회(수강자)
	public List<LectureQnaVO> selectAllByLearner(Map<String, String> map);

	// 전체조회 - 회원아이디로 조회(수강자)
	public List<LectureQnaVO> selectAllByLecturer(LectureQnaVO lectureQnaVO);

	// 단건조회 - 고유번호로 조회
	public LectureQnaVO selectOne(int qnaNo);

}
