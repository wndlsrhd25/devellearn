package com.yedam.devellearn.message.service;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface MessageService {

	//1. 쪽지 게시글 번호 조회
		public int getNum();

		//2. 쪽지 보내기(쪽지 등록)
		public int insertMessage (MessageVO messageVO);
		
		//3. (수신) 쪽지 전체 조회 
		public List<MessageVO> recvMes(String recvId);

		//3. (발신) 쪽지 전체 조회
		public List<MessageVO> sendMes(String sendId);
		
		//4. 쪽지글 삭제
		public int deleteMessage(int mesNo);
		
		//5. 쪽지 상태 업데이트
		public int updateMessage(int mesNo);
		
		//6. 수신 쪽지 내용 보기(단건조회)
		public MessageVO mesDetails(int mesNo);

		
}
