package com.yedam.devellearn.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.blog.mapper.BookmarkMapper;
import com.yedam.devellearn.blog.service.BlogPostVO;
import com.yedam.devellearn.blog.service.BookmarkService;
import com.yedam.devellearn.blog.service.BookmarkVO;
import com.yedam.devellearn.quill_editor.QuillEditor;

@Service
public class BookmarkServiceImpl implements BookmarkService {
	
	
	@Autowired
	BookmarkMapper mapper;
	QuillEditor quillEditor = new QuillEditor();
	
	//블로그 북마크 전체 조회
		@Override
		public Page<BlogPostVO> getBookmarkList(BlogPostVO blogPostVO) {
			Page<BlogPostVO> list = mapper.getBookmarkList(blogPostVO);
			this.changeNoneHtml(list);
		    return list;
		}

		//북마크 게시글 수 조회
		@Override
		public int getBookmarkCnt(String memberId) {
			return mapper.getBookmarkCnt(memberId);
		}
		
		//북마크 폴더별 게시글 수 조회
		@Override
		public List<BookmarkVO> getBookmarkFolder(String memberId) {
			return mapper.getBookmarkFolder(memberId);
		}
		
		//블로그 북마크 폴더 추가
		@Override
		public void insertBookmarkFolder(BookmarkVO bookmarkVO) {
			mapper.insertBookmarkFolder(bookmarkVO);
		}
		
		//블로그 북마크 폴더 삭제
		@Override
		public void deleteBookmarkFolder(BookmarkVO bookmarkVO) {
			mapper.deleteBookmarkFolder(bookmarkVO);
		}
		
		//블로그 북마크 이름 변경
		@Override
		public int updateBookmarkName(BookmarkVO bookmarkVO) {
			return mapper.updateBookmarkName(bookmarkVO);
		}

		//북마크 폴더별 전체 조회
		@Override
		public Page<BlogPostVO> getBookmarkFolderList(BlogPostVO blogPostVO) {
			Page<BlogPostVO> list = mapper.getBookmarkFolderList(blogPostVO);
			this.changeNoneHtml(list);
		     return list;
		}

		//북마크 등록
		@Override
		public int blogBookmark(BookmarkVO bookmarkVO) {
			return mapper.blogBookmark(bookmarkVO);
		}

		//북마크 삭제
		@Override
		public int deleteBookmark(int bookmarkNo) {
			return mapper.deleteBookmark(bookmarkNo);
		}

		//ontent html 태그 제거 - 리스트에 말줄임으로 들어갈 content
		private void changeNoneHtml(List<BlogPostVO> list) {
	      for (int i = 0; i < list.size(); i++) {
	         String content = quillEditor.readContentTxtFileToStr(list.get(i).getContent(), "blogPost");
	         String textWithoutTag = content.replaceAll("<p><img[^>]*></p>", " 사진 ");
	         textWithoutTag = textWithoutTag.replaceAll("<[^>]*>", "");
	         list.get(i).setNoneHtmlContent(textWithoutTag);
	      }
	   }

		//북마크 여부 확인(단건조회)
		@Override
		public BookmarkVO selectBookInfo(BookmarkVO bookmarkVO) {
			return mapper.selectBookInfo(bookmarkVO);
		}
}
