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
	 * 대여 신청 현황 조회 status == 100
	 */
	public List<Rental> selectRentalRequestList() throws RentalException;

	/**
	 * 현재 대여 현황 조회 status == 110
	 */
	public List<Rental> selectCurrentRentalList() throws RentalException;

	/**
	 * 대여 요청 승인 해당 postNum의 다른 대여 요청은 모두 거절
	 * 
	 */
	boolean approveRental(Connection con, int rentalNum) throws RentalException;

//	같은 게시글의 다른 대여 요청 거절
	boolean rejectOtherRentals(Connection con, int postNum, int rentalNum) throws RentalException;

	/**
	 * 로그인시 status값이 100, 200인 대여 목록 리스트 출력 로그인시 최초 1회
	 */
	public List<Rental> getPendingApprovals() throws RentalException;

	public Rental selectByRentalNum(int rentalNum) throws RentalException;

	public List<Rental> selectByStatus(int status) throws RentalException;

	public int updateStatusRentalNum(int setStatus, int rentalNum, int status) throws RentalException;
}
