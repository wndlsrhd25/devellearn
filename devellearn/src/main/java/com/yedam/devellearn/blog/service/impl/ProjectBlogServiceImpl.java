package com.yedam.devellearn.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.blog.mapper.ProjectBlogMapper;
import com.yedam.devellearn.blog.service.BlogPostVO;
import com.yedam.devellearn.blog.service.MyBlogVO;
import com.yedam.devellearn.blog.service.ProjectBlogService;
import com.yedam.devellearn.quill_editor.QuillEditor;

@Service
public class ProjectBlogServiceImpl implements ProjectBlogService {

	
	@Autowired
	ProjectBlogMapper mapper;
	QuillEditor quillEditor = new QuillEditor();
	
	
	// content html 태그 제거 - 리스트에 말줄임으로 들어갈 content
	private void changeNoneHtml(List<BlogPostVO> list) {
      for (int i = 0; i < list.size(); i++) {
         String content = quillEditor.readContentTxtFileToStr(list.get(i).getContent(), "blogPost");
         String textWithoutTag = content.replaceAll("<p><img[^>]*></p>", " 사진 ");
         textWithoutTag = textWithoutTag.replaceAll("<[^>]*>", "");
         list.get(i).setNoneHtmlContent(textWithoutTag);
      }
   }
		
	//프로젝트 가입 리스트 
	@Override
	public List<BlogPostVO> getProjectCard(BlogPostVO blogPostVO) {
		List<BlogPostVO> list = mapper.getProjectCard(blogPostVO);
		return list;
	}

	//프로젝트 게시글 전체조회 
	@Override
	public Page<BlogPostVO> getProjectPost(BlogPostVO blogPostVO) {
		Page<BlogPostVO> list = mapper.getProjectPost(blogPostVO);
		this.changeNoneHtml(list);
		return list;
	}
	
	//프로젝트 폴더별 게시글 조회
	@Override
	public Page<BlogPostVO> projectFolderInfo(BlogPostVO blogPostVO) {
		Page<BlogPostVO> list = mapper.projectFolderInfo(blogPostVO);
		this.changeNoneHtml(list);
		return list;
	}

	//프로젝트 썸네일 조회
	@Override
	public String selectProfile(String blogId) {
		return mapper.selectProfile(blogId);
	}

	//프로젝트 블로그 검색어 조회
	@Override
	public Page<BlogPostVO> searchProjectPost(BlogPostVO blogPostVO) {
		Page<BlogPostVO> list = mapper.searchProjectPost(blogPostVO);
		this.changeNoneHtml(list);
		return list;
	}
	
	//프로젝트 탈퇴
	@Override
	public int deleteProjectBlog(BlogPostVO blogPostVO) {
		mapper.updateProjectInfo(blogPostVO);
		return mapper.deleteProjectBlog(blogPostVO);
	}

	//프로젝트 참여 여부
	@Override
	public int selectPorjectId(MyBlogVO myBlogVO) {	
		return mapper.selectPorjectId(myBlogVO);
	}


	
	
}
