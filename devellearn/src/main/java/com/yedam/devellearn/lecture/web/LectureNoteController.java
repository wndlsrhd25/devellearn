package com.yedam.devellearn.lecture.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.devellearn.lecture.service.LectureNoteService;
import com.yedam.devellearn.lecture.service.LectureNoteVO;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class LectureNoteController {
	
	@Autowired
	LectureNoteService service;

	// 전체조회 - 회원아이디 + 커리큘럼번호로 조회
	@PostMapping("/noteList")
	public List<LectureNoteVO> getNoteList(@RequestBody LectureNoteVO lectureNoteVO) {
		System.out.println("@@@강의노트 조회 컨트롤러");
		List<LectureNoteVO> list = service.getNotesByMemberIdAndCurrNo(lectureNoteVO);
		return list;
	}
	
	// 등록 
	@PostMapping("/noteWrite")
	public LectureNoteVO noteWrite(@RequestBody LectureNoteVO lectureNoteVO) {
		int no = service.getNo();
		lectureNoteVO.setLectureNoteNo(no);
		service.insertNote(lectureNoteVO);
		return service.getNote(no);
	}

}
