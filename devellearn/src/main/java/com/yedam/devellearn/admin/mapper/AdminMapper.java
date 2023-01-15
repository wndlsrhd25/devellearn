package com.yedam.devellearn.admin.mapper;

import java.util.List;

import com.github.pagehelper.Page;
import com.yedam.devellearn.admin.service.MasterReqVO;
import com.yedam.devellearn.admin.service.Mymoney;
import com.yedam.devellearn.admin.service.ReportsVO;
import com.yedam.devellearn.resume.service.CareerVO;
import com.yedam.devellearn.resume.service.CertificationsVO;

public interface AdminMapper {
	// 신고등록
	public int insertReport(ReportsVO vo);

	// 신고목록조회
	public Page<ReportsVO> getReportList();

	// 신고처리하기
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

	// 수익반환 내역 등록
	public int regReturn(Mymoney vo);

	// 경력인증 리스트
	public Page<CareerVO> getCareerList();

	// 자격인증 리스트
	public Page<CertificationsVO> getCertiList();

	// 경력인증
	public int careerUpdate(CareerVO vo);

	// 자격인증
	public int certiUpdate(CertificationsVO vo);
	
	
}
