package com.yedam.devellearn.lecture.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.lecture.mapper.LectureMapper;
import com.yedam.devellearn.lecture.service.LectureService;
import com.yedam.devellearn.lecture.service.LectureVO;
import com.yedam.devellearn.quill_editor.QuillEditor;

@Service
public class LectureServiceImpl implements LectureService {

	@Autowired
	LectureMapper mapper;
	QuillEditor quillEditor = new QuillEditor();
	FileHandler filehandler = new FileHandler();

	// 고유번호조회
	@Override
	public int getNo() {
		return mapper.getNo();
	}

	// 강의등록
	@Override
	public void lectureRegist(MultipartFile image, LectureVO lectureVO) throws IllegalStateException, IOException {
		
		// 이미지 저장
		String fileName = filehandler.uploadFile("img/lecture", "lecture_" + lectureVO.getLectureNo(), image);
		// 이미지 이름을 DB에 저장
		lectureVO.setImgSrc(fileName);
		
		// 에디터 내용 txt 파일로 변경하여 저장
		String contentFileName = quillEditor.saveQnaContentToTxtFile(lectureVO.getIntroduce(), "lecture");
		// txt 파일이름을 DB에 저장
		lectureVO.setIntroduce(contentFileName);
		mapper.insert(lectureVO);
	}

	// 강의수정
	@Override
	public void lectureUpdate(MultipartFile image, LectureVO lectureVO) throws IllegalStateException, IOException {
		if (lectureVO.getIntroduce() != null) {
			
			// 기존이미지 파일삭제
			filehandler.deleteFile("img/lecture", mapper.selectOne(lectureVO.getLectureNo()).getImgSrc());
			// 새로운 파일 업로드
			String fileName = filehandler.uploadFile("img/lecture", "lecture" + lectureVO.getLectureNo(), image);
			lectureVO.setImgSrc(fileName);
			
			// 기존 에디터파일 삭제
			String deleteFile = mapper.selectOne(lectureVO.getLectureNo()).getIntroduce();
			quillEditor.deleteContentTxtFileToStr(deleteFile ,"lecture");
			// 에디터로 작성한 content txt 파일로 변경후 파일이름 변환
			String contentFileName = quillEditor.saveQnaContentToTxtFile(lectureVO.getIntroduce(), "lecture");
			lectureVO.setIntroduce(contentFileName);
			mapper.update(lectureVO);	
		}
		
	}

	// 전체조회
	@Override
	public Page<LectureVO> getLectureList() {
		return mapper.selectAll();
	}

	// 강사의 강의 전체 조회
	@Override
	public Page<LectureVO> getLectureListByLecturer(String memberId) {
		return mapper.selectAllByLecturer(memberId);

	}

	// 단건조회
	@Override
	public LectureVO getOneLecture(int lecureNo) {
		LectureVO lectureVo = mapper.selectOne(lecureNo);
		// editor content txt 파일 읽기 - 파라미터 : {DB에 저장된 content fileName, vo name}
		String content = quillEditor.readContentTxtFileToStr(lectureVo.getIntroduce(), "lecture");
		lectureVo.setIntroduce(content);
		return lectureVo;
	}

	@Override
	public Page<LectureVO> getUnapproved() {
		return mapper.getUnapproved();
	}

	// 상태변경
	@Override
	public int updateStatus(LectureVO vo) {
		return mapper.updateStatus(vo);
	}

	// 입문강의
	@Override
	public Page<LectureVO> getBeginnerLecture() {
		return mapper.getBeginnerLecture();
	}

	// 인기강의
	@Override
	public Page<LectureVO> getHotLecture() {
		return mapper.getHotLecture();
	}

	//SEK - 개설한 강의 수 조회 (마이페이지-학습현황)
	@Override
	public int openedLecture(LectureVO lectureVO) {
		return mapper.openedLecture(lectureVO);
	}

	// 강의스킬태그 조회
	@Override
	public List<String> getTags() {
		return mapper.getTags();
	}

	// 강의검색
	@Override
	public Page<LectureVO> searchLecture(Map<String, Object> map) {
		return mapper.searchLecture(map);
	}

}
