package main.java.com.rental.rental.repository;

import java.sql.Connection;

import main.java.com.rental.common.exception.RentalException;

public interface Rental {
	/**
	 * 대여 요청 취소
	 */
	int deleteRental(int rentalNum)throws RentalException;

	/**
	 * 대여 요청 승인
	 * 해당 postNum의 다른 대여 요청은 모두 거절
	 * NotFoundException
	 */
	boolean approveRental(Connection con, int rentalNum)throws RentalException;
//	 같은 게시글의 다른 대여 요청 거절
    boolean rejectOtherRentals(Connection con, int postNum, int rentalNum)throws RentalException;
	
    /**
	 * 대여 요청 거절
	 */
    int rejectRental(int rentalNum)throws RentalException;

	/**
	 * 반납 완료 확인
	 */
	int confirmReturn(int rentalNum)throws RentalException;
}
