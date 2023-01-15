package com.yedam.devellearn.blog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;

@Service
public interface ProjectBlogService {

	//프로젝트 가입 리스트 
	public List<BlogPostVO> getProjectCard(BlogPostVO blogPostVO);
		
	//프로젝트 썸네일 조회
	public String selectProfile(String blogId);
	
	//프로젝트 게시글 전체조회 
	public Page<BlogPostVO> getProjectPost(BlogPostVO blogPostVO);
		
	//프로젝트 폴더별 게시글 조회
	public Page<BlogPostVO> projectFolderInfo(BlogPostVO blogPostVO);
	
	//프로젝트 검색어 조회
	public Page<BlogPostVO> searchProjectPost(BlogPostVO blogPostVO);
			
	//프로젝트 탈퇴
	public int deleteProjectBlog(BlogPostVO blogPostVO);
	
		
	//프로젝트 참여 여부
	public int selectPorjectId(MyBlogVO myBlogVO);
}
