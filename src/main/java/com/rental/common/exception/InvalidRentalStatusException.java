package com.rental.common.exception;

public class InvalidRentalStatusException extends RuntimeException {
	public InvalidRentalStatusException(String message) {
		super(message);
	}
}
