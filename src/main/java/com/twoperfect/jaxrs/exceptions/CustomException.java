package com.twoperfect.jaxrs.exceptions;

public class CustomException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public CustomException() {
		super();
	}
	
	public CustomException(String message) {
		super(message);
	}
	
	public CustomException(String message, Exception e) {
		super(message, e);
	}
}
