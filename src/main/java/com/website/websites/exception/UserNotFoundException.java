package com.website.websites.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String mesaj) {
		super(mesaj);
	}

}
