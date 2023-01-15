package com.yedam.devellearn.blog.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.blog.service.BlogPostVO;
import com.yedam.devellearn.blog.service.MyBlogVO;
import com.yedam.devellearn.blog.service.ProjectBlogService;
import com.yedam.devellearn.quill_editor.QuillEditor;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins="*")
public class ProjectBlogController {

	
	
	@Autowired
	ProjectBlogService service;
	QuillEditor quillEditor = new QuillEditor();
	
	
	//프로젝트 가입 리스트 
	@PostMapping("/projectBlog")
	public List<BlogPostVO> getProjectCard(@RequestBody BlogPostVO blogPostVO){
		blogPostVO.setMemberId(blogPostVO.getMemberId());
		return service.getProjectCard(blogPostVO);
	}
	
	//프로젝트 게시글 전체조회 
	@PostMapping("/projectBlog/list")
	public PageInfo<BlogPostVO> getProjectPost(@RequestBody BlogPostVO blogPostVO,HttpSession session){
		if(session.getAttribute("id")!=null) {
			blogPostVO.setMemberId((String)session.getAttribute("id"));	
		}
		PageHelper.startPage(blogPostVO.getPageNum(),9);
		return  PageInfo.of(service.getProjectPost(blogPostVO));
	}
	
	//프로젝트 폴더별 게시글 전체조회
	@PostMapping("/projectBlog/folderlist")
	public PageInfo<BlogPostVO> projectFolderInfo(@RequestBody BlogPostVO blogPostVO,HttpSession session){
		if(session.getAttribute("id")!=null) {
			blogPostVO.setMemberId((String)session.getAttribute("id"));	
		}
		PageHelper.startPage(blogPostVO.getPageNum(),9);
		return  PageInfo.of(service.projectFolderInfo(blogPostVO));
	}
	
	//프로젝트 블로그 검색어 조회
	@PostMapping("/projectBlog/search")
	public PageInfo<BlogPostVO> searchProjectPost(@RequestBody BlogPostVO blogPostVO,HttpSession session){
		if(session.getAttribute("id")!=null) {
			blogPostVO.setMemberId((String)session.getAttribute("id"));	
		}
		PageHelper.startPage(blogPostVO.getPageNum(),9);
		return  PageInfo.of(service.searchProjectPost(blogPostVO));
	}
	
	//프로젝트 썸네일 조회
	@GetMapping("/projectBlog/{blogId}/profile")
	public String selectProfile(@PathVariable String blogId) {
		return service.selectProfile(blogId);
	}
	
	//프로젝트 탈퇴
	@DeleteMapping("/projectBlog/{blogId}/{memberId}")
	public int deleteProjectBlog(@PathVariable String blogId, @PathVariable String memberId) {
		BlogPostVO blogPostVO = new BlogPostVO();
		blogPostVO.setMemberId(memberId);
		blogPostVO.setBlogId(blogId);
		return service.deleteProjectBlog(blogPostVO);
	}
	
	//프로젝트 참여여부
	@PostMapping("/projectBlog/select")
	public int selectPorjectId(@RequestBody MyBlogVO myBlogVO) {
		return service.selectPorjectId(myBlogVO);
	}
}
