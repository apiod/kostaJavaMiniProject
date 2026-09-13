package main.java.com.rental.rental.controller;

import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.service.RentalService;
import main.java.com.rental.rental.service.RentalServiceImpl;
import main.java.com.rental.view.FailView;
import main.java.com.rental.view.SuccessView;

public class RentalController {
	RentalService rs = RentalServiceImpl.getInstance();

	public void rentalCreate(RentalCreateRequest rentalCreateRequest) {
		try {
			rs.rentalCreate(rentalCreateRequest);
			SuccessView.printMessage("생성되었습니다.");
		} catch (RentalException e) {
			FailView.FailMessage(e.getMessage());
		}

	}

	public void selectLendList(String lenderId) {
		try {
			rs.selectLendList(lenderId);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	public void selectBorrowList(String borrowerId) {
		try {
			rs.selectBorrowList(borrowerId);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	/**
	 * 대여 신청 현황 조회 status == 100
	 */
	public void selectRentalRequestList(String userId) {
		try {
			rs.selectRentalRequestList(userId);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	/**
	 * 현재 대여 현황 조회 status == 110
	 */
	public void selectCurrentRentalList(String userId) {
		try {
			rs.selectCurrentRentalList(userId);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	/**
	 * 과거 대여 이력 조회 status == 210 또는 211
	 */
	public void selectRentalHistoryList(String userId) {
		try {
			rs.selectRentalHistoryList(userId);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	/**
	 * 대여/반납 요청 내역 조회
	 *
	 * status % 100 == 0
	 *
	 * 100 ~ 199 : 대여 요청 200 ~ 299 : 반납 요청
	 */
	public void selectRequestList(String userId) {
		try {
			rs.selectRequestList(userId);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	public void selectRentalRequestHistory(String userId) {
		try {
			rs.selectRentalRequestHistory(userId);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	public void selectReturnRequestHistory(String userId) {
		try {
			rs.selectReturnRequestHistory(userId);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

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
