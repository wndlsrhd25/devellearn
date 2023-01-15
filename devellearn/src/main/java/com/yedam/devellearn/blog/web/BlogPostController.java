package com.yedam.devellearn.blog.web;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.blog.service.BlogFolderVO;
import com.yedam.devellearn.blog.service.BlogLikeService;
import com.yedam.devellearn.blog.service.BlogLikeVO;
import com.yedam.devellearn.blog.service.BlogPostService;
import com.yedam.devellearn.blog.service.BlogPostVO;
import com.yedam.devellearn.file.FileHandler;


@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api")
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
public class BlogPostController {
	
	

	@Autowired
	BlogPostService service;
	
	@Autowired
	BlogLikeService lservice;
	
	//전체조회(4칸)
	@PostMapping("/blogs")
	public PageInfo<BlogPostVO> selectBlogList(@RequestBody Map<String, Object> map,HttpSession httpSession) {
		String order = (String) map.get("order");
		 order +=" desc";
		
		//비회원인 경우-공개글만 보여야함
		if(httpSession.getAttribute("id")!=null) {
			
		map.put("memberId", ((String)httpSession.getAttribute("id")));	
		
			}
		
		//본인회원인 경우-내글은(공개,비공개), 프로젝트글도(공개,멤버공개)
		//다른회원 경우 - 글(공개), 프젝(공개)
		//페이징 하는거

		PageHelper.startPage(Integer.parseInt(String.valueOf( map.get("pageNum"))),16,order);
		return PageInfo.of(service.getBlogList(map));
		
	}
	
	
	//블로그 검색어 전체조회
	@PostMapping("/blogs/search")
	public PageInfo<BlogPostVO> searchBlogList(@RequestBody Map<String, Object> map,HttpSession httpSession) {
		String order = (String) map.get("order");
		 order +=" desc";
		
		if(httpSession.getAttribute("id")!=null) {
			map.put("memberId", ((String)httpSession.getAttribute("id")));	
		}
		PageHelper.startPage(Integer.parseInt(String.valueOf( map.get("pageNum"))),16,order);
		return PageInfo.of(service.serchBlogList(map));
	}
		
	
	//블로그 태그 검색 전체조회
//	@PostMapping("/blogs/searchTags")
//	public PageInfo<BlogPostVO> serchBlogTag(@RequestBody Map<String, Object> map,HttpSession httpSession) {
//		String order = (String) map.get("order");
//		 order +=" desc";
//		
//		if(httpSession.getAttribute("id")!=null) {
//			map.put("memberId", ((String)httpSession.getAttribute("id")));	
//		}
//		PageHelper.startPage(Integer.parseInt(String.valueOf( map.get("pageNum"))),16,order);
//		return PageInfo.of(service.serchBlogTag(map));
//	}
		
	//단건 조회
	@PostMapping("/blogs/detailPost")
	public BlogPostVO selectBlogPostInfo(@RequestBody BlogPostVO blogPostVO) {
		System.out.println("조회 아이디" +blogPostVO.getMemberId());
		BlogPostVO blogInfo = service.getBlogInfo(blogPostVO);
		
		//좋아요여부도 함께 조회
		BlogLikeVO blogLikeVO = new BlogLikeVO();
		blogLikeVO.setMemberId(blogPostVO.getMemberId());
		blogLikeVO.setPostNo(blogPostVO.getPostNo());
		 
		blogInfo.setBlogLikeVO(lservice.blogLike(blogLikeVO));
		
		return blogInfo;
	}
	
	
	//내블로그, 타블로그 게시글 전체조회(3칸)
	@PostMapping("/blogs/postHome")
	public PageInfo<BlogPostVO> selectMyBlogList(@RequestBody BlogPostVO blogPostVO,HttpSession session){
		if(session.getAttribute("id")!=null) {
			blogPostVO.setMemberId((String)session.getAttribute("id"));	
		}
		PageHelper.startPage(blogPostVO.getPageNum(),9);
		return PageInfo.of(service.getMyBlogList(blogPostVO));
	}
	
	

