package com.yedam.devellearn.lecture.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.UnsupportedAudioFileException;

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
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.lecture.service.LectureService;
import com.yedam.devellearn.lecture.service.LectureVO;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class LectureController {

	@Autowired
	LectureService service;

	// 등록
	@PostMapping("/insertLetcure")
	public void insertItem(MultipartFile image, LectureVO lectureVO)
			throws IllegalStateException, IOException, UnsupportedAudioFileException {
		service.lectureRegist(image, lectureVO);
	}

	// 강의 수정
	@PutMapping("/updateLecture")
	public void updateItem(MultipartFile image, LectureVO lectureVO)
			throws IllegalStateException, IOException, UnsupportedAudioFileException {

		service.lectureUpdate(image, lectureVO);
	}

	// 전체조회
	@GetMapping("/lecture")
	public PageInfo<LectureVO> lectureList(@RequestParam int pageNum) {
		PageHelper.startPage(pageNum, 12);
		return PageInfo.of(service.getLectureList());
	}

	// 전체조회
	@GetMapping("/lectureListByLecturer")
	public PageInfo<LectureVO> lectureList(@RequestParam int pageNum, String memberId) {
		PageHelper.startPage(pageNum, 10);
		return PageInfo.of(service.getLectureListByLecturer(memberId));
	}

	// 단건조회
	@GetMapping("/lecture/{lectureNo}")
	public LectureVO getLecture(@PathVariable int lectureNo) {
		LectureVO lecture = service.getOneLecture(lectureNo);
		return lecture;
	}

	// 검색
	@PostMapping("/lecture/search")
	public PageInfo<LectureVO> searchLecture(@RequestBody Map<String, Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 12, (String) map.get("order"));
		return PageInfo.of(service.searchLecture(map));
	}

	// 고유번호 조회
	@GetMapping("/lectureNo")
	public int getLectureNo() {
		return service.getNo();
	}

	// 미승인 목록조회
	@GetMapping("/lecture/unapproved/{pageNum}")
	public PageInfo<LectureVO> getUnapproved(@PathVariable int pageNum) {
		PageHelper.startPage(pageNum, 10);
		return PageInfo.of(service.getUnapproved());
	}

	// 상태변경
	@PutMapping("/lecture")
	public int updateStatus(@RequestBody LectureVO vo) {
		return service.updateStatus(vo);
	}

	// 메인에 보여질 강의리스트 조회
	@GetMapping("/lectureMain")
	public Map<String, PageInfo<LectureVO>> getMainLecture() {
		PageHelper.startPage(1, 4, "lecture_no desc");
		Map<String, PageInfo<LectureVO>> map = new HashMap<>();
		map.put("beginner", PageInfo.of(service.getBeginnerLecture()));
		PageHelper.startPage(1, 4, "lecture_no desc");
		map.put("hotLecture", PageInfo.of(service.getHotLecture()));
		return map;
	}

	// SEK - 개설한 강의 수 조회 (마이페이지-학습현황)

	@PostMapping("/openedLecture")
	public int openedLecture(@RequestBody LectureVO lectureVO) {
		return service.openedLecture(lectureVO);
	}

	// 기술태그 목록 조회
	@GetMapping("/lecture/tags")
	public Map<String, List<String>> getTags() {
		Map<String, List<String>> map = new HashMap<>();
		map.put("tags", service.getTags());
		return map;
	}
}
