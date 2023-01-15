package com.yedam.devellearn.lecture.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.devellearn.lecture.service.PaymentService;
import com.yedam.devellearn.lecture.service.PaymentVO;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")

public class PaymentController {
	
	@Autowired
	PaymentService service;
	
	@PostMapping("/insertPayment")
	public int insertPayment(@RequestBody Map<String, List<PaymentVO>> map) {
		List<PaymentVO> list = map.get("payList");
		
		for (PaymentVO paymentVO : list) {
			paymentVO.setPaymentNo(service.getNo());
			service.insertPayment(paymentVO);
		}
		return 1;
	}
	

}
