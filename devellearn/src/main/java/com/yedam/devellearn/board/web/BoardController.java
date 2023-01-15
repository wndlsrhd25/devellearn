package com.yedam.devellearn.board.web;

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
import com.yedam.devellearn.board.service.BoardRepVO;
import com.yedam.devellearn.board.service.BoardService;
import com.yedam.devellearn.board.service.BoardVO;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY) 
public class BoardController {

   @Autowired
   BoardService service;
   
   
   // 단건조회
   @GetMapping("/board/{boardNo}")
   public BoardVO getBoardDetail(@PathVariable int boardNo) {
      return service.getBoardDetail(boardNo);
   }
   
   //수정
   @PutMapping("/board")
   public int updateBoard(@RequestBody BoardVO vo) {
      return service.updateBoard(vo);
   }
   
   //삭제
   @DeleteMapping("/board/{boardNo}")
   public int deleteBoard(@PathVariable int boardNo) {
      return service.deleteBoard(boardNo);
   }
   
   
   //댓글조회
   @GetMapping("/rep/{boardNo}")
   public List<BoardRepVO> getRepListAll(@PathVariable int boardNo) {
      return service.getRepListAll(boardNo);
   }
   
   //댓글단건
   /*@GetMapping("/rep/{repNo}")
   public BoardRepVO getRepDetail(@PathVariable int repNo) {
      return service.getRepDetail(repNo);
   }*/
   
   
   //댓글등록
   @PostMapping("/rep")
   public int insertRep(@RequestBody BoardRepVO vo) {
      return service.insertRep(vo);
   }
   
   //댓글삭제
   @DeleteMapping("/rep/{repNo}")
   public int deleteRep(@PathVariable int repNo) {
      return service.deleteRep(repNo);
   }
   
   //댓글수정
   @PutMapping("/rep")
   public int updateRep(@RequestBody BoardRepVO vo) {
      return service.updateRep(vo);
   }
   
   //조회수
   @PutMapping("/boardc{boardNo}")
   public int boardCount(@RequestBody BoardVO vo) {
      return service.boardCount(vo);
   }
   
   // 전체조회 자유게시판
   @PostMapping("/board")
   public PageInfo<BoardVO> getBoardListAll(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      map.put("boardType", "자유게시판");
      return PageInfo.of(service.getBoardListAll(map));
   }
   //뉴스
   @PostMapping("/board2")
   public PageInfo<BoardVO> getBoardListAll2(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      map.put("boardType", "테크뉴스");
      return PageInfo.of(service.getBoardListAll(map));
   }
   //기술리뷰
   @PostMapping("/board3")
   public PageInfo<BoardVO> getBoardListAll3(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      map.put("boardType", "기술리뷰");
      return PageInfo.of(service.getBoardListAll(map));
   }
   //공지사항
   @PostMapping("/board4")
   public PageInfo<BoardVO> getBoardListAll4(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      map.put("boardType", "공지사항");
      return PageInfo.of(service.getBoardListAll(map));
   }
   //이벤트
   @PostMapping("/board5")
   public PageInfo<BoardVO> getBoardListAll5(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      map.put("boardType", "이벤트");
      return PageInfo.of(service.getBoardListAll(map));
   }
   //내글 전체조회
   @PostMapping("/myboard")
   public PageInfo<BoardVO> getMyBoardListAll(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      return PageInfo.of(service.getMyBoardListAll(map));
   }
   //내댓글 전체조회
   @PostMapping("/myrep")
   public PageInfo<BoardVO> getMyRepListAll(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      return PageInfo.of(service.getMyRepListAll(map));
   }
   
   //검색
   @PostMapping("/searchBoard")
   public PageInfo<BoardVO> getBoardFilter(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      map.put("boardType", "자유게시판");
      return PageInfo.of(service.getBoardListAll(map));
   }
   @PostMapping("/searchBoard2")
   public PageInfo<BoardVO> getBoardFilter2(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      map.put("boardType", "테크뉴스");
      return PageInfo.of(service.getBoardListAll(map));
   }
   @PostMapping("/searchBoard3")
   public PageInfo<BoardVO> getBoardFilter3(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      map.put("boardType", "기술리뷰");
      return PageInfo.of(service.getBoardListAll(map));
   }
   @PostMapping("/searchBoard4")
   public PageInfo<BoardVO> getBoardFilter4(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      map.put("boardType", "공지사항");
      return PageInfo.of(service.getBoardListAll(map));
   }
   @PostMapping("/searchBoard5")
   public PageInfo<BoardVO> getBoardFilter5(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      map.put("boardType", "이벤");
      return PageInfo.of(service.getBoardListAll(map));
   }
   //내글 검색
   @PostMapping("/searchMyBoard")
   public PageInfo<BoardVO> getMyBoardFilter(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      return PageInfo.of(service.getMyBoardFilter(map));
   }
   //내댓글 검색
   @PostMapping("/searchMyRep")
   public PageInfo<BoardVO> getMyRepFilter(@RequestBody Map<String,Object> map) {
      PageHelper.startPage((int) map.get("pageNum"), 5);
      return PageInfo.of(service.getBoardListAll(map));
   }
   
   //등록
   @PostMapping("/boardi")
   public int insertBoard(@RequestBody BoardVO vo) {
      vo.setBoardType("자유게시판");
      return service.insertBoard(vo);
   }
   @PostMapping("/boardi2")
   public int insertBoard2(@RequestBody BoardVO vo) {
      vo.setBoardType("테크뉴스");
      return service.insertBoard(vo);
   }
   @PostMapping("/boardi3")
   public int insertBoard3(@RequestBody BoardVO vo) {      
      vo.setBoardType("기술리뷰");
      return service.insertBoard(vo);
   }
   @PostMapping("/boardi4")
   public int insertBoard4(@RequestBody BoardVO vo) {
      vo.setBoardType("공지사항");
      return service.insertBoard(vo);
   }
   @PostMapping("/boardi5")
   public int insertBoard5(@RequestBody BoardVO vo) {
      vo.setBoardType("이벤트");
      return service.insertBoard(vo);
   }
}