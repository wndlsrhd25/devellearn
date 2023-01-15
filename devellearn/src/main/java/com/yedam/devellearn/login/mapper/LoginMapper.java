package com.yedam.devellearn.login.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.yedam.devellearn.blog.service.MyBlogVO;
import com.yedam.devellearn.login.service.ComMemberVO;
import com.yedam.devellearn.login.service.MemberVO;


public interface LoginMapper {
	
	//1.회원가입
	public int insertMember(MemberVO memberVO);

	//2.로그인
	public MemberVO loginCheck(MemberVO memberVO);
	    
	//3.아이디 중복체크
	public int idCheck(Map map);

	//4.기업회원 로그인
	public ComMemberVO companyLogin(ComMemberVO companyMemberVO);
	
	//5.기업아이디 중복체크
	public int comIdCheck(Map map);
	
	//6.기업 회원가입
	public int insertComMember(ComMemberVO companyMemberVO);

	//블로그 폴더 삽입
	public int insertFolder(MyBlogVO vo);
	
	//my blog 삽입
	public int insertMyblog(MyBlogVO vo);
	
	//북마크 폴더 삽입
	public int insertBookFolder(MyBlogVO vo);

	//7.회원조회 (개인회원)
	public MemberVO selectMember(String memberId);
	
	//12.회원조회 (기업회원)
	public ComMemberVO selectComMember(String comId);
	
	//8. 개인회원 이메일체크
	public int emailCheck(Map map);	
	
	//9. 개인정보 수정 (업데이트_이메일, 비밀번호, 전화번호, 프로필)
	public int updateInfo (MemberVO memberVO);
	
	//10. 회원탈퇴
	public int withdrawal (String memberId);

	//11. 기업회원 개인정보 수정 (비밀번호, 담당자, 전화번호, 부서명, 직책, 이메일)
	public int updateComInfo (ComMemberVO comMemberVO);
	
	//12. 개인회원 비밀번호 변경
	public int changePw (MemberVO memberVO);
	
	//12. 기업회원 비밀번호 변경
	public int changeComPw (ComMemberVO comMemberVO);
	
	//13. 아이디 찾기 (이름, 전화번호로 아이디찾기)

	public List<MemberVO> findId(@Param("name") String name, @Param("phone") String phone);

	//14. 기업회원 아이디 찾기 (이름, 전화번호로 아이디찾기)
	//public String findId(HashMap<String, String> map);
	public ComMemberVO findComId(@Param("name") String name, @Param("phone") String phone);

	//15. 탈퇴회원 체크
	public int withdrawIdCheck(Map map);
	
	
}
