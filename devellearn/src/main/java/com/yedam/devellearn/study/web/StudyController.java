package com.yedam.devellearn.study.web;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.study.service.StudyVO;
import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.study.service.StudyRepVO;
import com.yedam.devellearn.study.service.StudyService;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
public class StudyController {

	@Autowired
	StudyService service;
	
	//등록
	@PostMapping("/studyi")
	public int insertStudy(MultipartFile image, StudyVO vo) throws IllegalStateException, IOException {
		FileHandler filehandler = new FileHandler();
		String fileName = filehandler.uploadFile("img/teamstudy", "study" + vo.getStudyNo(), image);
		vo.setImg(fileName);
		return service.insertStudy(vo);
	}
	
	//수정
	@PutMapping("/study")
	public int updateStudy(MultipartFile image, StudyVO vo) throws IllegalStateException, IOException {
		// 기존파일삭제
		FileHandler filehandler = new FileHandler();
		filehandler.deleteFile("img/teamstudy", vo.getImg());
		// 새로운 파일 업로드
		String fileName = filehandler.uploadFile("img/teamstudy", "study" + vo.getStudyNo(), image);
		vo.setImg(fileName);
		return service.updateStudy(vo);
	}
	
	// 전체조회
	@PostMapping("/study")
	public PageInfo<StudyVO> getStudyListAll(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getStudyListAll(map));
	}
	
	//검색
	@PostMapping("/searchStudy")
	public PageInfo<StudyVO> getStudyFilter(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getStudyFilter(map));
	}
	//내글 전체조회
	@PostMapping("/mystudy")
	public PageInfo<StudyVO> getMyStudyListAll(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMyStudyListAll(map));
	}
	
	//내글검색
	@PostMapping("/searchMyStudy")
	public PageInfo<StudyVO> getMyStudyFilter(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMyStudyFilter(map));
	}
	
	// 단건조회
	@GetMapping("/study/{studyNo}")
	public StudyVO getStudyDetail(@PathVariable int studyNo) {
		return service.getStudyDetail(studyNo);
	}
	
	//모집상태수정
	@PutMapping("/studyOpen")
	public int updateOpen(@RequestBody StudyVO vo) {
		return service.updateOpen(vo);
	}
	
	//삭제
	@DeleteMapping("/study/{studyNo}")
	public int deleteStudy(@PathVariable int studyNo) {
		return service.deleteStudy(studyNo);
	}
	
	
	//댓글조회
	@GetMapping("/studyRep/{studyNo}")
	public List<StudyRepVO> getRepListAll(@PathVariable int studyNo) {
		return service.getRepListAll(studyNo);
	}
	
	//댓글단건
	/*@GetMapping("/studyreps/{studyrepsNo}")
	public StudyRepVO getRepDetail(@PathVariable int studyrepsNo) {
		return service.getRepDetail(studyrepsNo);
	}*/
	
	
	//댓글등록
	@PostMapping("/studyRep")
	public int insertRep(@RequestBody StudyRepVO vo) {
		return service.insertRep(vo);
	}
	
	//댓글삭제
	@DeleteMapping("/studyRep/{studyrepNo}")
	public int deleteRep(@PathVariable int studyrepNo) {
		return service.deleteRep(studyrepNo);
	}
	
	//댓글수정
	@PutMapping("/studyRep")
	public int updateRep(@RequestBody StudyRepVO vo) {
		return service.updateRep(vo);
	}
	
	//조회수
	@PutMapping("/studyc{studyNo}")
	public int studyCount(@RequestBody StudyVO vo) {
		return service.studyCount(vo);
	}
}
