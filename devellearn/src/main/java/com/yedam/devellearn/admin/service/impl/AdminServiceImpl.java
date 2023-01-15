package com.yedam.devellearn.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.admin.mapper.AdminMapper;
import com.yedam.devellearn.admin.service.AdminService;
import com.yedam.devellearn.admin.service.MasterReqVO;
import com.yedam.devellearn.admin.service.Mymoney;
import com.yedam.devellearn.admin.service.ReportsVO;
import com.yedam.devellearn.resume.service.CareerVO;
import com.yedam.devellearn.resume.service.CertificationsVO;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminMapper mapper;
	
	@Override
	public int insertReport(ReportsVO vo) {
		return mapper.insertReport(vo);
	}

	@Override
	public Page<ReportsVO> getReportList() {
		return mapper.getReportList();
	}

	@Override
	public int handleReport(ReportsVO vo) {
		return mapper.handleReport(vo);
	}

	@Override
	public ReportsVO getReport(ReportsVO vo) {
		return mapper.getReport(vo);
	}

	@Override
	public int masterReq(MasterReqVO vo) {
		return mapper.masterReq(vo);
	}

	@Override
	public int deletePost(ReportsVO vo) {
		return mapper.deletePost(vo);
	}

	@Override
	public List<MasterReqVO> getReqList() {
		return mapper.getReqList();
	}

	@Override
	public int handleReq(MasterReqVO vo) {
		return mapper.handleReq(vo);
	}

	@Override
	public Page<Mymoney> getReturnList() {
		return mapper.getReturnList();
	}

	@Override
	public int getReturnNo() {
		return mapper.getReturnNo();
	}

	@Override
	public int regReturn(Mymoney vo) {
		return mapper.regReturn(vo);
	}

	@Override
	public Page<CareerVO> getCareerList() {
		return mapper.getCareerList();
	}

	@Override
	public Page<CertificationsVO> getCertiList() {
		return mapper.getCertiList();
	}

	@Override
	public int careerUpdate(CareerVO vo) {
		return mapper.careerUpdate(vo);
	}

	@Override
	public int certiUpdate(CertificationsVO vo) {
		return mapper.certiUpdate(vo);
	}
	
}
