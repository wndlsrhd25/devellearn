package com.yedam.devellearn.file;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FileController {
	

	/******* 배포용 경로 ********/
	 private String filePath = "/home/ubuntu/devellearn/";
	 private String imgPath = "/home/ubuntu/devellearn/img/";
	 private String videoPath = "/home/ubuntu/devellearn/lectureVideo/";

	/******* 개발용 경로 ********/
//	private String filePath = "C:/devellearn/";
//	private String imgPath = "C:/devellearn/img/";
//	private String videoPath = "C:/devellearn/lectureVideo/";
	

	// 사진 넘겨주는 아이
	@GetMapping("/images/{folder}/{filename}")
	public Resource showImage(@PathVariable String folder, @PathVariable String filename) throws MalformedURLException {
		System.out.println(imgPath + folder + "/" + filename);
		return new UrlResource("file:" + imgPath + folder + "/" + filename);
	}

	// 영상 넘겨주는 아이
	@GetMapping("/video/{folder}/{filename}")
	public Resource showVideo(@PathVariable String folder, @PathVariable String filename) throws MalformedURLException {
		System.out.println("@@@@@@@@@@@@@@@@@videoPath = " + videoPath + folder + "/" + filename);
		return new UrlResource("file:" + videoPath + folder + "/" + filename);
	}

	// 다운로드 링크포함해서 파일 넘겨주는 매서드
	@GetMapping("/files/{folder}/{filename}")
	public <UploadFile> ResponseEntity<Resource> attachFileDownload(@PathVariable String folder,
			@PathVariable String filename, HttpServletResponse response) throws MalformedURLException {

		UrlResource urlResource = new UrlResource("file:" + filePath + folder + "/" + filename);
		String encode = UriUtils.encode(filename, StandardCharsets.UTF_8);
		String contentDispostion = "attachment; filename=\"" + encode + "\"";
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDispostion).body(urlResource);
	}
}
