package com.yedam.devellearn.lecture.service;

public interface PaymentService {

	// 고유번호 조회
	public int getNo();

	// 등록
	public int insertPayment(PaymentVO paymentVO);
}
