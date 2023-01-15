package com.yedam.devellearn.lecture.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.devellearn.lecture.service.CartService;
import com.yedam.devellearn.lecture.service.CartVO;

/**
 * @create 221006
 * @author 서지완
 * @title 장바구니 관련 컨트롤러
 */
@RestController
@CrossOrigin("*")

@RequestMapping("/api")
public class CartController {

	@Autowired
	CartService service;

	// 장바구니 전체 조회 - 회원아이디로 조회
	@GetMapping("/cart/{memberId}")
	public List<CartVO> cartList(@PathVariable String memberId) {
		System.out.println("@@@컨트롤러는 탐@@@");
		List<CartVO> list = service.getCartListByMemberId(memberId);
		return list;
	}

	// 장바구니 등록
	@PostMapping("/cart")
	public int addCart(@RequestBody CartVO cartVO) {
		cartVO.setCartNo(service.getNo());
		return service.insertCart(cartVO);
	}

	// 장바구니 한개삭제
	@DeleteMapping("/deleteCart/{cartNo}")
	public int deleteCart(@PathVariable int cartNo) {
		return service.deleteCart(cartNo);
	}

	// 장바구니 여러개 삭제
	@PostMapping("/deleteSelectedCart")
	public int deleteSelectedCart(@RequestBody Map<String, List<Integer>> map) {
		return service.deleteSelectedCart(map);
	}

	// 장바구니 전체삭제
	@DeleteMapping("/deleteAllCart/{memberId}")
	public int deleteCartAll(@PathVariable String memberId) {
		return service.deleteAllCartByMemberId(memberId);
	}

}
