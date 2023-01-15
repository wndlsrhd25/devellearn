package com.yedam.devellearn.qna.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.qna.mapper.QnaMapper;
import com.yedam.devellearn.qna.service.QnaRepVO;
import com.yedam.devellearn.qna.service.QnaService;
import com.yedam.devellearn.qna.service.QnaVO;


@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	QnaMapper mapper;
	
	//전체
	@Override
	public Page<QnaVO> getQnaListAll(Map<String,Object> map){
		return mapper.getQnaListAll(map);
	}
	//내글조회
	@Override
	public Page<QnaVO> getMyQnaListAll(Map<String,Object> map){
		return mapper.getMyQnaListAll(map);
	}
	//내답글조회
	@Override
	public Page<QnaRepVO> getMyQnaRep(Map<String,Object> map){
		return mapper.getMyQnaRep(map);
	}
	// 검색
	@Override
	public Page<QnaVO> getQnaFilter(Map<String,Object> map){
		return mapper.getQnaFilter(map);
	}
	// 내글 검색
	@Override
	public Page<QnaVO> getMyQnaFilter(Map<String,Object> map){
		return mapper.getMyQnaFilter(map);
	}
	
	//단건
	@Override
	public QnaVO getQnaDetail(int qnaNo) {
		return mapper.getQnaDetail(qnaNo);
	}
	
	//수정
	@Override
	public int updateQna(QnaVO vo) {
		return mapper.updateQna(vo);
	}
	//채택수정
	@Override
	public int updateChoice(QnaRepVO vo) {
		return mapper.updateChoice(vo);
	}
	//모집수정
	@Override
	public int updateOpen(QnaVO vo) {
		return mapper.updateOpen(vo);
	}
	
	//삭제
	@Override
	public int deleteQna(int qnaNo) {
		return mapper.deleteQna(qnaNo);
	}
	
	//등록
	@Override
	public int insertQna(QnaVO vo) {
		return mapper.insertQna(vo);
	}
	
	//댓글조회
	@Override
	public List<QnaRepVO> getRepListAll(int qnaNo){
		return mapper.getRepListAll(qnaNo);
	}
	
	//댓글단건
	@Override
	public QnaRepVO getRepDetail(int repNo) {
		return mapper.getRepDetail(repNo);
	}
	
	//댓글등록
	@Override
	public int insertRep(QnaRepVO vo) {
		return mapper.insertRep(vo);
	}
	
	//댓글삭제
	@Override
	public int deleteRep(int repNo) {
		return mapper.deleteRep(repNo);
	}
	
	//댓글수정
	@Override
	public int updateRep(QnaRepVO vo) {
		return mapper.updateRep(vo);
	}
	
	//조회수
	@Override
	public int qnaCount(QnaVO vo) {
		return mapper.qnaCount(vo);
	}
	
}
