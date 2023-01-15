package com.yedam.devellearn.login.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.devellearn.blog.service.MyBlogVO;
import com.yedam.devellearn.login.mapper.LoginMapper;
import com.yedam.devellearn.login.service.ComMemberVO;
import com.yedam.devellearn.login.service.LoginService;
import com.yedam.devellearn.login.service.MemberVO;

@Service
public class LoginServiceImpl implements LoginService  {

	@Autowired
	LoginMapper mapper;

	//회원가입
	@Override
	public int insertMember(MemberVO memberVO) {
		MyBlogVO vo = new MyBlogVO();
		vo.setMemberId(memberVO.getMemberId());
		mapper.insertFolder(vo);
		mapper.insertMyblog(vo);
		mapper.insertBookFolder(vo);
		return mapper.insertMember(memberVO);
	}

	//로그인
	@Override
	public MemberVO loginCheck(MemberVO memberVO) {
		return mapper.loginCheck(memberVO);
	}

	//아이디 중복체크
	@Override
	public int idCheck(Map map) {
		int cnt = mapper.idCheck(map);
		System.out.println("cnt:"+cnt);

		return cnt;
	}


	//기업회원 로그인
	@Override
	public ComMemberVO companyLogin(ComMemberVO comMemberVO) {
		return mapper.companyLogin(comMemberVO);
	}

	//기업회원 아이디 중복체크
	@Override
	public int comIdCheck(Map map) {
		return mapper.comIdCheck(map);
	}

	//기업회원 회원가입
	@Override
	public int insertComMember(ComMemberVO comMemberVO) {
		return mapper.insertComMember(comMemberVO);
	}

	//회원 단건조회
	@Override
	public MemberVO selectMember(String memberId) {
		return mapper.selectMember(memberId);
	}


	@Override
	public int updateInfo(MemberVO memberVO) {
		return mapper.updateInfo(memberVO);
	}

	@Override
	public int withdrawal(String memberId) {
		return mapper.withdrawal(memberId);
	}

	@Override
	public int updateComInfo(ComMemberVO comMemberVO) {
		return mapper.updateComInfo(comMemberVO);
	}

	@Override
	public ComMemberVO selectComMember(String comId) {
		return mapper.selectComMember(comId);
	}

	@Override
	public int changePw(MemberVO memberVO) {
		return mapper.changePw(memberVO);
	}

	@Override
	public int changeComPw(ComMemberVO comMemberVO) {
		return mapper.changeComPw(comMemberVO);
	}

	// 이메일 체크
	@Override
	public int emailCheck(Map map) {
		
		int cnt = mapper.emailCheck(map);
		System.out.println("cnt:"+cnt);

		return cnt;
	}

	//13. 아이디 찾기 (이름, 전화번호로 아이디찾기)
	@Override
	public List<MemberVO> findId(String name, String phone) {
		return mapper.findId(name, phone);
	}
	

	//14. 기업회원 아이디찾기 

	@Override
	public ComMemberVO findComId(String name, String phone) {
		return mapper.findComId(name, phone);
	}

	// 15.탈퇴회원 아이디 조회
	@Override
	public int withdrawIdCheck(Map map) {
		int cnt = mapper.withdrawIdCheck(map);	
		System.out.println("cnt:"+cnt);

		return cnt;
	}




	
}
