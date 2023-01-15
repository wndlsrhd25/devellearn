package com.yedam.devellearn.study.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.yedam.devellearn.study.service.StudyVO;
import com.yedam.devellearn.study.service.StudyRepVO;



public interface StudyMapper {

	// 전체조회
	public Page<StudyVO> getStudyListAll(Map<String,Object> map);
	// 내글 전체조회
	public Page<StudyVO> getMyStudyListAll(Map<String,Object> map);
	
	// 검색
	public Page<StudyVO> getStudyFilter(Map<String,Object> map);
	// 내글 검색
	public Page<StudyVO> getMyStudyFilter(Map<String,Object> map);
	
	//단건
	public StudyVO getStudyDetail(int studyNo);
	
	//수정
	public int updateStudy(StudyVO vo);
	//모집상태수정
	public int updateOpen(StudyVO vo);
	
	//삭제
	public int deleteStudy(int studyNo);
	
	//등록
	public int insertStudy(StudyVO vo);
	
	//댓글조회
	public List<StudyRepVO> getRepListAll(int studyNo);
	
	//단건
	public StudyRepVO getRepDetail(int studyrepNo);
	
	//댓글등록
	public int insertRep(StudyRepVO vo);
	
	//댓글삭제
	public int deleteRep(int studyrepNo);
	
	//댓글수정
	public int updateRep(StudyRepVO vo);
	
	//조회수
	public int studyCount(StudyVO vo);
}
