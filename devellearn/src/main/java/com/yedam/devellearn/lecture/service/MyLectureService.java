package com.yedam.devellearn.lecture.service;

import java.util.List;

public interface MyLectureService {
	// 고유번호조회
	public int getNo();

	// 등록
	public void insertMyLecture(List<MyLectureVO> list);

	// 수정 - latestWatchedCurr / latestWatctedTime / totalWatchedTime
	public int updateMyLecture(MyLectureVO myLectureVO);
	
	// 수강완료시 완료로 변경
	public int updateToComplete(MyLectureVO myLectureVO);

	// 삭제?
	public int deleteMyLecture(int myLectureNo);

	// 전체조회 - 멤버아이디로 조회
	public List<MyLectureVO> getMyLectureListByMemberId(MyLectureVO myLectureVO);

	// 가장 최근 강의 조회 - 멤버아이디로 조회
	public List<MyLectureVO> getMyLatestOne(MyLectureVO myLectureVO);

	// 단건조회 - 멤버아이디 + 강의번호로 조회
	public MyLectureVO getMyLecture(MyLectureVO myLectureVO);

	// SEK - 수강한 강의 수 조회 (마이페이지-학습현황)
	public int completeLecture(MyLectureVO myLectureVO);

}
