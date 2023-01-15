package com.yedam.devellearn.lecture.web;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.devellearn.lecture.service.LectureAnswerService;
import com.yedam.devellearn.lecture.service.LectureAnswerVO;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class LectureAnswerController {

	@Autowired
	LectureAnswerService service;
	
	// 질문답변 전체 조회 - 질문번호로 조회
	@GetMapping("/answerList/{qnaNo}")
	public List<LectureAnswerVO> getList(@PathVariable int qnaNo) {
		return service.getAnswerList(qnaNo);
	}
	
	// 질문답변 단건 조회
	@GetMapping("/lectureAnswer/{answerNo}")
	public LectureAnswerVO getLectureAnswer(@PathVariable int answerNo) {
		return service.getAnswer(answerNo);
	}
	
	// 질문답변 등록
	@PostMapping("/writeAnswer")
	public LectureAnswerVO writeAnswer(@RequestBody LectureAnswerVO lectureAnswerVO) {
		// 고유번호 세팅
		int no = service.getNo();
		lectureAnswerVO.setAnswerNo(no);
		System.out.println("lectureAnswerVO = " + lectureAnswerVO);
		// 질문답변 db 등록
		service.insertAnswer(lectureAnswerVO);
		return service.getAnswer(no);
	}
	
	// 질문답변 삭제
	@DeleteMapping("/deleteAnswer/{answerNo}")
	public int deleteAnswer(@PathVariable int answerNo) {
		return service.deleteAnswer(answerNo);
	}
	
	// 질문답변 수정
	@PutMapping("/updateAnswer")
	public int updateAnswer(@RequestBody LectureAnswerVO lectureAnswerVO) {
		return service.modifyAnswer(lectureAnswerVO);
	}
}
