package com.yedam.devellearn.study.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.study.service.StudyVO;
import com.yedam.devellearn.study.mapper.StudyMapper;
import com.yedam.devellearn.study.service.StudyRepVO;
import com.yedam.devellearn.study.service.StudyService;


@Service
public class StudyServiceImpl implements StudyService {

	@Autowired
	StudyMapper mapper;
	
	//전체
	@Override
	public Page<StudyVO> getStudyListAll(Map<String,Object> map){
		return mapper.getStudyListAll(map);
	}
	
	// 검색
	public Page<StudyVO> getStudyFilter(Map<String,Object> map){
		return mapper.getStudyFilter(map);
	}
	
	//내글 전체
	@Override
	public Page<StudyVO> getMyStudyListAll(Map<String,Object> map){
		return mapper.getMyStudyListAll(map);
	}
	
	// 내글 검색
	public Page<StudyVO> getMyStudyFilter(Map<String,Object> map){
		return mapper.getMyStudyFilter(map);
	}
	
	//단건
	@Override
	public StudyVO getStudyDetail(int studyNo) {
		return mapper.getStudyDetail(studyNo);
	}
	
	//수정
	@Override
	public int updateOpen(StudyVO vo) {
		return mapper.updateOpen(vo);
	}
	//모집상태수정
	@Override
	public int updateStudy(StudyVO vo) {
		return mapper.updateStudy(vo);
	}
	
	//삭제
	@Override
	public int deleteStudy(int studyNo) {
		return mapper.deleteStudy(studyNo);
	}
	
	//등록
	@Override
	public int insertStudy(StudyVO vo) {
		return mapper.insertStudy(vo);
	}
	
	//댓글조회
	@Override
	public List<StudyRepVO> getRepListAll(int studyNo){
		return mapper.getRepListAll(studyNo);
	}
	
	//댓글단건
	@Override
	public StudyRepVO getRepDetail(int studyrepNo) {
		return mapper.getRepDetail(studyrepNo);
	}
	
	//댓글등록
	@Override
	public int insertRep(StudyRepVO vo) {
		return mapper.insertRep(vo);
	}
	
	//댓글삭제
	@Override
	public int deleteRep(int studyrepNo) {
		return mapper.deleteRep(studyrepNo);
	}
	
	//댓글수정
	@Override
	public int updateRep(StudyRepVO vo) {
		return mapper.updateRep(vo);
	}
	
	//조회수
	@Override
	public int studyCount(StudyVO vo) {
		return mapper.studyCount(vo);
	}
	
}
