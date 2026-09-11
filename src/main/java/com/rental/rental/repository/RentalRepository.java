package main.java.com.rental.rental.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.rental.dto.ActionRequest;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.dto.RentalResponse;


public interface RentalRepository {

	/*
	 * 물품 조회하기
	 */
	List<RentalResponse> findBorrowedItems(ActionRequest request) throws SQLException;

	/*
	 * 대여/반납 요청
	 */
	int requestRental(RentalCreateRequest request) throws SQLException;


	// 대여 신청 현황 조회 100
	// 현재 대여 현황 조회 101
	// 과거 대여 이력 조회(반납완료 물품) 21
	// 대여 요청 내역 조회
	//서비스 - 컨트롤러
	
	/**
	 * 대여/반납요청 status%100 ==0 , -> 요청한것들
	 * 
	 * a = status/100 
	 * a==1 -> 대여 
	 * a==2 -> 반납
	 */
	/**
	 * 대여 요청 취소
	 */
	int deleteRental(int rentalNum)throws RentalException;

	/**
	 * 대여 요청 승인
	 * 해당 postNum의 다른 대여 요청은 모두 거절
	 * 
	 */
	boolean approveRental(Connection con, int rentalNum)throws RentalException;
//	같은 게시글의 다른 대여 요청 거절
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
