package com.yedam.devellearn.company.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.mail.Multipart;

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
import com.yedam.devellearn.company.service.ComLikesVO;
import com.yedam.devellearn.company.service.ComReviewsVO;
import com.yedam.devellearn.company.service.CompanyService;
import com.yedam.devellearn.company.service.CompanyVO;
import com.yedam.devellearn.company.service.InterviewReviewsVO;
import com.yedam.devellearn.file.FileHandler;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CompanyController {

	@Autowired
	CompanyService service;

	// 기업리스트조회
	@PostMapping("/companyList")
	public PageInfo<CompanyVO> getList(@RequestBody Map<String, Object> map) {
		String order = (String) map.get("order");
		order += " desc";
		PageHelper.startPage((int) map.get("pageNum"), 16, order);
		return PageInfo.of(service.getList(map));
	}

	// 기업 필터링
	@PostMapping("/company/search")
	public PageInfo<CompanyVO> getCompanyFilter(@RequestBody Map<String, Object> map) {
		String order = (String) map.get("order");
		order += " desc";
		PageHelper.startPage((int) map.get("pageNum"), 16, order);
		return PageInfo.of(service.getCompanyFilter(map));
	}

	// 단건조회
	@PostMapping("/company")
	public CompanyVO getCompany(@RequestBody Map<String, Object> map) {
		return service.getCompany(map);
	}

	// 관심기업 조회
	@PostMapping("/myCompanyList")
	public PageInfo<CompanyVO> getLikeList(@RequestBody Map<String, Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 4);
		return PageInfo.of(service.getLikeList(map));
	}

	// 관심기업 등록
	@PostMapping("/likeCompany")
	public int likeCompany(@RequestBody ComLikesVO vo) {
		return service.likeCompany(vo);
	}

	// 관심기업 삭제
	@DeleteMapping("/myCompanyList")
	public int deleteLikeCompany(@RequestBody ComLikesVO vo) {
		return service.deleteLikeCompany(vo);
	}

	// 리뷰전체조회
	@GetMapping("/reviews/{comNo}/{pageNum}")
	public PageInfo<ComReviewsVO> getReviewList(@PathVariable int comNo, @PathVariable int pageNum) {
		PageHelper.startPage(pageNum, 10, "review_no desc");

		return PageInfo.of(service.getReviewList(comNo));
	}

	// 리뷰 등록
	@PostMapping("/reviews")
	public int registReview(@RequestBody ComReviewsVO vo) {
		return service.registReview(vo);
	}

	// 리뷰 수정
	@PutMapping("/reviews")
	public int updateReview(@RequestBody ComReviewsVO vo) {
		return service.updateReview(vo);
	}

	// 리뷰 삭제
	@DeleteMapping("/reviews/{reviewNo}")
	public int deleteReview(@PathVariable int reviewNo) {
		return service.deleteReview(reviewNo);
	}

	// 면접통계 조회
	@GetMapping("/interview/stat/{comNo}")
	public List<Map<String, Object>> interviewStat(@PathVariable int comNo) {
		return service.interviewStat(comNo);
	}

	// 면접후기 전체조회
	@PostMapping("/interviewList")
	public PageInfo<InterviewReviewsVO> getInterviewList(@RequestBody Map<String, Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 10, "review_no desc");
		return PageInfo.of(service.getInterviewList(map));
	}

	// 면접후기 수정
	@PutMapping("/interview")
	public int updateInterview(@RequestBody InterviewReviewsVO vo) {
		return service.updateInterview(vo);
	}

	// 면접후기 삭제
	@DeleteMapping("/interview/{reviewNo}")
	public int deleteInterview(@PathVariable int reviewNo) {
		return service.deleteInterview(reviewNo);
	}

	// 면접후기 등록
	@PostMapping("/interview")
	public int registInterview(@RequestBody InterviewReviewsVO vo) {
		return service.registInterview(vo);
	}

	// 기업정보 수정
	@PutMapping("/company")
	public int editCompany(MultipartFile image, CompanyVO vo) throws IllegalStateException, IOException {
		FileHandler fileHandler = new FileHandler();
		if(!image.isEmpty()) {
			fileHandler.deleteFile("img/company", vo.getImg());
		}

		String fileName = fileHandler.uploadFile("img/company", "company" + vo.getComNo(), image);

		vo.setImg(fileName);

		return service.editCompany(vo);
	}

}