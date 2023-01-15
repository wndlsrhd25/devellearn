package com.yedam.devellearn.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.devellearn.blog.mapper.BlogCommentMapper;
import com.yedam.devellearn.blog.service.BlogCommentService;
import com.yedam.devellearn.blog.service.BlogCommentVO;
import com.yedam.devellearn.blog.service.BlogPostVO;
import com.yedam.devellearn.lecture.service.LectureAnswerVO;
import com.yedam.devellearn.quill_editor.QuillEditor;

@Service
public class BlogCommentServiceImpl implements BlogCommentService {

	
	@Autowired
	BlogCommentMapper mapper;
	QuillEditor quillEditor = new QuillEditor();

	
	//이거로 다시 바꿔야함
	//댓글 전체 조회(post_no)
	@Override
	public List<BlogCommentVO> selectComment(BlogCommentVO blogCommentVO) {
		List<BlogCommentVO> list = mapper.selectComment(blogCommentVO);
		for (BlogCommentVO blogCommentVO2 : list) {
			String content = quillEditor.readContentTxtFileToStr(blogCommentVO2.getContent(), "blogComment");
			blogCommentVO2.setContent(content);
		}
		return list;
	}

	//블로그 댓글 입력
	@Override
	public void insertComment(BlogCommentVO blogCommentVO) {
		String contentFileName = quillEditor.saveQnaContentToTxtFile(blogCommentVO.getContent(), "blogComment");
		blogCommentVO.setContent(contentFileName);
		mapper.insertComment(blogCommentVO);

	}

	//블로그 댓글 수정
	@Override
	public int updateBlogComment(BlogCommentVO blogCommentVO) {
		
		if(blogCommentVO.getContent() !=null) {
			BlogCommentVO Info = mapper.selectCommentOne(blogCommentVO);
			String deleteFolder = quillEditor.deleteContentTxtFileToStr(Info.getContent(), "blogComment");
			 String contentFileName = quillEditor.saveQnaContentToTxtFile(blogCommentVO.getContent(), "blogComment");
			 blogCommentVO.setContent(contentFileName);
			 
		}
		return mapper.updateBlogComment(blogCommentVO);
		
	}
		
	//블로그 댓글 삭제
	@Override
	public int deleteBlogComment(int commentNo) {
		return mapper.deleteBlogComment(commentNo);
	}
	
	
	//블로그 댓글 단건 조회
	@Override
	public BlogCommentVO selectCommentOne(BlogCommentVO blogCommentVO) {
		blogCommentVO = mapper.selectCommentOne(blogCommentVO);
		String content = quillEditor.readContentTxtFileToStr(blogCommentVO.getContent(), "blogComment");
		blogCommentVO.setContent(content);
	     return blogCommentVO;
	}

	
	//대댓글
	@Override
	public void insertCommentReply(BlogCommentVO blogCommentVO) {
		String contentFileName = quillEditor.saveQnaContentToTxtFile(blogCommentVO.getContent(), "blogComment");
		blogCommentVO.setContent(contentFileName);
		mapper.insertCommentReply(blogCommentVO);
		
	}
		
		
	// content html 태그 제거 - 리스트에 말줄임으로 들어갈 content
	private void changeNoneHtml(List<BlogPostVO> list) {
      for (int i = 0; i < list.size(); i++) {
         String content = quillEditor.readContentTxtFileToStr(list.get(i).getContent(), "blogComment");
         String textWithoutTag = content.replaceAll("<p><img[^>]*></p>", " 사진 ");
         textWithoutTag = textWithoutTag.replaceAll("<[^>]*>", "");
         list.get(i).setNoneHtmlContent(textWithoutTag);
      }
   }

	

}
