package com.yedam.devellearn.project.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.yedam.devellearn.blog.service.MyBlogVO;
import com.yedam.devellearn.project.mapper.ProjectMapper;
import com.yedam.devellearn.project.service.ProjectConsVO;
import com.yedam.devellearn.project.service.ProjectRepVO;
import com.yedam.devellearn.project.service.ProjectService;
import com.yedam.devellearn.project.service.ProjectVO;

@Service
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	ProjectMapper mapper;
	
	//아이디 중복체크
	@Override
	public int projectidCheck(Map map) {
		int cnt = mapper.projectidCheck(map);
		System.out.println("cnt:"+cnt);

		return cnt;
	}
	
	//전체
	@Override
	public Page<ProjectVO> getProjectListAll(Map<String,Object> map){
		return mapper.getProjectListAll(map);
	}
	//내글 전체
	@Override
	public Page<ProjectVO> getMyProjectListAll(Map<String,Object> map){
		return mapper.getMyProjectListAll(map);
	}
	//멤버승인 전체
	@Override
	public Page<ProjectConsVO> getPmemberList(Map<String,Object> map){
		return mapper.getPmemberList(map);
	}
	//참가한 플젝 전체
	@Override
	public Page<ProjectConsVO> getProjList(Map<String,Object> map){
		return mapper.getProjList(map);
	}
	
	// 검색
	public Page<ProjectVO> getProjectFilter(Map<String,Object> map){
		return mapper.getProjectFilter(map);
	}
	// 내글 검색
	public Page<ProjectVO> getMyProjectFilter(Map<String,Object> map){
		return mapper.getMyProjectFilter(map);
	}
	// 플젝멤버 검색
	public Page<ProjectConsVO> getMyPmemberFilter(Map<String,Object> map){
		return mapper.getMyPmemberFilter(map);
	}
	
	//멘티수정 -> 프젝 상태 수정하는거래요
	@Override
	public ProjectConsVO updatePmember(ProjectConsVO vo) {
		mapper.insertMyblogId(vo);
		mapper.updatePmember(vo);
		return vo;
	}
	
	//단건
	@Override
	public ProjectVO getProjectDetail(int projectId) {
		return mapper.getProjectDetail(projectId);
	}
	
	//수정
	@Override
	public int updateProject(ProjectVO vo) {
		return mapper.updateProject(vo);
	}
	//모집상태수정
	@Override
	public int updateOpen(ProjectVO vo) {
		return mapper.updateOpen(vo);
	}
	
	//삭제
	@Override
	public int deleteProject(int projectId) {
		return mapper.deleteProject(projectId);
	}
	//플젝거절
	@Override
	public int deleteProjectStatus(int projectconsNo) {
		return mapper.deleteProjectStatus(projectconsNo);
	}
	
	//등록
	@Override
	public ProjectVO insertProject(ProjectVO vo) {
		 mapper.insertProject(vo);
		 mapper.insertFolder(vo);
		 return vo;
	
	}
	
	//댓글조회
	@Override
	public List<ProjectRepVO> getProjectRepListAll(int projectId){
		return mapper.getProjectRepListAll(projectId);
	}

	//댓글등록
	@Override
	public int insertProjectRep(ProjectRepVO vo) {
		return mapper.insertProjectRep(vo);
	}
	
	//댓글삭제
	@Override
	public int deleteProjectRep(int projectrepNo) {
		return mapper.deleteProjectRep(projectrepNo);
	}
	
	//댓글수정
	@Override
	public int updateProjectRep(ProjectRepVO vo) {
		return mapper.updateProjectRep(vo);
	}
	
	//프로젝트신청
	@Override
	public int enterProject(ProjectConsVO vo) {
		return mapper.enterProject(vo);
	}
	
	//블로그신청
	@Override
	public int enterBlog(MyBlogVO vo) {
		return mapper.enterBlog(vo);
	}

	//프로젝트 만들때 기본 폴더 삽입되게 하기
//	@Override
//	public int insertMyblogId(ProjectConsVO vo) {
//		return mapper.insertFolder(null);
//	}

	
	
	
}
