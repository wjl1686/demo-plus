package com.example.demo.common.exception;


import com.example.demo.common.enums.ErrorMsg;

public class RestException extends Exception {

	private static final long serialVersionUID = -2863750853917028651L;

	private int state;

	private String msg;

	private Exception exception;

	public RestException(ErrorMsg errorMsg) {
		this.state = errorMsg.state;
		this.msg = errorMsg.msg;
	}

	public RestException(ErrorMsg errorMsg, String... args) {
		this.state = errorMsg.state;
		this.msg = String.format(errorMsg.msg, args);
	}
	
	public RestException(ErrorMsg errorMsg, Exception e) {
		this.state = errorMsg.state;
		this.msg = errorMsg.msg;
		this.exception = e;
	}
	
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

}
