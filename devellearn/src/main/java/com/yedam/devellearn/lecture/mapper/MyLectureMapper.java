package com.yedam.devellearn.lecture.mapper;

import java.util.List;

import com.yedam.devellearn.lecture.service.MyLectureVO;

public interface MyLectureMapper {
	// 고유번호조회
	public int getNo();

	// 등록
	public void insert(MyLectureVO myLectureVO);

	// 수정 - latestWatchedCurr / latestWatctedTime / totalWatchedTime
	public int update(MyLectureVO myLectureVO);
	
	// 수강 완료시 완료로 변경
	public int updateToComplete(MyLectureVO myLectureVO);

	// 삭제?
	public int delete(int myLectureNo);

	// 전체조회 - 멤버아이디로 조회
	public List<MyLectureVO> selectAllByMemberId(MyLectureVO myLectureVO);

	// 가장 최근 강의 조회 - 멤버아이디로 조회
		public List<MyLectureVO> getMyLatestOne(MyLectureVO myLectureVO);
	// 단건조회 - 멤버아이디 + 강의번호로 조회
	public MyLectureVO selectOne(MyLectureVO myLectureVO);

	
	// SEK - 수강한 강의 수 조회 (마이페이지-학습현황)
	public int completeLecture (MyLectureVO myLectureVO);
	

}
