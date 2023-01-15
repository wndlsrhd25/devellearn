package com.yedam.devellearn.lecture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.devellearn.lecture.mapper.LectureLikeMapper;
import com.yedam.devellearn.lecture.service.LectureLikeService;
import com.yedam.devellearn.lecture.service.LectureLikeVO;

@Service
public class LectureLikeServiceImpl implements LectureLikeService{

	@Autowired
	LectureLikeMapper mapper;
	
	@Override
	public int getNo() {
		return mapper.getNo();
	}

	@Override
	public int insertLikedLecture(LectureLikeVO lectureLikeVO) {
		return mapper.insert(lectureLikeVO);
	}

	@Override
	public int deleteLikedLecture(int likeNo) {
		return mapper.delete(likeNo);
	}

	@Override
	public LectureLikeVO getLikedLecture(LectureLikeVO lectureLikeVO) {
		return mapper.selectOne(lectureLikeVO);
	}

	@Override
	public List<LectureLikeVO> getLikedLectureListByMemberId(String memberId) {
		return mapper.selectAllByMemberId(memberId);
	}

}
