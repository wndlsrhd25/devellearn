package com.yedam.devellearn.lecture.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.devellearn.lecture.mapper.LectureNoteMapper;
import com.yedam.devellearn.lecture.service.LectureNoteService;
import com.yedam.devellearn.lecture.service.LectureNoteVO;
import com.yedam.devellearn.quill_editor.QuillEditor;

@Service
public class LectureNoteServiceImpl implements LectureNoteService {

	@Autowired
	LectureNoteMapper mapper;
	QuillEditor quillEditor = new QuillEditor();

	// 고유번호 조회
	@Override
	public int getNo() {
		return mapper.getNo();
	}

	// 등록
	@Override
	public void insertNote(LectureNoteVO lectureNoteVO) {
		// quill editor로 작성한 내용 txt 파일로 저장 - 파라미터 : {editor content, vo name}
		String contentFileName = quillEditor.saveQnaContentToTxtFile(lectureNoteVO.getContent(), "lectureNote");
		lectureNoteVO.setContent(contentFileName);
		mapper.insert(lectureNoteVO);
	}

	@Override
	public LectureNoteVO updateNote(LectureNoteVO lectureNoteVO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteNote(int lectureNoteNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	// 전체조회 - 회원아이디 + 커리큘럼번호로 조회
	@Override
	public List<LectureNoteVO> getNotesByMemberIdAndCurrNo(LectureNoteVO lectureNoteVO) {
		List<LectureNoteVO> list = mapper.selectAllByMemberIdAndCurrNo(lectureNoteVO);
		
		for (LectureNoteVO lectureNoteVO2 : list) {
			// editor content txt 파일 읽기 - 파라미터 : {DB에 저장된 content fileName, vo name}
			String content = quillEditor.readContentTxtFileToStr(lectureNoteVO2.getContent(), "lectureNote");
			lectureNoteVO2.setContent(content);
		}
		return list;
	}

	@Override
	public List<LectureNoteVO> getNotesByMemberId(String memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LectureNoteVO getNote(int lectureNoteNo) {
		LectureNoteVO lectureNoteVO = mapper.selectOne(lectureNoteNo);
		
		// editor content txt 파일 읽기 - 파라미터 : {DB에 저장된 content fileName, vo name}
		String content = quillEditor.readContentTxtFileToStr(lectureNoteVO.getContent(), "lectureNote");
		lectureNoteVO.setContent(content);
		return lectureNoteVO;
	}

}
