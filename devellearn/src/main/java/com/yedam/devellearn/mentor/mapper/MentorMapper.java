package com.yedam.devellearn.mentor.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.yedam.devellearn.mentor.service.MentorRelationVo;
import com.yedam.devellearn.mentor.service.MentorRepVo;
import com.yedam.devellearn.mentor.service.MentorVO;


public interface MentorMapper {
	// 전체조회
	public Page<MentorVO> getMentorListAll(Map<String,Object> map);
	// 내글 전체조회
	public Page<MentorVO> getMyMentorListAll(Map<String,Object> map);
	// 멘티  전체조회
	public Page<MentorRelationVo> getMentiList(Map<String,Object> map);
	
	// 검색
	public Page<MentorVO> getMentorFilter(Map<String,Object> map);
	// 내글 검색
	public Page<MentorVO> getMyMentorFilter(Map<String,Object> map);
	// 멘티 검색
	public Page<MentorRelationVo> getMyMentiFilter(Map<String,Object> map);
	
	//단건
	public MentorVO getMentorDetail(int mentorNo);
	
	//수정
	public int updateMentor(MentorVO vo);
	//멘티상태수정
	public int updateMenti(MentorRelationVo vo);
	//모집상태수정
	public int updateOpen(MentorVO vo);
	
	//삭제
	public int deleteMentor(int mentorNo);
	
	//등록
	public int insertMentor(MentorVO vo);
	
	//댓글조회
	public List<MentorRepVo> getMentorRepListAll(int mentorNo);
	
	//댓글등록
	public int insertMentorRep(MentorRepVo vo);
	
	//댓글삭제
	public int deleteMentorRep(int mentorrepNo);
	
	//댓글수정
	public int updateMentorRep(MentorRepVo vo);
	
	//멘토신청
	public int enterMentor(MentorRelationVo vo);
	
	//날짜리스트
	public List<MentorRelationVo> getMentorRelationList(MentorRelationVo vo);
	
	//날짜계산
	public MentorRelationVo getMentorCount(int mentorConsNo);
	
	//SEK - 완료한 멘토링 수 조회 (마이페이지-학습현황)
	public int finishMentor(MentorRelationVo vo);

}
