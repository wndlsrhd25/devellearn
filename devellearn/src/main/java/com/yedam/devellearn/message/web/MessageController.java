package com.yedam.devellearn.message.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.devellearn.login.service.MemberVO;
import com.yedam.devellearn.message.service.MessageService;
import com.yedam.devellearn.message.service.MessageVO;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class MessageController {

	@Autowired
	MessageService service;
	
	//로그인한 사람것 보는 것..
	//마이페이지나 메세지는 본인이 보는 것은 세션에서 꺼내서 쓸 수 있게..
	
	
	
	// 수신 쪽지 전체조회
//	@GetMapping("/recvMes")
//	public List<MessageVO> recvMes(HttpSession session) {
//		String recvId= (String) session.getAttribute("id");
//		System.out.println(recvId);
//		return service.recvMes(recvId);
//	}
	
	//기존 코드 ▼
	@GetMapping("/recvMes")
	public List<MessageVO> recvMes(@RequestParam(value="recvId", required=false) String recvId) {
		System.out.println(recvId);
		return service.recvMes(recvId);
	}
	

	
	// 발신 쪽지 전체조회
//	@GetMapping("/sendMes")
//	public List<MessageVO> sendMes(HttpSession session) {
//		String sendId= (String) session.getAttribute("id");
//		System.out.println(sendId);
//		return service.sendMes(sendId);
//	}
	
	//기존 코드 ▼
	@GetMapping("/sendMes")
	public List<MessageVO> sendMes(@RequestParam(value="sendId", required=false) String sendId) {
		System.out.println(sendId);
		return service.sendMes(sendId);
	}
	

	// 등록
	@PostMapping("/mesInput")
	public int insertMessage(@RequestBody MessageVO messageVO) {
		return service.insertMessage(messageVO);
	}

	// 수정
	@PutMapping("/mesUpdate/{mesNo}")
	public void updateMessage(@PathVariable int mesNo) {
		service.updateMessage(mesNo);
	}

	// 삭제
	@DeleteMapping("/mesDelete/{mesNo}")
	public void deleteMessage(@PathVariable int mesNo) {
		service.deleteMessage(mesNo);
	}
	

	// 단건조회
	@GetMapping("/message/{mesNo}")
	public MessageVO mesDetails (@PathVariable int mesNo) {
		return service.mesDetails(mesNo);
	}
	

}
