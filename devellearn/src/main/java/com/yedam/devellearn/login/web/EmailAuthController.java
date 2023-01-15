package com.yedam.devellearn.login.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.devellearn.login.service.LoginService;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class EmailAuthController {
	
	@Autowired
	MailSender mailSender;
	
	@Autowired
	LoginService service;
	
	
	/**
	 * 이메일 인증할때 사용할 메소드
	 * 이메일 주소를 Get 방식으로 넘겨주면
	 * 인증코드(6자리)를 이메일 발송하고
	 * 요청한 곳에 인증번호(6자리)를 Return 해줌
	 *  
	 * @param data(이메일 주소) email : ""
	 * @return int(인증번호)
	 */		
	@GetMapping("/emailSending/{email}")
	public int emailValidation(@PathVariable String email) {
		System.out.println("===========Mail Validation RUN");				
		SimpleMailMessage msg = new SimpleMailMessage();
		
			
		String key = "";
		for(int i=0; i<6; i++) {
			int rand = (int) (Math.random()*10); // 0 ~ 9
			key += rand;
		}		

		msg.setTo(email);
		msg.setSubject("devellearn 회원가입을 위한 인증메일입니다.");		
		msg.setText("안녕하세요. devellearn입니다."
				  + "\n 회원가입을 위한 인증번호를 확인해주세요. "
				  + "\n"
				  + "\n 아래의 인증번호를 입력하여 인증을 완료해주세요 "
				  + "\n ===================================  "
				  + "\n" + key);
		msg.setFrom("tlskahs_25@naver.com");
		mailSender.send(msg);
		
		return Integer.parseInt(key);
	}
	
//	@GetMapping("/emailCheck")
//	public int emailCheck(@RequestParam String email) {
//	
//		int result = service.emailCheck(email);
//		System.out.println("인증번호 맞는지 = "+result);
//		
//		return result;
//	}

}
