package main.java.com.rental.rental.repository;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.rental.dto.BorrowerActionRequest;
import main.java.com.rental.rental.dto.BorrowerRentalResponse;
import main.java.com.rental.rental.dto.LenderRentalResponse;
import main.java.com.rental.rental.dto.LenderActionRequest;

public interface RentalRepository {

	/*
	 * 임차인 입장의 물품 조회하기 
	 */
	List<BorrowerRentalResponse> findBorrowedItems(BorrowerActionRequest request) throws SQLException;

	
	
	/*
	 * 대여자 입장의 물품 조회하기
	 */
	List<LenderRentalResponse> findLentItems(LenderActionRequest request) throws SQLException;

	/*
	 * 대여 신청하기 
	 */
	int requestRental(BorrowerActionRequest request) throws SQLException;
	
	
	/*
	 * 반납 신청하기 
	 */
	int requestReturn(LenderActionRequest request) throws SQLException;
	
	

}
