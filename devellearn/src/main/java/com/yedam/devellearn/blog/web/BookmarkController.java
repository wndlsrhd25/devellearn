package com.yedam.devellearn.blog.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.blog.service.BlogPostVO;
import com.yedam.devellearn.blog.service.BookmarkService;
import com.yedam.devellearn.blog.service.BookmarkVO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*", maxAge = 3600)
public class BookmarkController {

	@Autowired
	BookmarkService service;
	
	//블로그 북마크 전체 조회
		@PostMapping("/bookmark")
		public PageInfo<BlogPostVO> selectBookmarkList(@RequestBody BlogPostVO blogPostVO){
			blogPostVO.setMemberId(blogPostVO.getMemberId());
			PageHelper.startPage(blogPostVO.getPageNum(),9);
			return PageInfo.of(service.getBookmarkList(blogPostVO));
		}
		
		//블로그 북마크 전체 수 조회
		@GetMapping("/bookmark/@{memberId}/bookCnt")
		public int getBookmarkCnt(@PathVariable String memberId) {
			System.out.println(service.getBookmarkCnt(memberId));
			return service.getBookmarkCnt(memberId);	
		}
		
		
		//블로그 북마크 폴더별 게시글 수 조회
		@GetMapping("/bookmark/@{memberId}/bookmarkFolder")
		public List<BookmarkVO> selectBookmarkFolder(@PathVariable String memberId) {
			return service.getBookmarkFolder(memberId);
		}
		
		//블로그 북마크 폴더 추가
		@PostMapping("/bookmarkFolder")
		public BookmarkVO insertBookmarkFolder(@RequestBody BookmarkVO bookmarkVO) {
			service.insertBookmarkFolder(bookmarkVO);
			return bookmarkVO;
			
		}
		
		//블로그 북마크 폴더 삭제
		@DeleteMapping("/deleteBookmark")
		public BookmarkVO deleteBookmarkFolder(@RequestBody BookmarkVO bookmarkVO) {
			service.deleteBookmarkFolder(bookmarkVO);
			return bookmarkVO;
		}
		
		//블로그 북마크 이름 변경
		@PutMapping("/bookmarkUpdate")
		public BookmarkVO updateBookmarkName(@RequestBody BookmarkVO bookmarkVO) {
			service.updateBookmarkName(bookmarkVO);
			return bookmarkVO;
		}
	
		//블로그 북마크 폴더별 조회
		@PostMapping("/bookmarkFolderList")
		public PageInfo<BlogPostVO> getBookmarkFolderList(@RequestBody BlogPostVO blogPostVO) {
			blogPostVO.setMemberId(blogPostVO.getMemberId());
			blogPostVO.setBookFolderName(blogPostVO.getBookFolderName());
			PageHelper.startPage(blogPostVO.getPageNum(),9);
			return PageInfo.of(service.getBookmarkFolderList(blogPostVO));
		}
		
		//북마크 등록
		@PostMapping("/bookmarkBlog")
		public BookmarkVO blogBookmark(@RequestBody BookmarkVO bookmarkVO) {
			service.blogBookmark(bookmarkVO);
			return bookmarkVO;
		}
		
		//북마크 삭제
		@DeleteMapping("/deleteBookmark/{bookmarkNo}")
		public int deleteBookmark(@PathVariable int bookmarkNo) {
			return service.deleteBookmark(bookmarkNo);
			
		}
		//북마크 여부 확인(단건조회)-memberId, postNo
		@PostMapping("/bookmarkInfo")
		public BookmarkVO selectBookInfo(@RequestBody BookmarkVO bookmarkVO) {
			return service.selectBookInfo(bookmarkVO);
		}
		
}
