	package com.yedam.devellearn.project.web;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yedam.devellearn.blog.service.MyBlogVO;
import com.yedam.devellearn.file.FileHandler;
import com.yedam.devellearn.project.service.ProjectConsVO;
import com.yedam.devellearn.project.service.ProjectRepVO;
import com.yedam.devellearn.project.service.ProjectService;
import com.yedam.devellearn.project.service.ProjectVO;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
public class ProjectController {

	@Autowired
	ProjectService service;
	//등록
	@PostMapping("/projecti")
	public ProjectVO insertProject(MultipartFile image, ProjectVO vo) throws IllegalStateException, IOException {
		FileHandler filehandler = new FileHandler();
		String fileName = filehandler.uploadFile("img/teamstudy", "project" + vo.getProjectNo(), image);
		vo.setImg(fileName);
		return service.insertProject(vo);
	}
	//수정
	@PutMapping("/project")
	public int updateProject(MultipartFile image, ProjectVO vo) throws IllegalStateException, IOException {
		// 기존파일삭제
		FileHandler filehandler = new FileHandler();
		filehandler.deleteFile("img/teamstudy", vo.getImg());
		// 새로운 파일 업로드
		String fileName = filehandler.uploadFile("img/teamstudy", "project" + vo.getProjectNo(), image);
		vo.setImg(fileName);
		return service.updateProject(vo);
	}
	
	/* 아이디 중복체크 */
	@PostMapping("/projectidCheck")
	public int projectidCheck(@RequestBody Map map) {
		
		//1이나오면 사용중인 아이디가 있다는 것, 1이 못쓴다는 것
		if (service.projectidCheck(map) == 1) {
			return 1;
		} else {
			return 0;
		}
	}
	
	// 전체조회
	@PostMapping("/project")
	public PageInfo<ProjectVO> getProjectListAll(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getProjectListAll(map));
	}
	// 내글 전체조회
	@PostMapping("/myproject")
	public PageInfo<ProjectVO> getMyProjectListAll(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMyProjectListAll(map));
	}
	// 플젝멤버 
	@PostMapping("/mypmember")
	public PageInfo<ProjectConsVO> getPmemberList(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getPmemberList(map));
	}
	// 참가플젝리스트 
	@PostMapping("/myproj")
	public PageInfo<ProjectConsVO> getProjList(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getProjList(map));
	}
	
	//검색
	@PostMapping("/searchProject")
	public PageInfo<ProjectVO> getProjectFilter(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int)map.get("pageNum"), 8);
		return PageInfo.of(service.getProjectFilter(map));
	}
	//내글 검색
	@PostMapping("/searchMyProject")
	public PageInfo<ProjectVO> getMyProjectFilter(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMyProjectFilter(map));
	}
	//플젝멤버 검색
	@PostMapping("/searchMyPmember")
	public PageInfo<ProjectConsVO> getMyPmemberFilter(@RequestBody Map<String,Object> map) {
		PageHelper.startPage((int) map.get("pageNum"), 8);
		return PageInfo.of(service.getMyPmemberFilter(map));
	}
	
	// 단건조회
	@GetMapping("/project/{projectId}")
	public ProjectVO getProjectDetail(@PathVariable int projectId) {
		return service.getProjectDetail(projectId);
	}
	
	
	
	//프로젝트상태수정
	@PutMapping("/projectStatus")
	public ProjectConsVO updatePmember(@RequestBody ProjectConsVO vo) {
		return service.updatePmember(vo);
	}
	//모집상태수정
	@PutMapping("/projectOpen")
	public int updateOpen(@RequestBody ProjectVO vo) {
		return service.updateOpen(vo);
	}
	
	//삭제
	@DeleteMapping("/project/{projectId}")
	public int deleteProject(@PathVariable int projectId) {
		return service.deleteProject(projectId);
	}
	//플젝거절
	@DeleteMapping("/projectStatusDelate/{projectconsNo}")
	public int deleteProjectStatus(@PathVariable int projectconsNo) {
		return service.deleteProjectStatus(projectconsNo);
	}

	
	//댓글조회
	@GetMapping("/projectRep/{projectId}")
	public List<ProjectRepVO> getProjectRepListAll(@PathVariable int projectId) {
		return service.getProjectRepListAll(projectId);
	}

	//댓글등록
	@PostMapping("/projectRep")
	public int insertProjectRep(@RequestBody ProjectRepVO vo) {
		return service.insertProjectRep(vo);
	}
	
	//댓글삭제
	@DeleteMapping("/projectRep/{projectrepNo}")
	public int deleteProjectRep(@PathVariable int projectrepNo) {
		return service.deleteProjectRep(projectrepNo);
	}
	
	//댓글수정
	@PutMapping("/projectRep")
	public int updateProjectRep(@RequestBody ProjectRepVO vo) {
		return service.updateProjectRep(vo);
	}
	
	//프로젝트신청
	@PostMapping("/projectenter")
	public int enterMentor(@RequestBody ProjectConsVO vo) {
		return service.enterProject(vo);
	}
	//블로그insert
	@PostMapping("/blogenter")
	public int enterBlog(@RequestBody MyBlogVO vo) {
		return service.enterBlog(vo);
	}
}
