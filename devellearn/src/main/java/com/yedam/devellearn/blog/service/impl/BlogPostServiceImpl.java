package com.yedam.devellearn.blog.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.blog.mapper.BlogLikeMapper;
import com.yedam.devellearn.blog.mapper.BlogPostMapper;
import com.yedam.devellearn.blog.service.BlogFolderVO;
import com.yedam.devellearn.blog.service.BlogLikeVO;
import com.yedam.devellearn.blog.service.BlogPostService;
import com.yedam.devellearn.blog.service.BlogPostVO;
import com.yedam.devellearn.quill_editor.QuillEditor;


@Service
public class BlogPostServiceImpl implements BlogPostService {

	
	@Autowired
	BlogPostMapper mapper;
	
	@Autowired
	BlogLikeMapper lmapper;
	
	QuillEditor quillEditor = new QuillEditor();

	//게시글 번호 조회
	@Override
	public int getBlogPostNo() {
		BlogPostVO vo = mapper.getBlogPostNo();
		return vo.getPostNo();
	}

	//블로그 전체 조회(4칸)
	@Override
	public Page<BlogPostVO> getBlogList(Map<String, Object> map) {
		Page<BlogPostVO> list = mapper.getBlogList(map);
		 this.changeNoneHtml(list);
	     return list;
	}

	//블로그 게시글 검색어 조회
	@Override
	public Page<BlogPostVO> serchBlogList(Map<String, Object> map) {
		Page<BlogPostVO> list = mapper.serchBlogList(map);
		this.changeNoneHtml(list);
	     return list;
	}
	
	//블로그 태그 검색 조회
	//@Override
//	public Page<BlogPostVO> serchBlogTag(Map<String, Object> map) {
//		Page<BlogPostVO> list = mapper.serchBlogTag(map);
//		this.changeNoneHtml(list);
//	     return list;
//	}

	
	//블로그 게시글 단건 조회
	@Override
	public BlogPostVO getBlogInfo(BlogPostVO blogPostVO) {
		
		 //단건조회
		 blogPostVO = mapper.getBlogInfo(blogPostVO);
		
	     // editor content txt 파일 읽기 - 파라미터 : {DB에 저장된 content fileName, vo name}
	     String content = quillEditor.readContentTxtFileToStr(blogPostVO.getContent(), "blogPost");
	     blogPostVO.setContent(content);
	    
	     return blogPostVO;
	}

	//블로그 홈 전체 조회(내블로그, 타블로그 3칸)
	@Override
	public Page<BlogPostVO> getMyBlogList(BlogPostVO blogPostVO) {
		Page<BlogPostVO> list = mapper.getMyBlogList(blogPostVO);
		this.changeNoneHtml(list);
	    return list;
	}
	
	//내블로그, 타블로그 게시글 검색(3칸)
	@Override
	public Page<BlogPostVO> getMyBlogSearch(BlogPostVO blogPostVO) {
		Page<BlogPostVO> list = mapper.getMyBlogSearch(blogPostVO);
		this.changeNoneHtml(list);
	     return list;
		
	}


	//블로그 프로필 조회
	@Override
	public String selectProfile(String blogId) {
		return mapper.selectProfile(blogId);
		
	}

	
	//블로그 좋아요 게시글 조회
	@Override
	public Page<BlogPostVO> getLikeList(BlogPostVO blogPostVO) {
		return mapper.getLikeList(blogPostVO);
	}
	

	//블로그 게시글 삭제
	@Override
	public void deleteBlogPost(BlogPostVO blogPostVO) {
		
		BlogPostVO Info = mapper.getBlogInfo(blogPostVO);
		//에디터 파일을 조회해서 삭제하기
		quillEditor.deleteContentTxtFileToStr(Info.getContent(), "blogPost");
		mapper.deleteBlogPost(blogPostVO.getPostNo());
	}

	//블로그 게시글 등록
	@Override
	public int insertBlogPost(BlogPostVO blogPostVO) {
	// quill editor로 작성한 내용 txt 파일로 저장 - 파라미터 : {editor content, vo name}
      String contentFileName = quillEditor.saveQnaContentToTxtFile(blogPostVO.getContent(), "blogPost");
      blogPostVO.setContent(contentFileName);
      return mapper.insertBlogPost(blogPostVO);
	          
	}
	

	//블로그 게시글 수정
	@Override
	public int updateBlogPost(BlogPostVO blogPostVO) {
	
		if (blogPostVO.getContent() != null) {
			BlogPostVO Info = mapper.getBlogInfo(blogPostVO);
			//파일 삭제
			quillEditor.deleteContentTxtFileToStr(Info.getContent(), "blogPost");
			//파일 등록
			 String contentFileName = quillEditor.saveQnaContentToTxtFile(blogPostVO.getContent(), "blogPost");
			 blogPostVO.setContent(contentFileName);
			
		}
		return mapper.updateBlogPost(blogPostVO);
	}

	
	
	// content html 태그 제거 - 리스트에 말줄임으로 들어갈 content
	private void changeNoneHtml(List<BlogPostVO> list) {
      for (int i = 0; i < list.size(); i++) {
         String content = quillEditor.readContentTxtFileToStr(list.get(i).getContent(), "blogPost");
         String textWithoutTag = content.replaceAll("<p><img[^>]*></p>", " 사진 ");
         textWithoutTag = textWithoutTag.replaceAll("<[^>]*>", "");
         list.get(i).setNoneHtmlContent(textWithoutTag);
      }
   }

	
	//블로그 조회수
	@Override
	public int updateHitsCnt(BlogPostVO blogPostVO) {
		return mapper.updateHitsCnt(blogPostVO);
	}

	//블로그 폴더명 조회
	@Override
	public List<BlogFolderVO> getFolderList(String blogId) {
		return mapper.getFolderList(blogId);
	}
	
	//블로그 폴더별 게시글 조회
	@Override
	public Page<BlogPostVO> folderPostInfo(BlogPostVO blogPostVO) {
		Page<BlogPostVO> list = mapper.folderPostInfo(blogPostVO);
		this.changeNoneHtml(list);
		return list;
	}
		
	//블로그 폴더 이름 변경
	@Override
	public int updateFolderName(BlogFolderVO blogFolderVO) {
		mapper.updateFolderName(blogFolderVO);
		return mapper.updatePostFolder(blogFolderVO);
	}

	//블로그 폴더 추가
	@Override
	public int insertFolder(BlogFolderVO blogFolderVO) {
		return mapper.insertFolder(blogFolderVO);
	}

	//블로그 폴더 삭제
	@Override
	public int deleteFolder(BlogFolderVO blogFolderVO) {
		return mapper.deleteFolder(blogFolderVO);
	}

	
	

	

}
