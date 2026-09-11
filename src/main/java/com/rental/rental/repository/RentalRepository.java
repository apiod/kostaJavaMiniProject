package main.java.com.rental.rental.repository;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.rental.dto.BorrowerActionRequest;
import main.java.com.rental.rental.dto.BorrowerRentalResponse;
import main.java.com.rental.rental.dto.LenderRentalResponse;
import main.java.com.rental.rental.dto.RentalCreateRequest;
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
	int requestRental(RentalCreateRequest request) throws SQLException;


	// 대여 신청 현황 조회 100
	// 현재 대여 현황 조회 101
	// 과거 대여 이력 조회(반납완료 물품) 21
	/**
	 * 대여/반납요청 status%100 ==0 , -> 요청한것들
	 * 
	 * a = status/100 
	 * a==1 -> 대여 
	 * a==2 -> 반납
	 */
}
