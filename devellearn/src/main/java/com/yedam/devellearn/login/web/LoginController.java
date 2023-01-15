package com.yedam.devellearn.login.web;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.login.service.ComMemberVO;
import com.yedam.devellearn.login.service.LoginService;
import com.yedam.devellearn.login.service.MemberVO;
import com.yedam.devellearn.message.service.MessageVO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class LoginController {

	@Autowired
	LoginService service;

	/* 회원가입(개인) */
	@PostMapping("/join")
	public int insertMember(@RequestBody MemberVO memberVO) {
		return service.insertMember(memberVO);
	}

	/* 로그인(개인) */
	@PostMapping("/login")
	public MemberVO loginCheck(@RequestBody MemberVO memberVO, Model model) {
		memberVO = service.loginCheck(memberVO);
		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		// Request 가지고 오기 HttpServletRequest httpRequest =
		// servletRequestAttribute.getRequest();//Session 가지고 오기 HttpSession
		// httpSession = servletRequestAttribute.getRequest().getSession(true);

		// 왜 세션을 가져오질 못하는데!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		System.out.println("로그인 세션 확인 membervo" + memberVO);
		// 로그인 성공하면
		if (memberVO != null) {
			System.out.println("@@@@@@@@@@@@@세션 담는 부분@@@@@@@@@@@@@");
			HttpSession httpSession = servletRequestAttribute.getRequest().getSession(true);
			System.out.println("@@@@@@@@@@@@@세션 담는 부분111111111111111111111111");
			httpSession.setAttribute("id", memberVO.getMemberId());
			System.out.println("@@@@@@@@@@@@@세션 담는 부분222222222222222222222222");
			httpSession.setAttribute("loginMember", memberVO);
			System.out.println("@@@@@@@@@@@@@세션 담는 부분333333333333333333333333");
			System.out.println("세션에 아이디###############" + (String) httpSession.getAttribute("id"));
//			model.addAttribute("loginMember", memberVO);
			System.out.println("@@@@@@@@@@@@@@@@@@@@@if문 종료@@@@@@@@@@@@@@@");
		}
		return memberVO;
	}
	@GetMapping("/logout")
	public void logout() {
		ServletRequestAttributes servletRequestAttribute = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession httpSession = servletRequestAttribute.getRequest().getSession(true);
		httpSession.removeAttribute("id");
	}

	/* 기업회원 로그인 */
	@PostMapping("/companyLogin")
	public ComMemberVO companyLogin(@RequestBody ComMemberVO comMemberVO) {
		return service.companyLogin(comMemberVO);
	}

	/* 아이디 중복체크 */
	@PostMapping("/idCheck")
	public int idCheck(@RequestBody Map map) {

		// 1이나오면 사용중인 아이디가 있다는 것, 1이 못쓴다는 것
		if (service.idCheck(map) == 1) {
			return 1;
		} else {
			return 0;
		}
	}

	/* 기업회원 아이디 중복체크 */
	@PostMapping("/comIdCheck")
	public int comIdCheck(@RequestBody Map map) {

		// 1이나오면 사용중인 아이디가 있다는 것, 1이 못쓴다는 것
		if (service.comIdCheck(map) == 1) {
			return 1;
		} else {
			return 0;
		}
	}

	/* 회원가입(개인) */
	@PostMapping("/companyJoin")
	public int insertComMember(@RequestBody ComMemberVO comMemberVO) {
		return service.insertComMember(comMemberVO);
	}

	/* 개인 회원 단건조회 */
	@GetMapping("/selectMember/{memberId}")
	public MemberVO selectMember(@PathVariable String memberId) {
		return service.selectMember(memberId);
	}

	/* 개인정보 수정 (업데이트_이메일, 비밀번호, 전화번호, 프로필) */
	@PutMapping("/updateInfo")
	public int updateIfo(MultipartFile image, MemberVO memberVO) throws IllegalStateException, IOException {

		// 삭제
		FileHandler fileHandler = new FileHandler();
		System.out.println("파일명확인하기"+memberVO.getProfile());
		if(!memberVO.getProfile().equals("default_profile.jpg")) {
			fileHandler.deleteFile("img/profile", memberVO.getProfile());
		}
		
		// 수정
		String fileName = fileHandler.uploadFile("img/profile", "members" + memberVO.getMemberId(), image);
		memberVO.setProfile(fileName);
		
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa" + memberVO.toString());
		System.out.println("파일이름" + fileName);
		
		return service.updateInfo(memberVO);

	}

	/* 기업 회원 단건조회 */
	@GetMapping("/selectComMember/{comId}")
	public ComMemberVO selectComMember(@PathVariable String comId) {
		return service.selectComMember(comId);
	}

	/* 기업회원정보 수정 (업데이트_이메일, 비밀번호, 전화번호, 프로필) */
	@PutMapping("/updateComInfo")
	public int updateComInfo(@RequestBody ComMemberVO comMemberVO) {
		return service.updateComInfo(comMemberVO);
	}

	/* 개인 회원 단건조회 */
	@PostMapping("/emailCheck")
	public int emailCheck(@RequestBody Map map) {

		// 1이나오면 일치하는 정보가 있다는 것, 1이 사용 가능한 것
		if (service.emailCheck(map) == 1) {
			return 1;
		} else {
			return 0;
		}
	}

	/* 개인회원정보 비밀번호 변경 */
	@PutMapping("/changePw")
	public int changePw(@RequestBody MemberVO memberVO) {
		return service.changePw(memberVO);
	}

	/* 기업회원정보 수정 */
	@PutMapping("/changeComPw")
	public int changeComPw(@RequestBody ComMemberVO comMemberVO) {
		return service.changeComPw(comMemberVO);
	}

	/* 회원탈퇴 */
	@PutMapping("/withdrawal/{memberId}")
	public int withdrawal(@PathVariable String memberId) {
		return service.withdrawal(memberId);
	}


	@GetMapping("/findId")
	public List<MemberVO> findId(@RequestParam String name, @RequestParam String phone) {
		return service.findId(name, phone);
	}


	@GetMapping("/findComId")
	public ComMemberVO findComId(@RequestParam String name, @RequestParam String phone) {
		return service.findComId(name, phone);
	}


	/* 탈퇴회원 아이디 중복체크 */
	@PostMapping("/withdrawIdCheck")
	public int withdrawIdCheck(@RequestBody Map map) {
		// 1이나오면 탈퇴한 아이디가 라는 것, 1이면 못쓰는 아이디라는 것
		if (service.withdrawIdCheck(map) == 1) {
			return 1;
		} else {
			return 0;
		}
	}
	


	
}
