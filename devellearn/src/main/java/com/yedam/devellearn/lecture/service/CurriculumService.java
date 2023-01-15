package com.yedam.devellearn.lecture.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface CurriculumService  {
	// 고유번호 조회
	public int getNo();

	// 등록
	public int insertCurr(MultipartFile video, CurriculumVO curriculumVO) throws IllegalStateException, IOException ;

	// 수정
	public int updateCurr(MultipartFile video, CurriculumVO curriculumVO) throws IllegalStateException, IOException;

	// 삭제
	public void deleteCurr();

	// 전체조회 - 강의번호로 조회
	public List<CurriculumVO> getCurrListByLectureNo(int lectureNo);

	// 단건조회 - 커리큘럼번호로 조회
	public CurriculumVO getCurr(int currNo);
	
}
