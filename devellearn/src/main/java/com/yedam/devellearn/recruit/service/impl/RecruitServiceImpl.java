package com.yedam.devellearn.recruit.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.recruit.mapper.RecruitMapper;
import com.yedam.devellearn.recruit.service.AvgRate;
import com.yedam.devellearn.recruit.service.Filter;
import com.yedam.devellearn.recruit.service.RecruitHistoryVO;
import com.yedam.devellearn.recruit.service.RecruitLikesVO;
import com.yedam.devellearn.recruit.service.RecruitService;
import com.yedam.devellearn.recruit.service.RecruitVO;

@Service
public class RecruitServiceImpl implements RecruitService {

	@Autowired
	RecruitMapper mapper;

	/************************** 채용공고 *************************/
	// 채용공고 전체조회
	@Override
	public Page<RecruitVO> getRecruitListAll(Map<String, Object> map) {
		return mapper.getRecruitListAll(map);
	}

	// 채용공고 필터
	@Override
	public Page<RecruitVO> getRecruitFilter(Map<String, Object> map) {
		return mapper.getRecruitFilter(map);
	}

	// 채용공고 검색
	@Override
	public Page<RecruitVO> searchRecruit(Map<String, Object> map) {
		return mapper.searchRecruit(map);
	}

	// 채용공고 단건조회
	@Override
	public RecruitVO getRecruit(Map<String, Object> map) {
		return mapper.getRecruit(map);
	}

	// 채용공고 등록
	@Override
	public int registRecruit(RecruitVO vo) {
		return mapper.registRecruit(vo);
	}

	// 채용공고 수정
	@Override
	public int updateRecruit(RecruitVO vo) {
		return mapper.updateRecruit(vo);
	}

	// 채용공고 삭제
	@Override
	public int deleteRecruit(int recruitNo) {
		return mapper.deleteRecruit(recruitNo);
	}

	// 업계평균 평점조회
	@Override
	public AvgRate getAvg(Filter filter) {
		return mapper.getAvg(filter);
	}

	/************************* 관심공고 **********************/
	// 관심공고 등록
	@Override
	public int RecruitLike(RecruitLikesVO vo) {
		return mapper.RecruitLike(vo);
	}

	// 관심공고 삭제
	@Override
	public int deleteRecruitLike(RecruitLikesVO vo) {
		return mapper.deleteRecruitLike(vo);
	}

	// 관심공고 조회
	@Override
	public Page<RecruitVO> getMyRecruitList(Map<String, Object> map) {
		return mapper.getMyRecruitList(map);
	}

	// 지원하기
	@Override
	public int apply(RecruitHistoryVO vo) {
		return mapper.apply(vo);
	}

	// 지원이력 리스트
	@Override
	public List<RecruitHistoryVO> getApplyList(String memberId) {
		return mapper.getApplyList(memberId);
	}

	// 지원취소
	@Override
	public int withdrawApply(RecruitHistoryVO vo) {
		return mapper.withdrawApply(vo);

	}

	// 지원자 목록 조회
	@Override
	public List<RecruitHistoryVO> getApplyMember(int comNo) {
		return mapper.getApplyMember(comNo);
	}

	// 이력서 읽음
	@Override
	public int readResume(int historyNo) {
		return mapper.readResume(historyNo);
	}

	@Override
	public int checkApply(RecruitHistoryVO vo) {
		return mapper.checkApply(vo);
	}
}
