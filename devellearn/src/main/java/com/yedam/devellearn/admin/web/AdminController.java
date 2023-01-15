package com.yedam.devellearn.admin.web;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.admin.service.AdminService;
import com.yedam.devellearn.admin.service.MasterReqVO;
import com.yedam.devellearn.admin.service.Mymoney;
import com.yedam.devellearn.admin.service.ReportsVO;
import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.resume.service.CareerVO;
import com.yedam.devellearn.resume.service.CertificationsVO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	AdminService service;

	// 신고하기
	@PostMapping("/report")
	public int insertReport(@RequestBody ReportsVO vo) {
		return service.insertReport(vo);
	}

	// 신고목록
	@GetMapping("/report/{pageNum}")
	public PageInfo<ReportsVO> getReportList(@PathVariable int pageNum) {
		PageHelper.startPage(pageNum, 10);
		return PageInfo.of(service.getReportList());
	}

	// 신고처리
	@PutMapping("/report")
	public int handleReport(@RequestBody ReportsVO vo) {
		return service.handleReport(vo);
	}

	// 신고글조회
	@PostMapping("/reportContent")
	public ReportsVO getReport(@RequestBody ReportsVO vo) {
		return service.getReport(vo);
	}

	// 신고글 삭제
	@DeleteMapping("/report/{colname}/{postCode}/{postNo}")
	public int deletePost(@PathVariable String colname, @PathVariable String postCode, @PathVariable int postNo) {
		ReportsVO vo = new ReportsVO();
		vo.setColname(colname);
		vo.setPostCode(postCode);
		vo.setPostNo(postNo);
		return service.deletePost(vo);
	}

	// 고수신청
	@PostMapping("/masterReq")
	public int masterReq(MultipartFile files, MasterReqVO vo) throws IllegalStateException, IOException {
		FileHandler fileHandler = new FileHandler();
		String fileName = fileHandler.uploadFile("req", "masterReq" + vo.getMemberId(), files);

		vo.setFileName(fileName);

		return service.masterReq(vo);
	}

	// 고수신청리스트
	@GetMapping("/masterReq")
	public List<MasterReqVO> getReqList() {
		return service.getReqList();
	}

	// 고수 신청처리
	@PutMapping("/masterReq")
	public int handleReq(@RequestBody MasterReqVO vo) {
		return service.handleReq(vo);
	}

	// 수익반환 대상조회
	@GetMapping("/getReturnList/{pageNum}")
	public PageInfo<Mymoney> getReturnList(@PathVariable int pageNum) {
		PageHelper.startPage(pageNum, 10);
		return PageInfo.of(service.getReturnList());
	}

	// 수익금반환 번호조회
	@GetMapping("/getReturn")
	public int getReturnNo() {
		return service.getReturnNo();
	}

	// 수익반환내역 등록
	@PutMapping("/regReturn")
	public int regReturn(@RequestBody Mymoney vo) {
		return service.regReturn(vo);
	}

	// 경력인증리스트
	@GetMapping("/careerList/{pageNum}")
	public PageInfo<CareerVO> getCareerList(@PathVariable int pageNum) {
		PageHelper.startPage(pageNum, 10);
		return PageInfo.of(service.getCareerList());
	}

	// 자격인증리스트
	@GetMapping("/certiList/{pageNum}")
	public PageInfo<CertificationsVO> getCertiList(@PathVariable int pageNum) {
		PageHelper.startPage(pageNum, 10);
		return PageInfo.of(service.getCertiList());
	}

	// 경력인증
	@PutMapping("/careerList")
	public int careerUpdate(@RequestBody CareerVO vo) {
		return service.careerUpdate(vo);
	}

	// 자격인증
	@PutMapping("/certiList")
	public int certiUpdate(@RequestBody CertificationsVO vo) {
		return service.certiUpdate(vo);
	}

}
