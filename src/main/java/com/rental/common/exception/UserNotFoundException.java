package main.java.com.rental.common.exception;

public class UserNotFoundException extends RuntimeException {
	public UserNotFoundException() {}
	public UserNotFoundException(String message) {
		super(message);
	}
}
