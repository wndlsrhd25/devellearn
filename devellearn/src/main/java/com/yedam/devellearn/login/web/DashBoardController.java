package com.yedam.devellearn.login.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedam.devellearn.board.service.BoardService;
import com.yedam.devellearn.lecture.service.LectureService;
import com.yedam.devellearn.lecture.service.MyLectureService;
import com.yedam.devellearn.login.service.LoginService;
import com.yedam.devellearn.login.service.MemberVO;
import com.yedam.devellearn.mentor.service.MentorService;
import com.yedam.devellearn.message.service.MessageService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class DashBoardController {

	@Autowired
	MessageService msgService;
	LoginService loginService;
	MyLectureService myLectureService;
	LectureService lectureService;
	MentorService mentorService;
	BoardService boardService;

	@GetMapping("/dashBoard")

	public Map<String, Object> dashBoard(HttpServletRequest request, Model model) {
		Map<String, Object> map = new HashMap<String, Object>();
		// String memberId = (String)session.getAttribute("id");
//		HttpSession session = request.getSession();
//		String memberId = (String) session.getAttribute("id");
//		HttpSession session = request.getSession(true);
//		MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
//		System.out.println("로그인된 멤버 세션에서 꺼내기" + loginMember);
//		MemberVO loginMember = (MemberVO) model.getAttribute("loginMember");
//		String memberId = loginMember.getMemberId();
//
//		System.out.println("세션 잘 찍히는가," + memberId);

		// 회원정보 가져오기
		//map.put("memberInfo", loginService.selectMember("ant"));
		// 수신 메세지 리스트 가져오기
		//map.put("message", msgService.recvMes("ant"));
		// 학습통계 - 수강한 강의 수 가져오기
		//map.put("myLecture", myLectureService.completeLecture("ant"));

		// 학습통계 - 개설한 강의 수 가져오기 status='승인';
		//map.put("lecture", lectureService.openedLecture("ant"));

		// 학습통계 - 완료 멘토링 수 가져오기 status='멘토종료';
		//map.put("mentor", mentorService.finishMentor("ant"));
    
		// 내가쓴 게시글 (map)
		//map.put("board", boardService.getMyBoardListAll(map));

		return map;
	}

}
