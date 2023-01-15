package com.yedam.devellearn.lecture.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yedam.devellearn.lecture.service.LectureQnaLikeService;
import com.yedam.devellearn.lecture.service.LectureQnaLikeVO;


/**
 * @create 221014
 * @author 서지완
 * @Title 강의질문좋아요기능 관련 컨트롤러
 *
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class LectureQnaLikeController {

	@Autowired
	LectureQnaLikeService service;
	
	// 단건조회 - 로그인된 회원이 좋아요 했는지 확인 - 파라미터 : {qnaNo, memberId}
	@PostMapping("/lectureQnaLike")
	public LectureQnaLikeVO getLectureQnaLike(@RequestBody LectureQnaLikeVO lectureQnaLikeVO) {
		return service.getLectureQnaLike(lectureQnaLikeVO);
	}
	
	// 좋아요 등록
	@PostMapping("/insertLectureQnaLike")
	public LectureQnaLikeVO insertLectureQnaLike(@RequestBody LectureQnaLikeVO lectureQnaLikeVO) {
		int no = service.getNo();
		lectureQnaLikeVO.setLikeNo(no);
		service.insertLectureQnaLike(lectureQnaLikeVO);
		return service.getLectureQnaLike(lectureQnaLikeVO);
	}
	
	// 좋아요 삭제
	@DeleteMapping("/deleteLectureQnaLike/{likeNo}") 
	public int deleteLectureQnaLike(@PathVariable int likeNo) {
		return service.deleteLectureQnaLike(likeNo);
	}
	
	// 좋아요 수 카운트 - 강의질문번호로 조회
	@GetMapping("/lectureQnaLikeCnt/{qnaNo}")
	public int lectureQnaLikeCnt(@PathVariable int qnaNo) {
		return service.getLectureQnaLikeCntByQnaNo(qnaNo);
	}
}
