package main.java.com.rental.common.exception;

public class RentalNotFoundException extends RuntimeException {
	public RentalNotFoundException() {}
	public RentalNotFoundException(String message) {
		super(message);
	}
}
