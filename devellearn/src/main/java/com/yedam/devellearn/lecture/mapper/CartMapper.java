package com.yedam.devellearn.lecture.mapper;

import java.util.List;
import java.util.Map;

import com.yedam.devellearn.lecture.service.CartVO;

public interface CartMapper {
	// 고유번호 조회
	public int getNo();

	// 등록
	public int insert(CartVO cartVO);

	// 단건삭제
	public int delete(int cartNo);

	// 여러개 삭제
	public int deleteSelectedCart(Map<String, List<Integer>> map);

	// 전체삭제 - 회원아이디로 삭제
	public int deleteAllByMemberId(String memberId);

	// 전체조회 - 회원아이디로 조회
	public List<CartVO> selectAllByMemberId(String memberId);

}
