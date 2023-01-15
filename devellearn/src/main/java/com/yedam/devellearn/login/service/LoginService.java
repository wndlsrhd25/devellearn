package com.yedam.devellearn.login.service;

import java.util.List;
import java.util.Map;

public interface LoginService {

	// 1.회원가입
	public int insertMember(MemberVO memverVO);

	// 2.로그인
	public MemberVO loginCheck(MemberVO memberVO);

	// 3.아이디 중복체크
	public int idCheck(Map map);

	// 4.기업회원 로그인
	public ComMemberVO companyLogin(ComMemberVO comMemberVO);

	// 5.기업회원 아이디 중복체크
	public int comIdCheck(Map map);

	// 6.기업회원 회원가입
	public int insertComMember(ComMemberVO comMemberVO);

	// 7.회원 단건조회
	public MemberVO selectMember(String memberId);

	// 9.개인정보 수정
	public int updateInfo(MemberVO memberVO);

	// 10.회원탈퇴
	public int withdrawal(String memberId);

	// 11.기업회원 개인정보 수정
	public int updateComInfo(ComMemberVO comMemberVO);

	// 12. 기업회원 단건조회
	public ComMemberVO selectComMember(String comId);

	// 8.이메일 중복체크
	public int emailCheck(Map map);
	

	// 12. 개인회원 비밀번호 변경
	public int changePw(MemberVO memberVO);

	// 12. 기업회원 비밀번호 변경
	public int changeComPw(ComMemberVO comMemberVO);
	
	//13. 아이디 찾기 (이름, 전화번호로 아이디찾기)	
	public List<MemberVO> findId (String name, String phone);
	
	//14. 기업회원 아이디 찾기 (이름, 전화번호로 아이디찾기)	
	public ComMemberVO findComId(String name, String phone);
	
	//15. 탈퇴회원 체크
	public int withdrawIdCheck(Map map);

}
