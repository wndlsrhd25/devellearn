package com.yedam.devellearn.lecture.mapper;

import java.util.List;

import com.yedam.devellearn.lecture.service.MyCurriculumVO;

public interface MyCurriculumMapper {
	// 등록
	public void insert(MyCurriculumVO myCurriculumVO);

	// 전체조회 - 강의번호로 조회
	public List<MyCurriculumVO> selectAllByLectureNo(MyCurriculumVO MyCurriculumVO);

	// 단건조회 - 커리큘럼번호로 조회
	public MyCurriculumVO selectOne(MyCurriculumVO myCurriculumVO);
	
	// 수정 - 수강여부(watchedAll 수정)
	public int update(MyCurriculumVO myCurriculumVO);
}
