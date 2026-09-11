package main.java.com.rental.rental.service;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.rental.dto.ActionRequest;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.dto.RentalResponse;

public interface RentalService {

	 /*
     * 물품 조회  하기 
     */
	List<RentalResponse> findBorrowedItems(ActionRequest request) throws SQLException;

	/*
	 * 대여/반납 요청 
	 */
	int requestRental(RentalCreateRequest request) throws SQLException;
	
}
