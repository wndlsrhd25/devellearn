package com.yedam.devellearn.lecture.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yedam.devellearn.lecture.service.LectureLikeService;
import com.yedam.devellearn.lecture.service.LectureLikeVO;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class LectureLikeController {

	@Autowired
	LectureLikeService service;

	// 좋아요 강의 단건조회 - 회원아이디 + 강의번호로 조회
	@PostMapping("/lectureLike")
	public LectureLikeVO getLiked(@RequestBody LectureLikeVO lectureLikeVO) {
		return service.getLikedLecture(lectureLikeVO);
	}

	// 좋아요 등록
	@PostMapping("/insertLiked")
	public LectureLikeVO insertLiked(@RequestBody LectureLikeVO lectureLikeVO) {
		int no = service.getNo();
		lectureLikeVO.setLikeNo(no);
		int insertNo = service.insertLikedLecture(lectureLikeVO);
		System.out.println("insertNo = " + insertNo);
		return service.getLikedLecture(lectureLikeVO);
		
	}

	// 좋아요 취소
	@DeleteMapping("/deleteLiked/{likeNo}")
	public int deleteLiked(@PathVariable int likeNo) {
		return service.deleteLikedLecture(likeNo);
	}
}
