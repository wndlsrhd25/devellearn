package com.yedam.devellearn.lecture.web;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yedam.devellearn.lecture.service.MyLectureService;
import com.yedam.devellearn.lecture.service.MyLectureVO;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class MyLectureController {

	@Autowired
	MyLectureService service;

	// 수정 - latestWatchedCurr / latestWatctedTime / totalWatchedTime
	@PutMapping("/updateMyLecture")
	public int updateMyLecture(@RequestBody MyLectureVO myLectureVO) {
		return service.updateMyLecture(myLectureVO);
	}

	// 수강완료시 완료로 변경
	@PutMapping("/updateToComplete")
	public int updateToComplete(@RequestBody MyLectureVO myLectureVO) {
		return service.updateToComplete(myLectureVO);
	}

	// 단건조회 - 멤버아이디 + 강의번호로 조회
	@PostMapping("/getMyLecture")
	public MyLectureVO getMyLecture(@RequestBody MyLectureVO myLectureVO) {
		return service.getMyLecture(myLectureVO);
	}

	// 전체조회 - 멤버아이디로 조회
	@PostMapping("/getMyLectureList")
	public List<MyLectureVO> getMyLectureList(@RequestBody MyLectureVO myLectureVO) {
		return service.getMyLectureListByMemberId(myLectureVO);
	}

	// 가장 최근 강의 조회 - 멤버아이디로 조회
	@PostMapping("/getMyLatestOne")
	public List<MyLectureVO> getMyLatestOne(@RequestBody MyLectureVO myLectureVO) {
		return service.getMyLatestOne(myLectureVO);
	}

	// 등록 - 결제 완료시 발생 (파라미터 : member_id, lecture_no)
	// Vue에서 배열로 전달 -> <string, list> 형태의 map 으로 전달 및 받을 수 있음
	@PostMapping("/insertMyLecture")
	public void insertMyLecture(@RequestBody List<MyLectureVO> list) {
		service.insertMyLecture(list);
	}

	// SEK - 수강한 강의 수 조회 (마이페이지-학습현황)

	@PostMapping("/completeLecture")
	public int completeLecture(@RequestBody MyLectureVO myLectureVO) {
		return service.completeLecture(myLectureVO);
	}

}
