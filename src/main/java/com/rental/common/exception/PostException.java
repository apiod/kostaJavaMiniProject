package main.java.com.rental.common.exception;

public class PostException extends Exception{
	public PostException() {
		super("post문제 발생 관리자에게 연락해주세요.");
	}
	public PostException(String message) {
		super(message);
	}
}
