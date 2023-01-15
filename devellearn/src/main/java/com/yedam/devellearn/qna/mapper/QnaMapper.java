package com.yedam.devellearn.qna.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.yedam.devellearn.qna.service.QnaRepVO;
import com.yedam.devellearn.qna.service.QnaVO;



public interface QnaMapper {

	// 전체조회
	public Page<QnaVO> getQnaListAll(Map<String,Object> map);
	// 내글조회
	public Page<QnaVO> getMyQnaListAll(Map<String,Object> map);
	// 내답글조회
	public Page<QnaRepVO> getMyQnaRep(Map<String,Object> map);
	
	// 검색
	public Page<QnaVO> getQnaFilter(Map<String,Object> map);
	// 내글검색
	public Page<QnaVO> getMyQnaFilter(Map<String,Object> map);
	
	//단건
	public QnaVO getQnaDetail(int qnaNo);
	
	//수정
	public int updateQna(QnaVO vo);
	//채택수정
	public int updateChoice(QnaRepVO vo);
	//모집수정
	public int updateOpen(QnaVO vo);
	
	//삭제
	public int deleteQna(int qnaNo);
	
	//등록
	public int insertQna(QnaVO vo);
	
	//댓글조회
	public List<QnaRepVO> getRepListAll(int qnaNo);
	
	//단건
	public QnaRepVO getRepDetail(int repNo);
	
	//댓글등록
	public int insertRep(QnaRepVO vo);
	
	//댓글삭제
	public int deleteRep(int repNo);
	
	//댓글수정
	public int updateRep(QnaRepVO vo);
	
	//조회수
	public int qnaCount(QnaVO vo);
}
