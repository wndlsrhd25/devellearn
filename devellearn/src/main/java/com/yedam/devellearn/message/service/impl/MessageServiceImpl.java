package com.yedam.devellearn.message.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.devellearn.message.mapper.MessageMapper;
import com.yedam.devellearn.message.service.MessageService;
import com.yedam.devellearn.message.service.MessageVO;

@Service
public class MessageServiceImpl implements MessageService{

	@Autowired
	MessageMapper mapper;
	
	@Override
	public int getNum() {
		return 0;
	}

	@Override
	public int insertMessage(MessageVO messageVO) {
		return mapper.insertMessage(messageVO);
	}

	//수신함
	@Override
	public List<MessageVO> recvMes(String recvId) {
		return mapper.recvMes(recvId);
	}
	//발신함
	@Override
	public List<MessageVO> sendMes(String sendId) {
		return mapper.sendMes(sendId);
	}
	@Override
	public int deleteMessage(int mesNo) {
		return mapper.deleteMessage(mesNo);
	}

	@Override
	public int updateMessage(int mesNo) {
		return mapper.updateMessage(mesNo);
	}

	@Override
	public MessageVO mesDetails(int mesNo) {
		return mapper.mesDetails(mesNo);
	}

	



}
