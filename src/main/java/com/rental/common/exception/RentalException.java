package main.java.com.rental.common.exception;

public class RentalException extends Exception {
	public RentalException() {
		super("db문제로 관리자에게 문의해주세요.");
	}
	public RentalException(String message) {
		super(message);
	}
	
}
