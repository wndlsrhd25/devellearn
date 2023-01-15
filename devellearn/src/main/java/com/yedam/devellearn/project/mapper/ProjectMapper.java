package com.yedam.devellearn.project.mapper;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.Page;
import com.yedam.devellearn.blog.service.MyBlogVO;
import com.yedam.devellearn.project.service.ProjectConsVO;
import com.yedam.devellearn.project.service.ProjectRepVO;
import com.yedam.devellearn.project.service.ProjectVO;

public interface ProjectMapper {
	
	//3.아이디 중복체크
	public int projectidCheck(Map map);

	// 전체조회
	public Page<ProjectVO> getProjectListAll(Map<String,Object> map);
	// 내글 전체조회
	public Page<ProjectVO> getMyProjectListAll(Map<String,Object> map);
	// 플젝승인 전체조회
	public Page<ProjectConsVO> getPmemberList(Map<String,Object> map);
	// 참가한 플젝 전체조회
	public Page<ProjectConsVO> getProjList(Map<String,Object> map);
	
	// 검색
	public Page<ProjectVO> getProjectFilter(Map<String,Object> map);
	// 내글 검색
	public Page<ProjectVO> getMyProjectFilter(Map<String,Object> map);
	// 플젝멤버 검색
	public Page<ProjectConsVO> getMyPmemberFilter(Map<String,Object> map);
	
	//단건
	public ProjectVO getProjectDetail(int projectId);
	
	//수정
	public int updateProject(ProjectVO vo);
	//멘티수정
	public int updatePmember(ProjectConsVO vo);
	//모집수정
	public int updateOpen(ProjectVO vo);
	
	//삭제
	public int deleteProject(int projectId);
	//플젝거절
	public int deleteProjectStatus(int projectconsNo);
	
	//등록
	public int insertProject(ProjectVO vo);
	
	//댓글조회
	public List<ProjectRepVO> getProjectRepListAll(int projectId);
	
	//댓글등록
	public int insertProjectRep(ProjectRepVO vo);
	
	//댓글삭제
	public int deleteProjectRep(int projectrepNo);
	
	//댓글수정
	public int updateProjectRep(ProjectRepVO vo);
	
	//프로젝트신청
	public int enterProject(ProjectConsVO vo);
	
	//프로젝트블로그
	public int enterBlog(MyBlogVO vo);
	
	//////////////////////////////////////////////////////////////
	//승인 완료시 my_blog에 insert되게 하기(nhj)
	public int insertMyblogId(ProjectConsVO vo);
	
	//블로그 폴더 삽입(nhj)
	public int insertFolder(ProjectVO vo);

}
