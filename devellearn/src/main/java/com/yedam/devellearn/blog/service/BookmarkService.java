package com.yedam.devellearn.blog.service;

import java.util.List;

import com.github.pagehelper.Page;


public interface BookmarkService {

	//블로그 북마크 전체 조회
		public Page<BlogPostVO> getBookmarkList(BlogPostVO blogPostVO);
		
		//블로그 북마크 전체 수 조회
		public int getBookmarkCnt(String memberId);
		
		//블로그 북마크 폴더별 게시글 수 조회
		public List<BookmarkVO> getBookmarkFolder(String memberId);
		
		//블로그 북마크 폴더 추가
		public void insertBookmarkFolder(BookmarkVO bookmarkVO);
		
		//블로그 북마크 폴더 삭제
		public void deleteBookmarkFolder(BookmarkVO bookmarkVO);
			
		//블로그 북마크 폴더 이름 변경
		public int updateBookmarkName(BookmarkVO bookmarkVO);
		
		//블로그 폴더별 전체 조회
		public Page<BlogPostVO> getBookmarkFolderList(BlogPostVO blogPostVO);

		//블로그 북마크 등록
		public int blogBookmark(BookmarkVO bookmarkVO);
		
		//블로그 북마크 삭제
		public int deleteBookmark(int bookmarkNo);

		//북마크 확인 여부
		public BookmarkVO selectBookInfo(BookmarkVO bookmarkVO);

}
