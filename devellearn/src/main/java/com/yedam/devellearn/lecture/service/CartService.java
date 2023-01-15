package com.yedam.devellearn.lecture.service;

import java.util.List;
import java.util.Map;

public interface CartService {
	// 고유번호 조회
	public int getNo(); 
	// 등록
	public int insertCart(CartVO cartVO);
	// 삭제
	public int deleteCart(int cartNO);
	// 여러개 삭제
	public int deleteSelectedCart(Map<String, List<Integer>> map);
	// 전체삭제 - 회원아이디로 삭제
	public int deleteAllCartByMemberId(String memberId);
	// 전체조회 - 회원아이디로 조회
	public List<CartVO> getCartListByMemberId(String memberId);
}
