package com.yedam.devellearn.lecture.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.lecture.mapper.CurriculumMapper;
import com.yedam.devellearn.lecture.service.CurriculumService;
import com.yedam.devellearn.lecture.service.CurriculumVO;

import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.probe.FFmpegFormat;
import net.bramp.ffmpeg.probe.FFmpegProbeResult;

@Service
public class CurriculumServiceImpl implements CurriculumService {

	@Autowired
	CurriculumMapper mapper;
	FileHandler filehandler = new FileHandler();

	// 고유번호 조회
	@Override
	public int getNo() {
		return mapper.getNo();
	}

	// 커넥션 발생
	@Override
	public int insertCurr(MultipartFile video, CurriculumVO curriculumVO) throws IllegalStateException, IOException {
		// PK 세팅
		curriculumVO.setCurrNo(getNo());

		if (video.isEmpty()) {
			return 0;
		}

//		// 개발용
//		String path = "C:/devellearn/lectureVideo/lecture_" + curriculumVO.getLectureNo();
//		// 배포용
////		String path = "/home/ubuntu/devellearn/lectureVideo/lecture_" + curriculumVO.getLectureNo();

		String fileName = filehandler.uploadFile("lectureVideo/lecture_" + curriculumVO.getLectureNo(),
				"curr_" + curriculumVO.getCurrNo(), video);
		curriculumVO.setVideoSrc(fileName);

//		// 동영상 플레이타임
//		int vedioPlayTime = getVedioPlayTime(path, fileName);
//		System.out.println("vedioPlayTime = " + vedioPlayTime);
//		curriculumVO.setPlayTime(vedioPlayTime);

		System.out.println("curriculumVO = " + curriculumVO);
		return mapper.insert(curriculumVO);
	}

	@Override
	public int updateCurr(MultipartFile video, CurriculumVO curriculumVO) throws IllegalStateException, IOException {

		// 비디오가 null이 아닐때만 비디오 경로, 시간 수정 -> sql도 비디오가 null일 경우는 수정 x
		if (video != null) {

//			// 개발용 - 영상길이 설정용 path
//			String path = "C:/devellearn/lectureVideo/lecture_" + curriculumVO.getLectureNo();
//			// 배포용 - 영상길이 설정용 path
//			// String path = "/home/ubuntu/devellearn/lectureVideo/lecture_" + curriculumVO.getLectureNo(); 

			// 기존 파일 삭제
			filehandler.deleteFile("lectureVideo/lecture_"  + curriculumVO.getLectureNo(), mapper.selectOneCurr(curriculumVO.getCurrNo()).getVideoSrc());
			
			// 새로운 파일 업로드
			String fileName = filehandler.uploadFile("lectureVideo/lecture_" + curriculumVO.getLectureNo(),
					"curr_" + curriculumVO.getCurrNo(), video);
			curriculumVO.setVideoSrc(fileName);

//			// 동영상 플레이타임
//			int vedioPlayTime = getVedioPlayTime(path, fileName);
//			System.out.println("vedioPlayTime = " + vedioPlayTime);
//			curriculumVO.setPlayTime(vedioPlayTime);

			System.out.println("curriculumVO = " + curriculumVO);
		}

		return mapper.update(curriculumVO);
	}

	@Override
	public void deleteCurr() {
		mapper.delete();
	}

	@Override
	public List<CurriculumVO> getCurrListByLectureNo(int lectureNo) {
		return mapper.selectAllByLectureNo(lectureNo);
	}

	@Override
	public CurriculumVO getCurr(int currNo) {
		return mapper.selectOneCurr(currNo);
	}

//	// 동영상 플레이타임
//	public int getVedioPlayTime(String path, String fileName) {
//		double timeSecond = 0;
//
//		try {
//			// 개발용
//			FFprobe ffprobe = new FFprobe("C:/ffmpeg/bin/ffprobe"); // ffprobe.exe 파일 경로
//			// 서버용
////			FFprobe ffprobe = new FFprobe("/usr/bin/ffprobe"); // ffprobe.exe 파일 경로
//			FFmpegProbeResult probeResult = ffprobe.probe(path + "/" + fileName);
//			FFmpegFormat format = probeResult.getFormat();
//			timeSecond = format.duration; // 초단위
//
//		} catch (IOException e) {
//			System.out.println("@@ media_player_time error @@");
//			e.printStackTrace();
//		}
//
//		return (int) timeSecond;
//	}

}
