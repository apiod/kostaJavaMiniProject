package main.java.com.rental.rental.controller;

import java.util.List;

import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.entity.Rental;
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
	
	//rentalNum조회
	public Rental selectByRentalNum(int rentalNum) {
		try {
			Rental re = rs.selectByRentalNum(rentalNum);
			return re;
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
		return null;
	}
	
	//status 값 따라 출력
	public List<Rental> selectByStatus(int status){
		try {
			List<Rental> list = rs.selectByStatus(status);
			SuccessView.printEntityList(list);
			return list;
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
		return null;
	}
	
	public void confirmRental(int rentalNum) {
		try {
			rs.confirmRental(rentalNum);
			SuccessView.printMessage("대여에 성공했습니다.");
		} catch (RentalException e) {
			FailView.FailMessage(e.getMessage());
		}
	}
	
	public void selectLendList(String lenderId) {
		try {
			List<Rental> list = rs.selectLendList(lenderId);
			SuccessView.printEntityList(list);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	public void selectBorrowList(String borrowerId) {
		try {
			List<Rental> list = rs.selectBorrowList(borrowerId);
			SuccessView.printEntityList(list);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	/**
	 * 대여 신청 현황 조회 status == 100
	 */
	public void selectRentalRequestList(String userId) {
		try {
			List<Rental> list = rs.selectRentalRequestList(userId);
			SuccessView.printEntityList(list);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}
	public List<Rental> selectRentalRequestListByLender() {
		List<Rental> list = null;
		try {
			list = rs.selectRentalRequestListByLender();
			SuccessView.printEntityList(list);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
		return list;
		
	}

	/**
	 * 현재 대여 현황 조회 status == 110
	 */
	public void selectCurrentRentalList(String userId) {
		try {
			List<Rental> list = rs.selectCurrentRentalList(userId);
			SuccessView.printEntityList(list);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	/**
	 * 과거 대여 이력 조회 status == 210 또는 211
	 */
	public void selectRentalHistoryList(String userId) {
		try {
			List<Rental> list = rs.selectRentalHistoryList(userId);
			SuccessView.printEntityList(list);
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
			List<Rental> list = rs.selectRequestList(userId);
			SuccessView.printEntityList(list);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	public void selectRentalRequestHistory(String userId) {
		try {
			List<Rental> list = rs.selectRentalRequestHistory(userId);
			SuccessView.printEntityList(list);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	public void selectReturnRequestHistory(String userId) {
		try {
			List<Rental> list = rs.selectReturnRequestHistory(userId);
			SuccessView.printEntityList(list);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	public void approveRental(int rentalNum, int postNum) {
		try {
			if(rs.approveRental(rentalNum, postNum))
				SuccessView.printMessage("대여 승인에 성공했습니다.");
		} catch (Exception e) {
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
	
	//로그인 시 현재 승인 대기중인 목록 출력
	public void getPendingApprovals(){
		try {
			List<Rental> list = rs.getPendingApprovals();
			SuccessView.printPendingApprovals(list);
		} catch (NotFoundException e) {
			//아무것도 없으면 출력안함.
		}catch (RentalException e) {
			FailView.FailMessage(e.getMessage());
		}
	}
	
}
