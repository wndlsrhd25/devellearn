package com.yedam.devellearn.blog.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.yedam.devellearn.blog.service.BlogFolderVO;
import com.yedam.devellearn.blog.service.BlogPostVO;
import com.yedam.devellearn.blog.service.BookmarkVO;

public interface BlogPostMapper {
	
	//블로그 게시글 번호 조회
	public BlogPostVO getBlogPostNo();
	
	//블로그 메인 전체 조회
	public Page<BlogPostVO> getBlogList(Map<String, Object> map);
	
	//블로그 검색어 전체 조회
	public Page<BlogPostVO> serchBlogList(Map<String, Object> map);
	
	//블로그 태그 전체 조회
	public Page<BlogPostVO> serchBlogTag(Map<String, Object> map);
	
	//블로그 게시글 단건 조회
	public BlogPostVO getBlogInfo(BlogPostVO blogPostVO);
	
	//블로그 게시글 전체 조회(내블로그, 타블로그)
	public Page<BlogPostVO> getMyBlogList(BlogPostVO blogPostVO);
	
	//블로그 게시글 검색(내블로그, 타블로그)
	public Page<BlogPostVO> getMyBlogSearch(BlogPostVO blogPostVO);
	
	//블로그 프로필 조회
	public String selectProfile(String blogId);
	
	//좋아요 게시글 조회
	public Page<BlogPostVO> getLikeList(BlogPostVO blogPostVO);
	
	//블로그 게시글 삭제
	public int deleteBlogPost(int postNo);
	
	//블로그 게시글 등록
	public int insertBlogPost(BlogPostVO blogPostVO);
	
	//블로그 게시글 수정
	public int updateBlogPost(BlogPostVO blogPostVO);
	
	//블로그 조회수
	public int updateHitsCnt(BlogPostVO blogPostVO);
	
	//블로그 폴더명 조회
	public List<BlogFolderVO> getFolderList(String blogId);
	
	//블로그 폴더별 게시글 조회
	public Page<BlogPostVO> folderPostInfo(BlogPostVO blogPostVO);
	
	//블로그 폴더 이름 변경
	public int updateFolderName(BlogFolderVO blogFolderVO);
	
	//블로그 포스트 폴더 이름 변경
	public int updatePostFolder(BlogFolderVO blogFolderVO);
	
	//블로그 폴더 추가
	public int insertFolder(BlogFolderVO blogFolderVO);
	
	//블로그 폴더 삭제
	public int deleteFolder(BlogFolderVO blogFolderVO);
	
}
