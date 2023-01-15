package com.yedam.devellearn.company.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;

@Service
public interface CompanyService {
	// 기업리스트조회
	public Page<CompanyVO> getList(Map<String, Object> map);

	// 단건조회
	public CompanyVO getCompany(Map<String, Object> map);

	// 관심기업 조회
	public Page<CompanyVO> getLikeList(Map<String, Object> map);

	// 리뷰 전체조회
	public Page<ComReviewsVO> getReviewList(int comNo);

	// 리뷰등록
	public int registReview(ComReviewsVO vo);

	// 리뷰수정
	public int updateReview(ComReviewsVO vo);

	// 리뷰삭제
	public int deleteReview(int reviewNo);

	// 관심기업 삭제
	public int deleteLikeCompany(ComLikesVO vo);

	// 관심기업 등록
	public int likeCompany(ComLikesVO vo);

	// 검색
	public Page<CompanyVO> getCompanyFilter(Map<String, Object> map);

	// 면접통계조회
	public List<Map<String, Object>> interviewStat(int comNo);

	// 면접후기 전체조회
	public Page<InterviewReviewsVO> getInterviewList(Map<String, Object> map);

	// 면접후기 수정
	public int updateInterview(InterviewReviewsVO vo);

	// 면접후기 삭제
	public int deleteInterview(int reviewNo);

	// 면접후기 등록
	public int registInterview(InterviewReviewsVO vo);
	
	// 기업정보 수정
	public int editCompany(CompanyVO vo);
}
