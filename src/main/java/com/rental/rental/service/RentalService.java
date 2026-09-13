package main.java.com.rental.rental.service;

import java.util.List;

import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.entity.Rental;

public interface RentalService {
	/**
	 * rental 생성
	 */
	public void rentalCreate(RentalCreateRequest rentalCreateRequest) throws RentalException;

	/**
	 * 물품 조회
	 *
	 * 내가 빌려주는 물품 내가 빌린 물품
	 */
	public List<Rental> selectLendList(String lenderId) throws RentalException, NotFoundException;

	public List<Rental> selectBorrowList(String borrowerId) throws RentalException, NotFoundException;

	/**
	 * 대여 신청 현황 조회 status == 100
	 */
	public List<Rental> selectRentalRequestList(String userId) throws RentalException, NotFoundException;

	/**
	 * 현재 대여 현황 조회 status == 110
	 */
	public List<Rental> selectCurrentRentalList(String userId) throws RentalException, NotFoundException;

	/**
	 * 과거 대여 이력 조회 status == 210 또는 211
	 */
	public List<Rental> selectRentalHistoryList(String userId) throws RentalException, NotFoundException;

	/**
	 * 대여/반납 요청 내역 조회
	 *
	 * status % 100 == 0
	 *
	 * 100 ~ 199 : 대여 요청 200 ~ 299 : 반납 요청
	 */
	public List<Rental> selectRequestList(String userId) throws RentalException, NotFoundException;

	public List<Rental> selectRentalRequestHistory(String userId) throws RentalException, NotFoundException;

	public List<Rental> selectReturnRequestHistory(String userId) throws RentalException, NotFoundException;

	public boolean approveRental(int rentalNum, int postNum) throws RentalException;

	public void rejectRental(int rentalNum) throws RentalException;

	public void confirmReturn(int rentalNum) throws RentalException;
}
