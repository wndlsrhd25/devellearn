package com.yedam.devellearn.lecture.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.devellearn.lecture.mapper.CartMapper;
import com.yedam.devellearn.lecture.service.CartService;
import com.yedam.devellearn.lecture.service.CartVO;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	CartMapper mapper;

	// 고유번호 조회
	@Override
	public int getNo() {
		return mapper.getNo();
	}

	// 장바구니 등록
	@Override
	public int insertCart(CartVO cartVO) {
		return mapper.insert(cartVO);
	}

	// 장바구니 한개 삭제
	@Override
	public int deleteCart(int cartNo) {
		return mapper.delete(cartNo);
	}
	
	// 장바구니 여러개 삭제
	@Override
	public int deleteSelectedCart(Map<String, List<Integer>> map) {
		return mapper.deleteSelectedCart(map);
	}
	
	// 장바구니 전체삭제 - 회원아이디로 삭제
	@Override
	public int deleteAllCartByMemberId(String memberId) {
		return mapper.deleteAllByMemberId(memberId);
	}

	// 장바구니 전체 조회 - 회원아이디로 조회
	@Override
	public List<CartVO> getCartListByMemberId(String memberId) {
		return mapper.selectAllByMemberId(memberId);
	}




	
	
}
