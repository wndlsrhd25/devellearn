package com.yedam.devellearn.lecture.mapper;

import java.util.List;

import com.yedam.devellearn.lecture.service.CurriculumVO;

public interface CurriculumMapper {
	// 고유번호 조회
	public int getNo();

	// 등록
	public int insert(CurriculumVO curriculumVO);

	// 수정
	public int update(CurriculumVO curriculumVO);

	// 삭제
	public void delete();

	// 전체조회 - 강의번호로 조회
	public List<CurriculumVO> selectAllByLectureNo(int lectureNo);

	// 단건조회 - 커리큘럼번호로 조회
	public CurriculumVO selectOneCurr(int currNo);

}
