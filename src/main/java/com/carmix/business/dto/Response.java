package com.carmix.business.dto;

public class Response<T> {
	
	private String code;
	
	private T response;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getResponse() {
		return response;
	}

	public void setResponse(T response) {
		this.response = response;
	}
	
	

}
