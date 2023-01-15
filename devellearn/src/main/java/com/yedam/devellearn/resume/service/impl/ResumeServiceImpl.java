package com.yedam.devellearn.resume.service.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.yedam.devellearn.login.service.MemberVO;
import com.yedam.devellearn.recruit.service.RecruitHistoryVO;
import com.yedam.devellearn.resume.mapper.ResumeMapper;
import com.yedam.devellearn.resume.service.CareerVO;
import com.yedam.devellearn.resume.service.CertificationsVO;
import com.yedam.devellearn.resume.service.EducationsVO;
import com.yedam.devellearn.resume.service.MyprojectVO;
import com.yedam.devellearn.resume.service.ResumeService;

@Service
public class ResumeServiceImpl implements ResumeService {

	@Autowired
	ResumeMapper mapper;
	
	//주소등록
	@Override
	public int updateAddr(Map map) {
		return mapper.updateAddr(map);
	}

	//경력사항추가
	@Override
	public int insertCareer(CareerVO careerVO) {
		return mapper.insertCareer(careerVO);
	}

	//경력사항 출력
	@Override
	public List<CareerVO> selectCareer(String memberId) {
		return mapper.selectCareer(memberId);
	}
	
	//자기소개 추가
	@Override
	public int updateIntro(Map map) {
		return mapper.updateIntro(map);
	}

	//내프로젝트추가
	@Override
	public int insertMyProj(MyprojectVO myprojectVO) {
		return mapper.insertMyProj(myprojectVO);
	}


	
	// 이력서에 들어가는 전체 리스트 정보
	@Override
	public Map<String, List> getResumeInfo(MemberVO vo) {
		Map<String, List> map = new HashMap<>();
		map.put("CertList", mapper.getCertList(vo));
		map.put("CareerList", mapper.getCareerList(vo));
		map.put("EduList", mapper.getEduList(vo));
		map.put("ProjectList", mapper.getProjectList(vo));
		map.put("SkillList", mapper.getSkillList(vo));
		
		return map;
	}

	

	// 교육사항 추가
    @Override
    public int insertEdu(EducationsVO eduVO) {
        return mapper.insertEdu(eduVO);
    }

    @Override
    public int insertCerti(CertificationsVO certiVO) {
        return mapper.insertCerti(certiVO);
    }

	@Override
	public int updateMySkill(MemberVO memberVO) {
		return mapper.updateMySkill(memberVO);
	}

	@Override
	public int updateCareer(CareerVO careerVO) {
		return mapper.updateCareer(careerVO);
	}

	@Override
	public int deleteCareer(int careerNo) {
		return mapper.deleteCareer(careerNo);
	}

	@Override
	public int updateProj(MyprojectVO myprojectVO) {
		return mapper.updateProj(myprojectVO);
	}

	@Override
	public int deleteProj(int projectNo) {
		return mapper.deleteProj(projectNo);
	}

	@Override
	public int updateEdu(EducationsVO eduVO) {
		return mapper.updateEdu(eduVO);
	}

	@Override
	public int deleteEdu(int eduNo) {
		return mapper.deleteEdu(eduNo);
	}

	@Override
	public int updateCerti(CertificationsVO certiVO) {
		return mapper.updateCerti(certiVO);
	}

	@Override
	public int deleteCerti(int certiNo) {
		return mapper.deleteCerti(certiNo);
	}

	@Override
	public int resumeYes(String memberId) {
		return mapper.resumeYes(memberId);
	}
	
	@Override
	public List<CareerVO> applyCareer(CareerVO careerVO) {
		return mapper.applyCareer(careerVO);
	}

	@Override
	public List<CertificationsVO> applyCerti(CertificationsVO certiVO) {
		return mapper.applyCerti(certiVO);
	}

	@Override
	public int insertDevelProj(MyprojectVO projectVO) {
		return mapper.insertDevelProj(projectVO);
	}

	@Override
	public int insertDevelEdu(EducationsVO eduVO) {
		return mapper.insertDevelEdu(eduVO);
	}



}
