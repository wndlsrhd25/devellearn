package com.yedam.devellearn.lecture.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.yedam.devellearn.lecture.service.LectureVO;

public interface LectureMapper {
	// 고유번호조회
	public int getNo();

	// 삽입
	public void insert(LectureVO lectureVO);
	
	// 수정
	public void update(LectureVO lectureVO);

	// 전체조회
	public Page<LectureVO> selectAll();

	// 강사의 강의 전체 조회
	public Page<LectureVO> selectAllByLecturer(String memberId);

	// 단건조회
	public LectureVO selectOne(int lectureNo);

	// 미승인 조회
	public Page<LectureVO> getUnapproved();

	// 상태변경
	public int updateStatus(LectureVO vo);

	// 입문강의
	public Page<LectureVO> getBeginnerLecture();

	// 인기강의
	public Page<LectureVO> getHotLecture();

	// SEK - 개설한 강의 수 조회 (마이페이지-학습현황)
	public int openedLecture(LectureVO lectureVO);

	// 강의 스킬태그 조회
	public List<String> getTags();

	// 강의검색
	public Page<LectureVO> searchLecture(Map<String, Object> map);

}
