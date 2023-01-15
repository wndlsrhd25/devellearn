package com.yedam.devellearn.recruit.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.yedam.devellearn.recruit.service.AvgRate;
import com.yedam.devellearn.recruit.service.Filter;
import com.yedam.devellearn.recruit.service.RecruitHistoryVO;
import com.yedam.devellearn.recruit.service.RecruitLikesVO;
import com.yedam.devellearn.recruit.service.RecruitVO;

public interface RecruitMapper {

	/*********************** 채용공고 **********************/
	// 채용공고 전체조회 0
	public Page<RecruitVO> getRecruitListAll(Map<String, Object> map);

	// 채용공고 필터 0
	public Page<RecruitVO> getRecruitFilter(Map<String, Object> map);

	// 채용공고 검색 0
	public Page<RecruitVO> searchRecruit(Map<String, Object> map);

	// 채용공고 단건조회 0
	public RecruitVO getRecruit(Map<String, Object> map);

	// 채용공고 등록 0
	public int registRecruit(RecruitVO vo);

	// 채용공고 수정 0
	public int updateRecruit(RecruitVO vo);

	// 채용공고 삭제 0
	public int deleteRecruit(int recruitNo);

	// 업계평균 평점 조회 0
	public AvgRate getAvg(Filter filter);

	/************************* 관심공고 **********************/
	// 관심공고 등록 0
	public int RecruitLike(RecruitLikesVO vo);

	// 관심공고 삭제 0
	public int deleteRecruitLike(RecruitLikesVO vo);

	// 관심공고 조회 0
	public Page<RecruitVO> getMyRecruitList(Map<String, Object> map);

	// 지원하기
	public int apply(RecruitHistoryVO vo);

	// 지원이력
	public List<RecruitHistoryVO> getApplyList(String memberId);

	// 지원취소
	public int withdrawApply(RecruitHistoryVO vo);

	// 지원자 목록 조회
	public List<RecruitHistoryVO> getApplyMember(int comNo);
	
	// 이력서 읽음
	public int readResume(int historyNo);
	
	// 지원이력조회
	public int checkApply(RecruitHistoryVO vo);
}
