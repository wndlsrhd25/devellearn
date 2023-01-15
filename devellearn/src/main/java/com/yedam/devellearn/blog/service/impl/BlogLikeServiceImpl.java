package com.yedam.devellearn.blog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.blog.mapper.BlogLikeMapper;
import com.yedam.devellearn.blog.service.BlogLikeService;
import com.yedam.devellearn.blog.service.BlogLikeVO;
import com.yedam.devellearn.blog.service.BlogPostVO;
import com.yedam.devellearn.quill_editor.QuillEditor;

@Service
public class BlogLikeServiceImpl implements BlogLikeService {

	
	@Autowired
	BlogLikeMapper mapper;
	QuillEditor quillEditor = new QuillEditor();
	
	//좋아요 등록
	@Override
	public int insertBlogLike(BlogLikeVO blogLikeVO) {
		return mapper.insertBlogLike(blogLikeVO);
	}

	//좋아요 삭제
	@Override
	public int deleteBlogLike(int likeNo) {
		return mapper.deleteBlogLike(likeNo);
	}

	//좋아요 수
	@Override
	public int selectLikeCnt(int PostNo) {
		return mapper.selectLikeCnt(PostNo);
	}

	//좋아요 여부 확인
	@Override
	public BlogLikeVO blogLike(BlogLikeVO blogLikeVO) {
		return mapper.blogLike(blogLikeVO);
	}

	//좋아요 전체 조회(CARD)
	@Override
	public Page<BlogPostVO> getLikeCard(String memberId) {
		Page<BlogPostVO> list = mapper.getLikeCard(memberId);
		this.changeNoneHtml(list);
		return list;
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


}
