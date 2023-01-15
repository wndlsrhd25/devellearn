package com.yedam.devellearn.board.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.board.mapper.BoardMapper;
import com.yedam.devellearn.board.service.BoardRepVO;
import com.yedam.devellearn.board.service.BoardService;
import com.yedam.devellearn.board.service.BoardVO;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardMapper mapper;
	
	
	//단건
	@Override
	public BoardVO getBoardDetail(int boardNo) {
		return mapper.getBoardDetail(boardNo);
	}
	
	//수정
	@Override
	public int updateBoard(BoardVO vo) {
		return mapper.updateBoard(vo);
	}
	
	//삭제
	@Override
	public int deleteBoard(int boardNo) {
		return mapper.deleteBoard(boardNo);
	}
	
	
	//댓글조회
	@Override
	public List<BoardRepVO> getRepListAll(int boardNo){
		return mapper.getRepListAll(boardNo);
	}
	
	//댓글단건
	@Override
	public BoardRepVO getRepDetail(int repNo) {
		return mapper.getRepDetail(repNo);
	}
	
	//댓글등록
	@Override
	public int insertRep(BoardRepVO vo) {
		return mapper.insertRep(vo);
	}
	
	//댓글삭제
	@Override
	public int deleteRep(int repNo) {
		return mapper.deleteRep(repNo);
	}
	
	//댓글수정
	@Override
	public int updateRep(BoardRepVO vo) {
		return mapper.updateRep(vo);
	}
	
	//조회수
	@Override
	public int boardCount(BoardVO vo) {
		return mapper.boardCount(vo);
	}
	
	//전체
	@Override
	public Page<BoardVO> getBoardListAll(Map<String,Object> map){
		return mapper.getBoardListAll(map);
	}
	
	//내글 전체조회
	@Override
	public Page<BoardVO> getMyBoardListAll(Map<String,Object> map){
		return mapper.getMyBoardListAll(map);
	}
	//내댓글 전체조회
	@Override
	public Page<BoardVO> getMyRepListAll(Map<String,Object> map){
		return mapper.getMyRepListAll(map);
	}
	
	
	//내글 검색
	@Override
	public Page<BoardVO> getMyBoardFilter(Map<String,Object> map){
		return mapper.getMyBoardFilter(map);
	}
	//내댓글 검색
	@Override
	public Page<BoardVO> getMyRepFilter(Map<String,Object> map){
		return mapper.getMyRepFilter(map);
	}
	
	//등록
	@Override
	public int insertBoard(BoardVO vo) {
		return mapper.insertBoard(vo);
	}	
	
	
}
