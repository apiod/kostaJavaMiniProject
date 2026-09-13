package main.java.com.rental.rental.repository;

import java.sql.Connection;
import java.util.List;

import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.entity.Rental;

public interface RentalRepository {

	/**
	 * rental 생성
	 */
	public int rentalCreate(RentalCreateRequest rentalCreateRequest) throws RentalException;

	/**
	 * 물품 조회
	 *
	 * 내가 빌려주는 물품 내가 빌린 물품
	 */
	public List<Rental> selectLendList(String lenderId) throws RentalException;

	public List<Rental> selectBorrowList(String borrowerId) throws RentalException;

	/**
	 * 대여 신청 현황 조회 status == 100
	 */
	public List<Rental> selectRentalRequestList(String userId) throws RentalException;

	/**
	 * 현재 대여 현황 조회 status == 110
	 */
	public List<Rental> selectCurrentRentalList(String userId) throws RentalException;

	/**
	 * 과거 대여 이력 조회 status == 210 또는 211
	 */
	public List<Rental> selectRentalHistoryList(String userId) throws RentalException;

	/**
	 * 대여/반납 요청 내역 조회
	 *
	 * status % 100 == 0
	 *
	 * 100 ~ 199 : 대여 요청 200 ~ 299 : 반납 요청
	 */
	public List<Rental> selectRequestList(String userId) throws RentalException;

	public List<Rental> selectRentalRequestHistory(String userId) throws RentalException;

	public List<Rental> selectReturnRequestHistory(String userId) throws RentalException;

	/**
	 * 대여 요청 취소
	 */
	int deleteRental(int rentalNum) throws RentalException;

	/**
	 * 대여 요청 승인 해당 postNum의 다른 대여 요청은 모두 거절
	 * 
	 */
	boolean approveRental(Connection con, int rentalNum) throws RentalException;

//	같은 게시글의 다른 대여 요청 거절
	boolean rejectOtherRentals(Connection con, int postNum, int rentalNum) throws RentalException;

	/**
	 * 대여 요청 거절
	 */
	int rejectRental(int rentalNum) throws RentalException;

	/**
	 * 반납 완료 확인
	 */
	int confirmReturn(int rentalNum) throws RentalException;
}
