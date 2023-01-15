package com.yedam.devellearn.message.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MessageVO {

	private int mesNo;
	private String sendId;
	private String recvId;
	private String content;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sentDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date ReadDate;
	private String readUnread;
	
}
