package com.yedam.devellearn.lecture.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.lecture.service.CurriculumService;
import com.yedam.devellearn.lecture.service.CurriculumVO;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class CurriculumController {

	@Autowired
	CurriculumService service;

	// 커리큘럼 전체 조회 - 강의번호로 조회
	@GetMapping("/currList/{lectureNo}")
	public List<CurriculumVO> currList(@PathVariable int lectureNo) {
		List<CurriculumVO> list = service.getCurrListByLectureNo(lectureNo);
		return list;
	}

	// 커리큘럼 단건 조회 - 커리큘럼 번호로 조회
	@GetMapping("/curr/{currNo}")
	public CurriculumVO getCurr(@PathVariable int currNo) {
		CurriculumVO curr = service.getCurr(currNo);
		return curr;
	}

	@PostMapping("/insertCurr")
	// MultipartFile : 뷰에서 v-file-input 의 name 속성과 일치
	public int insertCurr(MultipartFile video, CurriculumVO curriculumVO) throws IllegalStateException, IOException, UnsupportedAudioFileException {
		
		System.out.println("@@@@@@@@@@영상길이 가져오는 로직 수정@@@@@@@@@@@curriculumVO = " + curriculumVO);
		
		return service.insertCurr(video, curriculumVO);
	}

	@PutMapping("/updateCurr")
	// MultipartFile : 뷰에서 v-file-input 의 name 속성과 일치
	public int updateCurr(MultipartFile video, CurriculumVO curriculumVO) throws IllegalStateException, IOException, UnsupportedAudioFileException {
		return service.updateCurr(video, curriculumVO);
	}
}
