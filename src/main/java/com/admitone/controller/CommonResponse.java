package com.admitone.controller;

import java.io.Serializable;

public class CommonResponse implements Serializable{
	private String message;
	
	public CommonResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

