package com.yedam.devellearn.resume.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.login.service.MemberVO;
import com.yedam.devellearn.resume.service.CareerVO;
import com.yedam.devellearn.resume.service.CertificationsVO;
import com.yedam.devellearn.resume.service.EducationsVO;
import com.yedam.devellearn.resume.service.MyprojectVO;
import com.yedam.devellearn.resume.service.ResumeService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ResumeController {

	@Autowired
	ResumeService service;

	// 기본사항 저장(주소 UPDATE)
	@PutMapping("/updateAddr")
	public int updateAddr(@RequestBody Map map) {
		return service.updateAddr(map);
	}

	// 경력사항 저장
	@PostMapping("/insertCareer")
	public int insertCareer(MultipartFile imgFiles, CareerVO careerVO) throws IllegalStateException, IOException {

		// 파일등록하기
		FileHandler fileHandler = new FileHandler();
		String fileName =  fileHandler.uploadFile("resume", "career" + careerVO.getCareerNo(), imgFiles); 
		careerVO.setCertiFile(fileName);
		System.out.println("경력사항 저장 시 들어가는 이름"+fileName);
		
		return service.insertCareer(careerVO);
				
	}

	// 경력사항 불러오기
	@GetMapping("/selectCareer")
	public List<CareerVO> selectCareer(@RequestParam(value = "memberId", required = false) String memberId) {
		System.out.println(memberId);
		return service.selectCareer(memberId);
	}
	// get은 리퀘스트바디에 보내는게 아님.

	// 경력사항 수정
	@PutMapping("/updateCareer")
	public int updateCareer(MultipartFile imgFiles, CareerVO careerVO) throws IllegalStateException, IOException {

		FileHandler fileHandler = new FileHandler();
		String fileName = fileHandler.uploadFile("resume", "career" + careerVO.getCareerNo(), imgFiles);
		fileHandler.deleteFile("resume", careerVO.getCertiFile());
		careerVO.setCertiFile(fileName);
		System.out.println(careerVO.toString());

		return service.updateCareer(careerVO);
	}

	// 경력사항 삭제
	@DeleteMapping("/deleteCareer/{careerNo}")
	public int deleteCareer(@PathVariable int careerNo) {
		return service.deleteCareer(careerNo);
	}

	// 프로젝트 인서트

	@PostMapping("/insertMyProj")
	public int insertMyProj(@RequestBody MyprojectVO myprojectVO) {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaa" + myprojectVO);
		return service.insertMyProj(myprojectVO);
	}

	// 프로젝트 수정
	@PutMapping("/updateProj")
	public int updateProj(@RequestBody MyprojectVO myprojectVO) throws IllegalStateException, IOException {
		return service.updateProj(myprojectVO);
	}

	// 프로젝트 삭제
	@DeleteMapping("/deleteProj/{projectNo}")
	public int deleteProj(@PathVariable int projectNo) {
		return service.deleteProj(projectNo);
	}

	// 교육사항 인서트
	@PostMapping("/insertEdu")
	public int insertEdu(@RequestBody EducationsVO eduVO) {
		return service.insertEdu(eduVO);
	}

	// 자격증 인서트
	@PostMapping("/insertCerti")
	public int insertCerti(MultipartFile imgFiles, CertificationsVO certiVO) throws IllegalStateException, IOException {
		FileHandler fileHandler = new FileHandler();
		String fileName = fileHandler.uploadFile("resume", "certi" + certiVO.getCertiNo(), imgFiles);

		certiVO.setCertiFile(fileName);
		System.out.println(certiVO.toString());

		return service.insertCerti(certiVO);
	}

	// 교육사항 수정

	@PutMapping("/updateEdu")
	public int updateEdu(@RequestBody EducationsVO eduVO) throws IllegalStateException, IOException {
		return service.updateEdu(eduVO);
	}

	// 교육사항 삭제
	@DeleteMapping("/deleteEdu/{eduNo}")
	public int deleteEdu(@PathVariable int eduNo) {
		return service.deleteEdu(eduNo);
	}

	// 자격증 사항 수정

	@PutMapping("/updateCerti")
	public int updateCerti(MultipartFile imgFiles, CertificationsVO certiVO) throws IllegalStateException, IOException {

		FileHandler fileHandler = new FileHandler();
		String fileName = fileHandler.uploadFile("resume", "certi" + certiVO.getCertiNo(), imgFiles);
		fileHandler.deleteFile("resume", certiVO.getCertiFile());
		certiVO.setCertiFile(fileName);
		System.out.println(certiVO.toString());

		return service.updateCerti(certiVO);
	}

	// 자격증 사항 삭제

	@DeleteMapping("/deleteCerti/{certiNo}")
	public int deleteCerti(@PathVariable int certiNo) {
		return service.deleteCerti(certiNo);
	}

	// 이력서에 들어갈 내용리스트
	@PostMapping("/getResumeList")
	public Map<String, List> getResumeInfo(@RequestBody MemberVO vo) {
		return service.getResumeInfo(vo);
	}

	// 보유스킬 업데이트
	@PutMapping("/updateMySkill")
	public int updateMySkill(@RequestBody MemberVO vo) {
		return service.updateMySkill(vo);
	}

	// 자기소개 저장 (자기소개 UPDATE)
	@PutMapping("/updateIntro")
	public int updateIntro(@RequestBody Map map) {
		return service.updateIntro(map);
	}

	// 15. 이력서 있음으로 수정 (자기소개 UPDATE)
	@PutMapping("/resumeYes/{memberId}")
	public int resumeYes(@PathVariable String memberId) {
		return service.resumeYes(memberId);
	}

	// 16. 경력 신청사항 목록 조회
	@PostMapping("/applyCareer")
	public List<CareerVO> applyCareer(@RequestBody CareerVO careerVO) {
		return service.applyCareer(careerVO);
	}

	// 17. 자격 신청 사항 목록 조회
	@PostMapping("/applyCerti")
	public List<CertificationsVO> applyCerti(@RequestBody CertificationsVO certiVO) {
		return service.applyCerti(certiVO);
	}


	// 18. 디벨런 프로젝트 추가

	@PostMapping("/insertDevelProj")
	public int insertDevelProj(@RequestBody MyprojectVO myprojectVO) {
		return service.insertDevelProj(myprojectVO);
	}

	// 19. 디벨런 교육사항 추가
	@PostMapping("/insertDevelEdu")
	public int insertDevelEdu(@RequestBody EducationsVO eduVO) {
		return service.insertDevelEdu(eduVO);
	}
}
