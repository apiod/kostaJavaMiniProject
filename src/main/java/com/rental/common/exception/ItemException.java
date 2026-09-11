package main.java.com.rental.common.exception;

public class ItemException extends Exception{
	public ItemException() {
		super("Item에서 문제 발생 관리자 연락부탁합니다.");
	}
	public ItemException(String message) {
		super(message);
	}
}
