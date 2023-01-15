package com.yedam.devellearn.lecture.web;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.lecture.service.LectureQnaService;
import com.yedam.devellearn.lecture.service.LectureQnaVO;

/**
 * @create 221007
 * @author 서지완
 * @title 강의질문 관련 컨트롤러
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class LectureQnaController {

	@Autowired
	LectureQnaService service;

	// 전체 강의질문 리스트 조회 - 강의번호로 조회(객체 파라미터 전달 -> 마이바티스 쿼리 사용시 필드 값 꺼내서 사용 가능)
	@PostMapping("/qnaListByLectureNo")
	public PageInfo<LectureQnaVO> getListByLectureNo(@RequestBody LectureQnaVO lectureQnaVO) {
		System.out.println("lectureQnaVO = " + lectureQnaVO);
		PageHelper.startPage(lectureQnaVO.getPageNum(), 10);
		Page<LectureQnaVO> list = service.getQnaListByLectureNo(lectureQnaVO);
		return PageInfo.of(list);
	}
	
	// 전체 강의질문 리스트 조회 - 커리큘럼번호로 조회(객체 파라미터 전달 -> 마이바티스 쿼리 사용시 필드 값 꺼내서 사용 가능)
	@PostMapping("/qnaListByCurrNo")
	public List<LectureQnaVO> getListByCurrNo(@RequestBody LectureQnaVO lectureQnaVO) {
		System.out.println("lectureQnaVO = " + lectureQnaVO);
		List<LectureQnaVO> list = service.getQnaListByCurrNo(lectureQnaVO);
		return list;
	}

	// 전체 강의질문 리스트 조회 - 회원아이디로 조회(수강자)(map 으로 전달 -> 마이바티스 쿼리 사용시 키 값 꺼내서 사용 가능)
	@GetMapping("/qnaListByLearner/{memberId}/{status}")
	public List<LectureQnaVO> getListByLearner(@PathVariable String memberId, @PathVariable String status) {
		Map<String, String> map = new HashMap<>();
		map.put("memberId", memberId);
		if (status.equals("resolved")) {
			map.put("status", "해결");
		} else if (status.equals("unresolved")) {
			map.put("status", "미해결");
		} else {
			map.put("status", "all");
		}
		List<LectureQnaVO> list = service.getQnaListByLearner(map);
		return list;
	}

	// 전체 강의질문 리스트 조회 - 회원번호로 조회(강사)(객체 파라미터 전달 -> 마이바티스 쿼리 사용시 필드 값 꺼내서 사용 가능)
	@PostMapping("/qnaListByLecturer")
	public List<LectureQnaVO> getListByLecturer(@RequestBody LectureQnaVO lectureQnaVO) {
		List<LectureQnaVO> list = service.getQnaListByLecturer(lectureQnaVO);
		return list;
	}

	// 강의질문 단건조회
	@GetMapping("/qna/{qnaNo}")
	public LectureQnaVO getQna(@PathVariable int qnaNo) {
		System.out.println("@@@강의질문 단건조회 컨틀롤러는 탐@@@");
		return service.getQna(qnaNo);
	}

	// 강의질문 등록
	@PostMapping("/qna")
	public LectureQnaVO writeQna(@RequestBody LectureQnaVO lectureQnaVO) {
		int no = service.getNo();
		lectureQnaVO.setQnaNo(no);
		service.writeQna(lectureQnaVO);
		return service.getQna(no);
	}

	// 강의질문 수정
	@PutMapping("/qna")
	public LectureQnaVO modifyQna(@RequestBody LectureQnaVO lectureQnaVO) {
		service.modifyQna(lectureQnaVO);
		return service.getQna(lectureQnaVO.getQnaNo());
	}
	
	// 강의질문 삭제
	@DeleteMapping("/deleteQna/{qnaNo}")
	public int deleteQNa(@PathVariable int qnaNo) {
		return service.deleteQna(qnaNo);
	}
	
	// 강의질문 검색 ----- lgh작성 10/23
	@PostMapping("/searchLectureQna")
	public Map<String,Object> searchQna(@RequestBody LectureQnaVO vo) {
		return service.searchQna(vo);
	}
}
