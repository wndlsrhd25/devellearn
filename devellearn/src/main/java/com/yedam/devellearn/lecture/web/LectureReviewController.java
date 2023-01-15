package com.yedam.devellearn.lecture.web;

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

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.lecture.service.LectureReviewService;
import com.yedam.devellearn.lecture.service.LectureReviewVO;

/**
 * @create 221006
 * @author 서지완
 * @title 수강평 관련 컨트롤러
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class LectureReviewController {
	
	@Autowired
	LectureReviewService service;
	
	// 해당강의 수강평 전체 조회
	@GetMapping("/reviewList/{lectureNo}/{pageNum}")
	public PageInfo<LectureReviewVO> reviewList(@PathVariable int lectureNo,@PathVariable int pageNum) {
		PageHelper.startPage(pageNum, 10);
		Page<LectureReviewVO> list = service.getReviewListByLectureNo(lectureNo);
		list.forEach(System.out::println);
		return PageInfo.of(list);
	}
	
	// 수강평 등록
	@PostMapping("/writeReview")
	public LectureReviewVO writeReview(@RequestBody LectureReviewVO lectureReviewVO) { // json 객체를 받을 때 @RequestBody
		// 고유번호 세팅
		int no = service.getNo();
		lectureReviewVO.setReviewNo(no);
		System.out.println("lectureReviewVO = " + lectureReviewVO);
		service.reviewRegist(lectureReviewVO);
		return service.getReview(no);
	}
	
	// 수강평 삭제
	@DeleteMapping("/deleteReview/{reviewNo}")
	public int deleteReview(@PathVariable int reviewNo) {
		return service.reviewDelete(reviewNo);
	}
	
	// 수강평 수정
	@PutMapping("/updateLectureReview")
	public int updateReview(@RequestBody LectureReviewVO lectureReviewVO) {
		return service.reviewUpdate(lectureReviewVO);
		
	}
	

}
