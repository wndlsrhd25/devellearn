package com.yedam.devellearn.file;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author lgh
 * @apiNote 파일 업로드, 삭제 관련 클래스
 */
public class FileHandler {

	/******* 배포용 경로 ********/
	private String filePath = "/home/ubuntu/devellearn/";

	/******* 개발용 경로 ********/
//	private String filePath = "C:/devellearn/";

	/**
	 * @param folder 폴더이름 ex) "img/company"
	 * @param name   파일분류+기본키값 ex) "recruit+vo.getNo()"
	 * @param files  MultipartFile 정보
	 * @return fileName DB에 저장될 파일이름
	 */
	public String uploadFile(String folder, String name, MultipartFile files)
			throws IllegalStateException, IOException {
		if (!files.isEmpty()) {
			// 파일 이름 설정
			String millis = String.valueOf(System.currentTimeMillis());
			int idx = files.getOriginalFilename().lastIndexOf(".");
			String extension = "";
			if (idx > 0) {
				extension = files.getOriginalFilename().substring(idx);
			} else {
				extension = ".pdf";
			}
			String fileName = name + "_" + millis + "" + extension;
			String path = filePath + folder;
			File file = new File(path, fileName);
			file.mkdirs();
			files.transferTo(file);
			return fileName;
		}
		return null;
	}

	/**
	 * @param path 폴더 ex) "img/company"
	 * @param name DB에 저장된 파일이름
	 * @return 삭제성공 1, 삭제실패 0
	 */
	public int deleteFile(String folder, String name) {

		String path = filePath + folder + "/" + name;

		System.out.println("삭제할 파일" + path);
		File deleteFile = new File(path);

		// 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
		if (deleteFile.exists()) {
			// 파일을 삭제합니다.
			deleteFile.delete();
			System.out.println("파일을 삭제하였습니다.");
			return 1;
		} else {
			System.out.println("파일이 존재하지 않습니다.");
			return 0;
		}
	}

}
