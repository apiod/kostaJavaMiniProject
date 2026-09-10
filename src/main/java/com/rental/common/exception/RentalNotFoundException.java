package main.java.com.rental.common.exception;

public class RentalNotFoundException {
	public class NotFoundException extends Exception {
		public NotFoundException() {
		}

		public NotFoundException(String message) {
			super(message);
		}
	}
}
