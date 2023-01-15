package com.yedam.devellearn.lecture.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.devellearn.lecture.mapper.PaymentMapper;
import com.yedam.devellearn.lecture.service.PaymentService;
import com.yedam.devellearn.lecture.service.PaymentVO;

@Service
public class PaymentServiceImpl implements PaymentService{

	@Autowired
	PaymentMapper mapper;
	
	@Override
	public int getNo() {
		return mapper.getNo();
	}

	@Override
	public int insertPayment(PaymentVO paymentVO) {
		return mapper.insert(paymentVO);
	}

}
