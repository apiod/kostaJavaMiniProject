package main.java.com.rental.common.exception;

public class UserException  extends RuntimeException {
	public UserException() {
		super("db문제로 관리자에게 문의해주세요.");
	}
	public UserException(String message) {
		super(message);
	}
}
