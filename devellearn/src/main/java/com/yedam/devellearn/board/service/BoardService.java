package com.yedam.devellearn.board.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;



public interface BoardService {
	
	//단건
	public BoardVO getBoardDetail(int boardNo);
	
	//수정
	public int updateBoard(BoardVO vo);
	
	//삭제
	public int deleteBoard(int boardNo);
	
	
	//댓글조회
	public List<BoardRepVO> getRepListAll(int boardNo);
	
	//댓글단건
	public BoardRepVO getRepDetail(int repNo);
	
	//댓글등록
	public int insertRep(BoardRepVO vo);
	
	//댓글삭제
	public int deleteRep(int repNo);
	
	//댓글수정
	public int updateRep(BoardRepVO vo);
	
	//조회수
	public int boardCount(BoardVO vo);
	
	// 전체조회
	public Page<BoardVO> getBoardListAll(Map<String, Object> map);

	// 내글 전체조회
	public Page<BoardVO> getMyBoardListAll(Map<String, Object> map);
	// 내글 전체조회
	public Page<BoardVO> getMyRepListAll(Map<String, Object> map);
	
	// 내글 검색
	public Page<BoardVO> getMyBoardFilter(Map<String, Object> map);
	// 내댓글 검색
	public Page<BoardVO> getMyRepFilter(Map<String, Object> map);
	
	//등록
	public int insertBoard(BoardVO vo);

}
