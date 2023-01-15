package com.yedam.devellearn.lecture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.devellearn.lecture.mapper.MyCurriculumMapper;
import com.yedam.devellearn.lecture.service.MyCurriculumService;
import com.yedam.devellearn.lecture.service.MyCurriculumVO;

@Service
public class MyCurriculumServiceImpl implements MyCurriculumService{

	@Autowired
	MyCurriculumMapper mapper;
	
	// 등록
	@Override
	public void insertMyCurriculum(List<MyCurriculumVO> list) {
		for (MyCurriculumVO myCurriculumVO : list) {
			mapper.insert(myCurriculumVO);
		}
	}

	// 전체조회 - 강의번호로 조회
	@Override
	public List<MyCurriculumVO> getMyCurriculumListByLectureNo(MyCurriculumVO myCurriculumVO) {
		return mapper.selectAllByLectureNo(myCurriculumVO);
	}
	
	// 단건조회 - 커리큘럼번호로 조회
	@Override
	public MyCurriculumVO getMyCurriculum(MyCurriculumVO myCurriculumVO) {
		return mapper.selectOne(myCurriculumVO);
	}

	// 수정 - 수강여부(watchedAll 수정)
	@Override
	public int updateMyCurriculum(MyCurriculumVO myCurriculumVO) {
		return mapper.update(myCurriculumVO);
	}

}
