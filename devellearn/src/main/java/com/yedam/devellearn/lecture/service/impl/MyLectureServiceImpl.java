package com.yedam.devellearn.lecture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.devellearn.lecture.mapper.MyLectureMapper;
import com.yedam.devellearn.lecture.service.MyLectureService;
import com.yedam.devellearn.lecture.service.MyLectureVO;

@Service
public class MyLectureServiceImpl implements MyLectureService {

	@Autowired
	MyLectureMapper mapper;

	@Override
	public int getNo() {
		return mapper.getNo();
	}

	@Override
	public void insertMyLecture(List<MyLectureVO> list) {
		for (MyLectureVO myLectureVO : list ) {
			mapper.insert(myLectureVO);
		}
	}

	// 수정 - latestWatchedCurr / latestWatctedTime / totalWatchedTime
	@Override
	public int updateMyLecture(MyLectureVO myLectureVO) {
		return mapper.update(myLectureVO);
	}

	// 수강 완료시 완료로 변경
	@Override
	public int updateToComplete(MyLectureVO myLectureVO) {
		return mapper.updateToComplete(myLectureVO);
	}

	@Override
	public int deleteMyLecture(int myLectureNo) {
		return mapper.delete(myLectureNo);
	}

	@Override
	public List<MyLectureVO> getMyLectureListByMemberId(MyLectureVO myLectureVO) {
		return mapper.selectAllByMemberId(myLectureVO);
	}

	// 단건조회 - 멤버아이디 + 강의번호로 조회
	@Override
	public MyLectureVO getMyLecture(MyLectureVO myLectureVO) {
		return mapper.selectOne(myLectureVO);
	}

	// SEK - 수강한 강의 수 조회 (마이페이지-학습현황)
	@Override
	public int completeLecture(MyLectureVO myLectureVO) {
		return mapper.completeLecture(myLectureVO);
	}

	// 가장 최근 강의 조회 - 멤버아이디로 조회
	@Override
	public List<MyLectureVO> getMyLatestOne(MyLectureVO myLectureVO) {
		return mapper.getMyLatestOne(myLectureVO);
	}
	

}
