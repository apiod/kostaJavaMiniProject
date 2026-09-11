package main.java.com.rental.common.exception;

public class NotFoundException extends RuntimeException {
	public NotFoundException() {}
	public NotFoundException(String message) {
		super(message);
	}
}
