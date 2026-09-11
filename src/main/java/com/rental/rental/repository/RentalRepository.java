package main.java.com.rental.rental.repository;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.rental.dto.ReturnRequest;
import main.java.com.rental.rental.dto.ReturnResponse;
import main.java.com.rental.rental.dto.ReturnSearchRequest;

public interface RentalRepository {

	/*
	 * 대여 중인 물품 조회하기 
	 */
	List<ReturnResponse> findRentedItems(ReturnSearchRequest request) throws SQLException;

	
	/*
	 * 반납 신청하기 
	 */
	int requestReturn(ReturnRequest request) throws SQLException;

	/*
	 * 반납 신청한 물품 조회하기
	 */
	List<ReturnResponse> findRequestedReturnItems() throws SQLException;

}
