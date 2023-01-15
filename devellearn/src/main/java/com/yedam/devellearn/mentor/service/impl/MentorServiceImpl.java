package com.yedam.devellearn.mentor.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.mentor.mapper.MentorMapper;
import com.yedam.devellearn.mentor.service.MentorRelationVo;
import com.yedam.devellearn.mentor.service.MentorRepVo;
import com.yedam.devellearn.mentor.service.MentorService;
import com.yedam.devellearn.mentor.service.MentorVO;

@Service
public class MentorServiceImpl implements MentorService{

	@Autowired
	MentorMapper mapper;
	
	//전체
	@Override
	public Page<MentorVO> getMentorListAll(Map<String,Object> map){
		return mapper.getMentorListAll(map);
	}
	//내글 전체
	@Override
	public Page<MentorVO> getMyMentorListAll(Map<String,Object> map){
		return mapper.getMyMentorListAll(map);
	}
	//멘티  전체
	@Override
	public Page<MentorRelationVo> getMentiList(Map<String,Object> map){
		return mapper.getMentiList(map);
	}
	
	// 검색
	@Override
	public Page<MentorVO> getMentorFilter(Map<String,Object> map){
		return mapper.getMentorFilter(map);
	}
	// 내글 검색
	@Override
	public Page<MentorVO> getMyMentorFilter(Map<String,Object> map){
		return mapper.getMyMentorFilter(map);
	}
	// 멘티 검색
	@Override
	public Page<MentorRelationVo> getMyMentiFilter(Map<String,Object> map){
		return mapper.getMyMentiFilter(map);
	}
	
	//단건
	@Override
	public MentorVO getMentorDetail(int mentorNo) {
		return mapper.getMentorDetail(mentorNo);
	}
	
	//수정
	@Override
	public int updateMentor(MentorVO vo) {
		return mapper.updateMentor(vo);
	}
	//멘티수정
	@Override
	public int updateMenti(MentorRelationVo vo) {
		return mapper.updateMenti(vo);
	}
	//모집상태수정
	@Override
	public int updateOpen(MentorVO vo) {
		return mapper.updateOpen(vo);
	}
	
	//삭제
	@Override
	public int deleteMentor(int mentorNo) {
		return mapper.deleteMentor(mentorNo);
	}
	
	//등록
	@Override
	public int insertMentor(MentorVO vo) {
		return mapper.insertMentor(vo);
	}
	
	//댓글조회
	@Override
	public List<MentorRepVo> getMentorRepListAll(int mentorNo){
		return mapper.getMentorRepListAll(mentorNo);
	}

	//댓글등록
	@Override
	public int insertMentorRep(MentorRepVo vo) {
		return mapper.insertMentorRep(vo);
	}
	
	//댓글삭제
	@Override
	public int deleteMentorRep(int mentorrepNo) {
		return mapper.deleteMentorRep(mentorrepNo);
	}
	
	//댓글수정
	@Override
	public int updateMentorRep(MentorRepVo vo) {
		return mapper.updateMentorRep(vo);
	}
	
	//멘토신청
	@Override
	public int enterMentor(MentorRelationVo vo) {
		return mapper.enterMentor(vo);
	}
	
	//날짜리스트
	@Override
	public List<MentorRelationVo> getMentorRelationList(MentorRelationVo vo){
		return mapper.getMentorRelationList(vo);
	}
	
	//날짜계산
	@Override
	public MentorRelationVo getMentorCount(int mentorConsNo) {
		return mapper.getMentorCount(mentorConsNo);
	}
	
	//SEK - 완료한 멘토링 수 조회 (마이페이지-학습현황)
	@Override
	public int finishMentor(MentorRelationVo vo) {
		return mapper.finishMentor(vo);
	}
}
