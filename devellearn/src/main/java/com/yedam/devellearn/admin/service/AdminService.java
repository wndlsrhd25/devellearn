package com.yedam.devellearn.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.resume.service.CareerVO;
import com.yedam.devellearn.resume.service.CertificationsVO;

@Service
public interface AdminService {
	// 신고등록
	public int insertReport(ReportsVO vo);

	// 신고목록
	public Page<ReportsVO> getReportList();

	// 신고처리
	public int handleReport(ReportsVO vo);

	// 신고글내용 조회
	public ReportsVO getReport(ReportsVO vo);

	// 신고글 삭제
	public int deletePost(ReportsVO vo);

	// 고수신청
	public int masterReq(MasterReqVO vo);

	// 고수신청 리스트
	public List<MasterReqVO> getReqList();

	// 고수신청 처리
	public int handleReq(MasterReqVO vo);

	// 수익반환대상 리스트
	public Page<Mymoney> getReturnList();

	// 수익반환 번호조회
	public int getReturnNo();

	// 수익반환내역 등록
	public int regReturn(Mymoney vo);

	// 경력인증 신청리스트
	public Page<CareerVO> getCareerList();

	// 자격인증 신청리스트
	public Page<CertificationsVO> getCertiList();

	// 경력인증
	public int careerUpdate(CareerVO vo);

	// 자격인증
	public int certiUpdate(CertificationsVO vo);


}
