package com.yedam.devellearn.lecture.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.lecture.mapper.LectureQnaMapper;
import com.yedam.devellearn.lecture.service.LectureQnaService;
import com.yedam.devellearn.lecture.service.LectureQnaVO;
import com.yedam.devellearn.quill_editor.QuillEditor;

@Service
public class LectureQnaServiceImpl implements LectureQnaService {

	@Autowired
	LectureQnaMapper mapper;
	QuillEditor quillEditor = new QuillEditor();

	// 고유번호 조회
	@Override
	public int getNo() {
		return mapper.getNo();
	}

	// 질문 작성
	@Override
	public void writeQna(LectureQnaVO lectureQnaVO) {
		
		// quill editor로 작성한 내용 txt 파일로 저장 - 파라미터 : {editor content, vo name}
		String contentFileName = quillEditor.saveQnaContentToTxtFile(lectureQnaVO.getContent(), "lectureQna");
		lectureQnaVO.setContent(contentFileName);
		mapper.insert(lectureQnaVO);
	}

	// 질문 수정
	@Override
	public void modifyQna(LectureQnaVO lectureQnaVO) {
		
		if (lectureQnaVO.getContent() != null) {
			// 기존 파일 삭제
			String deleteFile = mapper.selectOne(lectureQnaVO.getQnaNo()).getContent();
			quillEditor.deleteContentTxtFileToStr(deleteFile ,"lectureQna");
			// 에디터로 작성한 content txt 파일로 변경후 파일이름 변환
			String contentFileName = quillEditor.saveQnaContentToTxtFile(lectureQnaVO.getContent(), "lectureQna");
			lectureQnaVO.setContent(contentFileName);
		}
		
		
		System.out.println("lectureQnaVO = " + lectureQnaVO);
		mapper.update(lectureQnaVO);
	}

	// 질문 삭제
	@Override
	public int deleteQna(int qnaNo) {
		
		// 기존 파일 삭제
		String deleteFile = mapper.selectOne(qnaNo).getContent();
		quillEditor.deleteContentTxtFileToStr(deleteFile ,"lectureQna");
		// 질문 content txt 파일 삭제 필요
		return mapper.delete(qnaNo);
	}

	// 전체 질문 조회 - 강의번호로 조회(객체 파라미터 전달)
	@Override
	public Page<LectureQnaVO> getQnaListByLectureNo(LectureQnaVO lectureQnaVO) {
		Page<LectureQnaVO> list = mapper.selectAllByLectureNo(lectureQnaVO);
		changeNoneHtml(list);
		return list;
	}
	
	// 전체 질문 조회 - 커리큘럼번호로 조회(객체 파라미터 전달)
	@Override
	public List<LectureQnaVO> getQnaListByCurrNo(LectureQnaVO lectureQnaVO) {
		List<LectureQnaVO> list = mapper.selectAllByCurrNo(lectureQnaVO);
		changeNoneHtml(list);
		return list;
	}

	// 전체조회 - 회원아이디로 조회(수강자)
	@Override
	public List<LectureQnaVO> getQnaListByLearner(Map<String, String> map) {
		List<LectureQnaVO> list = mapper.selectAllByLearner(map);

		changeNoneHtml(list);
		return list;
	}

	// 전체조회 - 회원아이디로 조회(강사)
	@Override
	public List<LectureQnaVO> getQnaListByLecturer(LectureQnaVO lectureQnaVO) {
		List<LectureQnaVO> list = mapper.selectAllByLecturer(lectureQnaVO);

		changeNoneHtml(list);
		return list;
	}

	// content HTML 태그 제거 - 리스트에 말줄임으로 들어갈 content
	// 리스트형식으로 출력시 내용 미리보기에도 HTML코드가 반영됨 -> 미리보기시에는 HTML코드 제거가 필요
	private void changeNoneHtml(List<LectureQnaVO> list) {
		
		for (int i = 0; i < list.size(); i++) {
			String content = quillEditor.readContentTxtFileToStr(list.get(i).getContent(), "lectureQna");
			// IMG 태그는 "사진"이라는 문구로 대체
			String textWithoutTag = content.replaceAll("<p><img[^>]*></p>", " 사진 ");
			// <> 형식인 HTML 태그 제거
			textWithoutTag = textWithoutTag.replaceAll("<[^>]*>", "");
			list.get(i).setNoneHtmlContent(textWithoutTag);
		}
	}

	// 질문 단건 조회 - 고유번호로 조회
	@Override
	public LectureQnaVO getQna(int qnaNo) {
		
		LectureQnaVO lectureQnaVO = mapper.selectOne(qnaNo);
		// editor content txt 파일 읽기 - 파라미터 : {DB에 저장된 content fileName, vo name}
		String content = quillEditor.readContentTxtFileToStr(lectureQnaVO.getContent(), "lectureQna");
		lectureQnaVO.setContent(content);
		return lectureQnaVO;
	}

	// 질문 검색
	@Override
	public Map<String,Object> searchQna(LectureQnaVO vo) {
		List<LectureQnaVO> list = mapper.searchLectureQna(vo);
		List<LectureQnaVO> searchResult = new ArrayList<>();
		changeNoneHtml(list);

		String keyword = vo.getKeyword();
		int total = 0;

		// 전체조회 리스트 돌면서 검색키워드 있는 vo객체만 result에 담음
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getNoneHtmlContent().toUpperCase().contains(keyword.toUpperCase())
					|| list.get(i).getTitle().toUpperCase().contains(keyword.toUpperCase())) {
				System.out.println(list.get(i));
				searchResult.add(list.get(i));
				// 페이징할때 다음페이지 존재여부 확인용으로 전체 검색데이터 수 넘김
				total++;
			}
		}

		// 페이징처리
		while(searchResult.size()>vo.getPageNum()*5) {
			searchResult.remove(vo.getPageNum()*5);
		}
		
		Map<String,Object> map = new HashMap<>();
		map.put("list", searchResult);
		map.put("total", total);
		return map;
	}

}
