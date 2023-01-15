package com.yedam.devellearn.resume.mapper;

import java.util.List;
import java.util.Map;

import com.yedam.devellearn.lecture.service.LectureVO;
import com.yedam.devellearn.login.service.MemberVO;
import com.yedam.devellearn.recruit.service.RecruitHistoryVO;
import com.yedam.devellearn.resume.service.CareerVO;
import com.yedam.devellearn.resume.service.CertificationsVO;
import com.yedam.devellearn.resume.service.EducationsVO;
import com.yedam.devellearn.resume.service.MyprojectVO;

public interface ResumeMapper {

	// 1. 기본사항 저장(주소 UPDATE)
	public int updateAddr(Map map);

	// 2. 경력사항 저장
	public int insertCareer(CareerVO careerVO);

	// 교육리스트
	public List<EducationsVO> getEduList(MemberVO vo);

	// 자격증리스트
	public List<CertificationsVO> getCertList(MemberVO vo);

	// 프로젝트리스트
	public List<MyprojectVO> getProjectList(MemberVO vo);

	// 3. 경력리스트
	public List<CareerVO> getCareerList(MemberVO vo);
	
	// 스킬리스트
	public List<LectureVO> getSkillList(MemberVO vo);
	
	// 저장된 경력사항 불러오기(아이디로 조회)
	public List<CareerVO> selectCareer (String memberId);
	
	// 4. 프로젝트 (인서트)
	public int insertMyProj(MyprojectVO myprojectVO);
	
	// 5. 교육사항 (인서트)
	public int insertEdu (EducationsVO eduVO);
	
	// 6. 자격증 (인서트)
	public int insertCerti (CertificationsVO certiVO);
	
	//보유스킬 불러오기 (셀렉트)
	public int updateMySkill (MemberVO memberVO);
	
	//자기소개 저장 (자기소개 UPDATE)
	public int updateIntro (Map map);
	
	//7. 경력사항 수정(단건 수정)
	public int updateCareer (CareerVO careerVO);
	
	//8. 경력사항 삭제(단건 삭제)
	public int deleteCareer (int careerNo);
	
	//9. 프로젝트 수정(단건 수정)
	public int updateProj (MyprojectVO projectVO);
	
	//10. 프로젝트 삭제 (단건삭제)
	public int deleteProj (int projectNo);
	
	//11. 교육사항 수정(단건수정)
	public int updateEdu (EducationsVO eduVO);
	
	//12. 교육사항 삭제
	public int deleteEdu (int eduNo);
	
	//13. 자격사항 수정
	public int updateCerti (CertificationsVO certiVO);
	
	//14. 자격사항 삭제
	public int deleteCerti (int certiNo);
	
	//15. 이력서 있음으로 수정 (자기소개 UPDATE)
	public int resumeYes (String memberId);
	

	//16. 경력 신청사항 목록 조회
	public List<CareerVO> applyCareer (CareerVO careerVO);

	//17. 자격 신청 사항 목록 조회
	public List<CertificationsVO> applyCerti (CertificationsVO certiVO);

	//18. 디벨런 프로젝트 추가
	public int insertDevelProj(MyprojectVO projectVO);

	//19. 디벨런 교육사항 추가
	public int insertDevelEdu(EducationsVO eduVO);
}
