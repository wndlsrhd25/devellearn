package com.yedam.devellearn.lecture.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yedam.devellearn.lecture.mapper.LectureAnswerMapper;
import com.yedam.devellearn.lecture.service.LectureAnswerService;
import com.yedam.devellearn.lecture.service.LectureAnswerVO;
import com.yedam.devellearn.quill_editor.QuillEditor;

@Service
public class LectureAnswerServiceImpl implements LectureAnswerService {

	@Autowired
	LectureAnswerMapper mapper;
	QuillEditor quillEditor = new QuillEditor();

	// 답변 등록
	@Override
	public void insertAnswer(LectureAnswerVO lectureAnswerVO) {
		// quill editor로 작성한 내용 txt 파일로 저장 - 파라미터 : {editor content, vo name}
		String contentFileName = quillEditor.saveQnaContentToTxtFile(lectureAnswerVO.getContent(), "lectureAnswer");
		lectureAnswerVO.setContent(contentFileName);
		mapper.insert(lectureAnswerVO);
	}

	// 답변 수정
	@Override
	public int modifyAnswer(LectureAnswerVO lectureAnswerVO) {
		System.out.println("lectureAnswerVO.getAnswerNo() = " + lectureAnswerVO.getAnswerNo());

		if (lectureAnswerVO.getContent() != null) {
			// 기존 파일 삭제
			String deleteFile = mapper.selectOne(lectureAnswerVO.getAnswerNo()).getContent();
			quillEditor.deleteContentTxtFileToStr(deleteFile, "lectureAnswer");

			// quill editor로 작성한 내용 txt 파일로 저장 - 파라미터 : {editor content, vo name}
			String contentFileName = quillEditor.saveQnaContentToTxtFile(lectureAnswerVO.getContent(), "lectureAnswer");
			lectureAnswerVO.setContent(contentFileName);
		}

		return mapper.update(lectureAnswerVO);
	}

	// 답변 삭제
	@Override
	public int deleteAnswer(int answerNo) {
		// 기존 파일 삭제
		String deleteFile = mapper.selectOne(answerNo).getContent();
		quillEditor.deleteContentTxtFileToStr(deleteFile, "lectureQna");

		return mapper.delete(answerNo);
	}

	// 고유번호 조회
	@Override
	public int getNo() {
		return mapper.getNo();
	}

	// 전체조회 - 질문번호로 조회
	@Override
	public List<LectureAnswerVO> getAnswerList(int qnaNo) {
		List<LectureAnswerVO> list = mapper.selectAllByQnaNo(qnaNo);
		for (LectureAnswerVO lectureAnswerVO : list) {
			// editor content txt 파일 읽기 - 파라미터 : {DB에 저장된 content fileName, vo name}
			String content = quillEditor.readContentTxtFileToStr(lectureAnswerVO.getContent(), "lectureAnswer");
			lectureAnswerVO.setContent(content);
		}
		return list;
	}

	// 단건조회
	@Override
	public LectureAnswerVO getAnswer(int answerNo) {
		LectureAnswerVO lectureAnswerVO = mapper.selectOne(answerNo);
		String content = quillEditor.readContentTxtFileToStr(lectureAnswerVO.getContent(), "lectureAnswer");
		lectureAnswerVO.setContent(content);
		return lectureAnswerVO;
	}

}