	//내블로그, 타블로그 게시글 검색(3칸)
		@PostMapping("/blogs/postHome/search")
		public PageInfo<BlogPostVO> getMyBlogSearch(@RequestBody BlogPostVO blogPostVO,HttpSession session) {

			if(session.getAttribute("id")!=null) {
				blogPostVO.setMemberId((String)session.getAttribute("id"));	
			}
			PageHelper.startPage(blogPostVO.getPageNum(),9);
			return PageInfo.of(service.getMyBlogSearch(blogPostVO));
		}
		
	
	
	
	//블로그 프로필 조회
//	@GetMapping("/blogs/@{blogId}/profile")
//	public String selectProfile(@PathVariable String blogId) {
//		BlogPostVO blogPostVO = new BlogPostVO();
//		blogPostVO.setBlogId(blogId);
//		return service.selectProfile(blogId);
//	}
//	
	
	
	//블로그 게시글 삭제
	@DeleteMapping("/blogs/{postNo}")
	public BlogPostVO deleteBlogPost(MultipartFile image, @PathVariable int postNo,@RequestParam String thumbnail) {
		
		BlogPostVO blogPostVO = new BlogPostVO();
		
		FileHandler fileHandler = new FileHandler();
		blogPostVO.setThumbnail(thumbnail);
		
		System.out.println("삭제할 썸네일 이름" + blogPostVO.getThumbnail());
		
		// 삭제(이미지)- 디폴트 이미지는 삭제 막기
		if(!blogPostVO.getThumbnail().equals("blog_default.jpg") || !blogPostVO.getThumbnail().equals("project_default.jpg")) {
			fileHandler.deleteFile("img/blog", blogPostVO.getThumbnail());
		}
		
		blogPostVO.setPostNo(postNo);
		service.deleteBlogPost(blogPostVO);
		return blogPostVO;
	}

	
	
	//블로그 게시글 등록
	@PostMapping("/blogs/insert")
	public int insertBlogPost(MultipartFile image, BlogPostVO blogPostVO) throws IllegalStateException, IOException {
		FileHandler fileHandler = new FileHandler();
		
		String fileName = fileHandler.uploadFile("img/blog","blogPost"+ blogPostVO.getPostNo(), image);
		System.out.println("파일명"+fileName);
		
		blogPostVO.setThumbnail(fileName);
		
		
		return service.insertBlogPost(blogPostVO);
	}
	
	
	//블로그 게시글 수정
	@PostMapping("/blogs/update")
	public int updateBlogPost(MultipartFile image,BlogPostVO blogPostVO) throws IllegalStateException, IOException {
		
		// 기존파일 삭제
		FileHandler fileHandler = new FileHandler();
		
		System.out.println(blogPostVO.getThumbnail());
		
		//디폴트 이미지가 아닌경우만 삭제
		if(!blogPostVO.getThumbnail().equals("blog_default.jpg") || !blogPostVO.getThumbnail().equals("project_default.jpg")) {
			fileHandler.deleteFile("img/blog", blogPostVO.getThumbnail());
		}
		
		//새롭게 등록
		String fileName = fileHandler.uploadFile("img/blog", "blogPost"+ blogPostVO.getPostNo(), image);
		blogPostVO.setThumbnail(fileName);
		
		
		System.out.println("새로운파일명"+fileName);
		return service.updateBlogPost(blogPostVO);
	}

	//조회수
	@PostMapping("/blogs/hits")
	public int updateHitsCnt(@RequestBody BlogPostVO blogPostVO){
	return service.updateHitsCnt(blogPostVO);	
	}
	
	
	//블로그 폴더명 조회
	@GetMapping("/blogs/@{blogId}/folder")
	public List<BlogFolderVO> selectFolderName(@PathVariable String blogId){
		return service.getFolderList(blogId);
	}
	
	//블로그 폴더별 게시글 조회
	@PostMapping("/blogs/postHome/folder")
	public PageInfo<BlogPostVO> folderPostInfo(@RequestBody BlogPostVO blogPostVO,HttpSession session){
		
		if(session.getAttribute("id")!=null) {
			blogPostVO.setMemberId((String)session.getAttribute("id"));	
		}
		PageHelper.startPage(blogPostVO.getPageNum(),9);
		return PageInfo.of(service.folderPostInfo(blogPostVO));
		
	}
	
	//블로그 폴더 이름 변경
	@PostMapping("/blogs/folder/update")
	public int updateFolderName(@RequestBody BlogFolderVO blogFolderVO) {
		return service.updateFolderName(blogFolderVO);
	}
	
	//블로그 폴더 추가
	@PostMapping("/blogs/folder/insert")
	public int insertFolder(@RequestBody BlogFolderVO blogFolderVO) {
		return service.insertFolder(blogFolderVO);
		
	}
	
	//블로그 폴더 삭제
	@DeleteMapping("/blogs/folder/delete")
	public int deleteFolder(@RequestBody BlogFolderVO blogFolderVO) {
		return service.deleteFolder(blogFolderVO);
	}
}
