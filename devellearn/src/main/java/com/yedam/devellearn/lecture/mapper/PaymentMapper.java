package com.yedam.devellearn.lecture.mapper;

import com.yedam.devellearn.lecture.service.PaymentVO;

public interface PaymentMapper {

	// 고유번호 조회
	public int getNo();

	// 등록
	public int insert(PaymentVO paymentVO);
}
