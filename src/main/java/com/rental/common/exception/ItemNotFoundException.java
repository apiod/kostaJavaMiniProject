package com.rental.common.exception;

public class ItemNotFoundException extends RuntimeException {
	public ItemNotFoundException(String message) {
		super(message);
	}
}
