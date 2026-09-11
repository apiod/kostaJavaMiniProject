package main.java.com.rental.rental.controller;

import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.rental.service.RentalService1;
import main.java.com.rental.view.FailView;
import main.java.com.rental.view.SuccessView;

public class RentalC {
	RentalService1 rs = RentalService1.getInstance();
	public void approveRental(int rentalNum, int postNum) {
		try {
			rs.approveRental(rentalNum, postNum);
			SuccessView.printMessage("대여 승인에 성공했습니다.");
		} catch (RentalException e) {
			FailView.FailMessage(e.getMessage());
		}
	}
	public void rejectRental(int rentalNum) {
		try {
			rs.rejectRental(rentalNum);
			SuccessView.printMessage("대여 거절에 성공했습니다.");
		} catch (RentalException e) {
			FailView.FailMessage(e.getMessage());
		}
	}
	public void confirmReturn(int rentalNum) {
		try {
			rs.confirmReturn(rentalNum);
			SuccessView.printMessage("반납 완료에 성공했습니다.");
		} catch (RentalException e) {
			FailView.FailMessage(e.getMessage());
		}
	}
}
