package com.yedam.devellearn.lecture.service;

import java.util.List;

public interface MyCurriculumService {
	
	// 등록
	public void insertMyCurriculum(List<MyCurriculumVO> list);

	// 전체조회 - 강의번호로 조회
	public List<MyCurriculumVO> getMyCurriculumListByLectureNo(MyCurriculumVO myCurriculumVO);

	// 단건조회 - 커리큘럼번호로 조회
	public MyCurriculumVO getMyCurriculum(MyCurriculumVO myCurriculumVO);
	
	// 수정 - 수강여부(watchedAll 수정)
	public int updateMyCurriculum(MyCurriculumVO myCurriculumVO);
}
