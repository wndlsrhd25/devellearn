package com.yedam.devellearn.recruit.web;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.recruit.service.AvgRate;
import com.yedam.devellearn.recruit.service.Filter;
import com.yedam.devellearn.recruit.service.RecruitHistoryVO;
import com.yedam.devellearn.recruit.service.RecruitLikesVO;
import com.yedam.devellearn.recruit.service.RecruitService;
import com.yedam.devellearn.recruit.service.RecruitVO;

@CrossOrigin(origins = "*")
@RestController
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
@RequestMapping("/api")
public class RecruitController {

	@Autowired
	RecruitService service;

	// 전체조회
	@PostMapping("/recruitList")
	public PageInfo<RecruitVO> getRecruitListAll(@RequestBody Map<String, Object> map) {
		String order = (String) map.get("order");
		if (!order.equals("end_date")) {
			order += " desc";
		}
		PageHelper.startPage((int) map.get("pageNum"), 16, order);
		return PageInfo.of(service.getRecruitListAll(map));
	}

	// 단건조회
	@PostMapping("/recruit/detail")
	public RecruitVO getRecruit(@RequestBody Map<String, Object> map) {
		return service.getRecruit(map);
	}

	// 채용공고 필터링
	@PostMapping("/recruit/search")
	public PageInfo<RecruitVO> getRecruitFilter(@RequestBody Map<String, Object> map) {
		String order = (String) map.get("order");
		if (!order.equals("end_date")) {
			order += " desc";
		}
		PageHelper.startPage((int) map.get("pageNum"), 16, order);
		return PageInfo.of(service.getRecruitFilter(map));
	}

	// 채용공고 등록
	@PostMapping("/recruit")
	// MultipartFile image는 사진파일
	// RecruitVO vo는 객체 받는거
	public int registRecruit(MultipartFile image, RecruitVO vo) throws IllegalStateException, IOException {
		FileHandler filehandler = new FileHandler();
		String fileName = filehandler.uploadFile("img/company", "recruit" + vo.getRecruitNo(), image);
		vo.setImg(fileName);

		return service.registRecruit(vo);
	}

	// 채용공고 수정
	@PutMapping("/recruit")
	public int updateRecruit(MultipartFile image, RecruitVO vo) throws IllegalStateException, IOException {
		// 기존파일삭제
		FileHandler filehandler = new FileHandler();
		if(!image.isEmpty()) {
			filehandler.deleteFile("img/company", vo.getImg());
		}
		
		// 새로운 파일 업로드
		String fileName = filehandler.uploadFile("img/company", "recruit" + vo.getRecruitNo(), image);
		vo.setImg(fileName);
		return service.updateRecruit(vo);
	}

	// 채용공고 삭제
	@DeleteMapping("/recruit/{recruitNo}")
	public int deleteRecruit(@PathVariable int recruitNo) {
		return service.deleteRecruit(recruitNo);
	}

	// 업계평균
	@PostMapping("/recruit/avg")
	public AvgRate getAvg(@RequestBody Filter filter) {
		return service.getAvg(filter);
	}

	// 지원하기
	@PutMapping("/apply")
	public int apply(MultipartFile pdf, RecruitHistoryVO vo) throws IllegalStateException, IOException {
		FileHandler filehandler = new FileHandler();
		String fileName = filehandler.uploadFile("resume", "resume" + vo.getMemberId(), pdf);
		vo.setResume(fileName);

		return service.apply(vo);
	}

	// 지원자 목록 조회
	@GetMapping("/applyMember/{comNo}")
	public List<RecruitHistoryVO> getApplyMember(@PathVariable int comNo) {
		return service.getApplyMember(comNo);
	}

	// 지원이력 리스트
	@GetMapping("/applyList/{memberId}")
	public List<RecruitHistoryVO> getApplyList(@PathVariable String memberId) {
		return service.getApplyList(memberId);
	}

	// 지원취소
	@PostMapping("/applyList")
	public int withdrawApply(@RequestBody RecruitHistoryVO vo) {
		return service.withdrawApply(vo);
	}

	// 이력서 읽음처리
	@PutMapping("readResume/{historyNo}")
	public int readResume(@PathVariable int historyNo) {
		return service.readResume(historyNo);
	}

	// 지원이력조회
	@PostMapping("/checkApply")
	public int checkApply(@RequestBody RecruitHistoryVO vo) {
		return service.checkApply(vo);
	}

	/******************* 관심공고 ************************/

	// 관심공고 등록
	@PostMapping("/likeRecruit")
	public int likeRecruit(@RequestBody RecruitLikesVO vo) {
		return service.RecruitLike(vo);
	}

	// 관심공고 삭제
	@DeleteMapping("/deleteRecruitLike")
	public int deleteRecruitLike(@RequestBody RecruitLikesVO vo) {
		return service.deleteRecruitLike(vo);
	}

	// 관심공고 조회
	@PostMapping("/myRecruit")
	public PageInfo<RecruitVO> getMyRecruit(@RequestBody Map<String, Object> map) {
		String order = "like_no desc";
		PageHelper.startPage((int) map.get("pageNum"), 4, order);
		return PageInfo.of(service.getMyRecruitList(map));
	}

}