package com.yedam.devellearn.lecture.web;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yedam.devellearn.lecture.service.MyCurriculumService;
import com.yedam.devellearn.lecture.service.MyCurriculumVO;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class MyCurriculumController {

	@Autowired
	MyCurriculumService service;

	// 등록
	// Vue에서 배열로 전달 -> <string, list> 형태의 map 으로 전달 및 받을 수 있음
	@PostMapping("/insertMyCurriculum")
	public void insertMyCurriculum(@RequestBody List<MyCurriculumVO> list) {	
		service.insertMyCurriculum(list);
	}

	// 전체조회 - 강의번호로 조회
	@PostMapping("/getMyCurriculumList")
	public List<MyCurriculumVO> getMyCurriculmList(@RequestBody MyCurriculumVO myCurriculumVO) {
		System.out.println("나의 커리큘럼 리스트 호출됨@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		List<MyCurriculumVO> list = service.getMyCurriculumListByLectureNo(myCurriculumVO);
		for (MyCurriculumVO myCurriculumVO2 : list) {
			System.out.println("나의 커리큘럼 리스트 호출됨@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ myCurriculumVO2 = " + myCurriculumVO2);
			
		}
		
		return list;
	}
	
	// 단건조회 - 커리큘럼번호로 조회
	@PostMapping("/getMyCurriculum")
	public MyCurriculumVO getMyCurriculm(@RequestBody MyCurriculumVO myCurriculumVO) {
		return service.getMyCurriculum(myCurriculumVO);
	}
	
	// 수정 - 수강여부(watchedAll, latestWatchedTime 수정)
	@PutMapping("/updateMyCurriculum")
	public int updateMyCurriculum(@RequestBody MyCurriculumVO myCurriculumVO) {
		return service.updateMyCurriculum(myCurriculumVO);
	}
	
	
}
