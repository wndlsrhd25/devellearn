package com.yedam.devellearn.lecture.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;

public interface LectureService {
	// 고유번호 조회
	public int getNo();

	// 강의 등록
	public void lectureRegist(MultipartFile image, LectureVO lectureVO) throws IllegalStateException, IOException;

	// 강의 수정
	public void lectureUpdate(MultipartFile image, LectureVO lectureVO) throws IllegalStateException, IOException;

	// 전체강의조회
	public Page<LectureVO> getLectureList();

	// 강사의 강의 전체 조회
	public Page<LectureVO> getLectureListByLecturer(String memberId);

	// 강의단건조회
	public LectureVO getOneLecture(int lecureNo);

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
