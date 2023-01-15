package com.yedam.devellearn.qna.web;

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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.qna.service.QnaRepVO;
import com.yedam.devellearn.qna.service.QnaService;
import com.yedam.devellearn.qna.service.QnaVO;



@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
public class QnaController {

	@Autowired
	QnaService service;
	
	// 전체조회
	@PostMapping("/sqna")
	public PageInfo<QnaVO> getQnaListAll(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 5);
		return PageInfo.of(service.getQnaListAll(map));
	}
	// 내글조회
	@PostMapping("/myQA")
	public PageInfo<QnaVO> getMyQnaListAll(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 5);
		return PageInfo.of(service.getMyQnaListAll(map));
	}
	// 내답글조회
	@PostMapping("/myQArep")
	public PageInfo<QnaRepVO> getMyQnaRep(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 5);
		return PageInfo.of(service.getMyQnaRep(map));
	}
	//검색
	@PostMapping("/searchQna")
	public PageInfo<QnaVO> getQnaFilter(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"),5);
		return PageInfo.of(service.getQnaFilter(map));
	}
	//내글검색
	@PostMapping("/searchMyQA")
	public PageInfo<QnaVO> getMyQnaFilter(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 5);
		return PageInfo.of(service.getMyQnaFilter(map));
	}
	// 단건조회
	@GetMapping("/sqna/{qnaNo}")
	public QnaVO getQnaDetail(@PathVariable int qnaNo) {
		return service.getQnaDetail(qnaNo);
	}
	
	//수정
	@PutMapping("/sqna")
	public int updateQna(@RequestBody QnaVO vo) {
		return service.updateQna(vo);
	}
	//채택수정
	@PutMapping("/choiceRep")
	public int updateChoice(@RequestBody QnaRepVO vo) {
		return service.updateChoice(vo);
	}
	//모집수정
	@PutMapping("/qnaOpen")
	public int updateOpen(@RequestBody QnaVO vo) {
		return service.updateOpen(vo);
	}
	
	//삭제
	@DeleteMapping("/sqna/{qnaNo}")
	public int deleteQna(@PathVariable int qnaNo) {
		return service.deleteQna(qnaNo);
	}
	
	//등록
	@PostMapping("/sqnai")
	public int insertQna(@RequestBody QnaVO vo) {
		return service.insertQna(vo);
	}
	
	//댓글조회
	@GetMapping("/sqnarep/{qnaNo}")
	public List<QnaRepVO> getRepListAll(@PathVariable int qnaNo) {
		return service.getRepListAll(qnaNo);
	}
	
	//댓글단건
	/*@GetMapping("/rep/{repNo}")
	public QnaRepVO getRepDetail(@PathVariable int repNo) {
		return service.getRepDetail(repNo);
	}*/
	
	
	//댓글등록
	@PostMapping("/sqnarep")
	public int insertRep(@RequestBody QnaRepVO vo) {
		return service.insertRep(vo);
	}
	
	//댓글삭제
	@DeleteMapping("/sqnarep/{repNo}")
	public int deleteRep(@PathVariable int repNo) {
		return service.deleteRep(repNo);
	}
	
	//댓글수정
	@PutMapping("/sqnarep")
	public int updateRep(@RequestBody QnaRepVO vo) {
		return service.updateRep(vo);
	}
	
	//조회수
	@PutMapping("/sqnac{qnaNo}")
	public int qnaCount(@RequestBody QnaVO vo) {
		return service.qnaCount(vo);
	}
}
