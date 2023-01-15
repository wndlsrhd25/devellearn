package com.yedam.devellearn.mentor.web;

import java.io.File;
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
import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.mentor.service.MentorRelationVo;
import com.yedam.devellearn.mentor.service.MentorRepVo;
import com.yedam.devellearn.mentor.service.MentorService;
import com.yedam.devellearn.mentor.service.MentorVO;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
public class MentorController {

	@Autowired
	MentorService service;
	
	//등록
	@PostMapping("/mentori")
	public int insertMentor(MultipartFile image, MentorVO vo) throws IllegalStateException, IOException {
		FileHandler filehandler = new FileHandler();
		String fileName = filehandler.uploadFile("img/teamstudy", "mentor" + vo.getMentorNo(), image);
		vo.setImg(fileName);
		return service.insertMentor(vo);
	}
	//수정
	@PutMapping("/mentor")
	public int updateMentor(MultipartFile image, MentorVO vo) throws IllegalStateException, IOException {
		// 기존파일삭제
		FileHandler filehandler = new FileHandler();
		filehandler.deleteFile("img/teamstudy", vo.getImg());
		// 새로운 파일 업로드
		String fileName = filehandler.uploadFile("img/teamstudy", "mentor" + vo.getMentorNo(), image);
		vo.setImg(fileName);
		return service.updateMentor(vo);
	}
	
	// 전체조회
	@PostMapping("/mentor")
	public PageInfo<MentorVO> getMentorListAll(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMentorListAll(map));
	}
	// 내글조회
	@PostMapping("/mymentor")
	public PageInfo<MentorVO> getMyMentorListAll(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMyMentorListAll(map));
	}
	// 멘티 
	@PostMapping("/mymenti")
	public PageInfo<MentorRelationVo> getMentiList(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMentiList(map));
	}
	
	//검색
	@PostMapping("/searchMentor")
	public PageInfo<MentorVO> getMentorFilter(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMentorFilter(map));
	}
	//멘토검색
	@PostMapping("/searchMyMentor")
	public PageInfo<MentorVO> getMyMentorFilter(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMyMentorFilter(map));
	}
	//멘티검색
	@PostMapping("/searchMyMenti")
	public PageInfo<MentorRelationVo> getMyMentiFilter(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMyMentiFilter(map));
	}
	
	// 단건조회
	@GetMapping("/mentor/{mentorNo}")
	public MentorVO getMentorDetail(@PathVariable int mentorNo) {
		return service.getMentorDetail(mentorNo);
	}
	
	
	//멘티상태수정
	@PutMapping("/mentorStatus")
	public int updateMenti(@RequestBody MentorRelationVo vo) {
		return service.updateMenti(vo);
	}
	//모집상태수정
	@PutMapping("/mentorOpen")
	public int updateOpen(@RequestBody MentorVO vo) {
		return service.updateOpen(vo);
	}
	
	//삭제
	@DeleteMapping("/mentor/{mentorNo}")
	public int deleteMentor(@PathVariable int mentorNo) {
		return service.deleteMentor(mentorNo);
	}
	
	
	//댓글조회
	@GetMapping("/mentorRep/{mentorNo}")
	public List<MentorRepVo> getMentorRepListAll(@PathVariable int mentorNo) {
		return service.getMentorRepListAll(mentorNo);
	}

	//댓글등록
	@PostMapping("/mentorRep")
	public int insertMentorRep(@RequestBody MentorRepVo vo) {
		return service.insertMentorRep(vo);
	}
	
	//댓글삭제
	@DeleteMapping("/mentorRep/{mentorrepNo}")
	public int deleteMentorRep(@PathVariable int mentorrepNo) {
		return service.deleteMentorRep(mentorrepNo);
	}
	
	//댓글수정
	@PutMapping("/mentorRep")
	public int updateMentorRep(@RequestBody MentorRepVo vo) {
		return service.updateMentorRep(vo);
	}
	
	//멘토신청
	@PostMapping("/mentorenter")
	public int enterMentor(@RequestBody MentorRelationVo vo) {
		return service.enterMentor(vo);
	}
	
	//날짜리스트
	@GetMapping("/mentorRelation")
	public List<MentorRelationVo> getMentorRelationList(MentorRelationVo vo) {
		return service.getMentorRelationList(vo);
	}
	
	//날짜계산
	@GetMapping("/mentorcount/{mentorConsNo}")
	public MentorRelationVo getMentorCount(@PathVariable int mentorConsNo) {
		return service.getMentorCount(mentorConsNo);
	}
	

	// SEK - 완료한 멘토링 수 조회 (마이페이지-학습현황)
	
	@PostMapping("/finishMentor")
	public int finishMentor(@RequestBody MentorRelationVo vo) {
		return service.finishMentor(vo);
	}
	
}
