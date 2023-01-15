package com.yedam.devellearn.quill_editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class QuillEditor {

	// 배포용
	private String path = "/home/ubuntu/devellearn/editorHTML/";

	// 개발용
//	private String path = "C:/devellearn/editorHTML/";

	// quill editor로 작성한 내용 txt 파일로 저장
	public String saveQnaContentToTxtFile(String content, String folder) {

		// fileName 지정
		String fileName = folder + "_" + String.valueOf(System.currentTimeMillis()) + ".txt";
		// filePath 지정
		String filePath = path + folder + "/" + fileName;

		System.out.println("filePath= " + filePath);

		File file = new File(filePath);

		try {
			FileOutputStream fos = new FileOutputStream(file, false);
			// content 내용 쓰기
			fos.write(content.getBytes());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// DB에 저장할 filename.txt
		return fileName;
	}

	// editor content txt 파일 읽기
	public String readContentTxtFileToStr(String fileName, String folder) {
		
		// filePath 지정
		String filePath = path + folder + "/" + fileName;

		String content = "";
		try {
			// DB에 저장된 text file 읽기
			content += Files.readString(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// HTML 형식으로 보여질 내용
		return content;
	}

	
	// editor content txt 파일 삭제
	public String deleteContentTxtFileToStr(String fileName, String folder) {

		// filePath 지정
		String filePath = path + folder + "/" + fileName;
		
		System.out.println("삭제할 파일" + filePath);
		File deleteFile = new File(filePath);

		// 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
		if (deleteFile.exists()) {

			// 파일을 삭제합니다.
			deleteFile.delete();

			System.out.println("파일을 삭제하였습니다.");

		} else {
			System.out.println("파일이 존재하지 않습니다.");
		}

		return fileName;
	}

}
