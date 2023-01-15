package com.yedam.devellearn.company.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.company.mapper.CompanyMapper;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	CompanyMapper mapper;

	// 기업리스트조회
	public Page<CompanyVO> getList(Map<String, Object> map) {
		return mapper.getList(map);
	}

	// 검색
	public Page<CompanyVO> getCompanyFilter(Map<String, Object> map) {
		return mapper.getCompanyFilter(map);
	}

	// 단건조회
	public CompanyVO getCompany(Map<String, Object> map) {
		return mapper.getCompany(map);
	}

	// 관심기업 조회
	public Page<CompanyVO> getLikeList(Map<String, Object> map) {
		return mapper.getLikeList(map);
	}

	// 관심기업 삭제
	public int deleteLikeCompany(ComLikesVO vo) {
		return mapper.deleteLikeCompany(vo);
	}

	// 관심기업 등록
	public int likeCompany(ComLikesVO vo) {
		return mapper.likeCompany(vo);
	}

	// 리뷰전체조회
	@Override
	public Page<ComReviewsVO> getReviewList(int comNo) {
		return mapper.getReviewList(comNo);
	}

	// 리뷰 등록
	@Override
	public int registReview(ComReviewsVO vo) {
		return mapper.registReview(vo);
	}

	// 리뷰 수정
	@Override
	public int updateReview(ComReviewsVO vo) {
		return mapper.updateReview(vo);
	}

	// 리뷰 삭제
	@Override
	public int deleteReview(int reviewNo) {
		return mapper.deleteReview(reviewNo);
	}

	// 면접통계 조회
	@Override
	public List<Map<String, Object>> interviewStat(int comNo) {
		return mapper.interviewStat(comNo);
	}

	// 면접후기 전체조회
	@Override
	public Page<InterviewReviewsVO> getInterviewList(Map<String, Object> map) {
		return mapper.getInterviewList(map);
	}

	// 면접후기 수정
	@Override
	public int updateInterview(InterviewReviewsVO vo) {
		return mapper.updateInterview(vo);
	}

	// 면접후기 삭제
	@Override
	public int deleteInterview(int reviewNo) {
		return mapper.deleteInterview(reviewNo);
	}

	// 면접후기 등록
	@Override
	public int registInterview(InterviewReviewsVO vo) {
		return mapper.registInterview(vo);
	}

	// 기업정보 수정
	@Override
	public int editCompany(CompanyVO vo) {
		return mapper.editCompany(vo);
	}

}
