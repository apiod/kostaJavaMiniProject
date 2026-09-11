package main.java.com.rental.rental.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.common.util.DBManager;
import main.java.com.rental.rental.dto.ActionRequest;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.dto.RentalResponse;
import main.java.com.rental.rental.repository.RentalRepository;
import main.java.com.rental.rental.repository.RentalRepositoryImpl;

public class RentalServiceImpl implements RentalService {

	private RentalRepository rr = new RentalRepositoryImpl();
	private RentalServiceImpl() {}
	public static RentalServiceImpl getInstance() {
		return new RentalServiceImpl();
	}
	/*
	 * 물품 조회 하기
	 */
	@Override
	public List<RentalResponse> findBorrowedItems(ActionRequest request) throws SQLException {

		List<RentalResponse> list = rr.findBorrowedItems(request);

		if (list.isEmpty()) {
			throw new NotFoundException("검색된 결과가 없습니다.");
		}
		return list;
	}

	/*
	 * 대여/반납 요청
	 */
	@Override
	public int requestRental(RentalCreateRequest request) throws SQLException {
		int result = rr.requestRental(request);


		return result;
	}
	
	
	
	@Override
	public boolean approveRental(int rentalNum, int postNum) throws RentalException {
	    Connection con = null;
	    try {
	        con = DBManager.getConnection();
	        // 트랜잭션 시작
	        con.setAutoCommit(false);
	        // 1. 다른 대여 요청 거절
	        if (!rr.rejectOtherRentals(con,postNum, rentalNum)) {
	            con.rollback();
	            return false;
	        }
	        // 2. 현재 대여 요청 승인
	        if (!rr.approveRental(con, rentalNum)) {
	            con.rollback();
	            return false;
	        }
	        // 둘 다 성공
	        con.commit();
	        return true;
	    } catch (SQLException e) {
	        try {
	            if (con != null) {con.rollback();}
	        } catch (SQLException rollbackException) {
	            rollbackException.printStackTrace();
	        }
	        e.printStackTrace();
	        return false;

	    } finally {
	    	DBManager.close(con,null);
	    }
	}
	@Override
	public void rejectRental(int rentalNum)throws RentalException{
		int result = rr.rejectRental(rentalNum);
		if(result==0)throw new RentalException("거절에 실패되었습니다.");
	}
	@Override
	public void confirmReturn(int rentalNum)throws RentalException{
		int result = rr.confirmReturn(rentalNum);
		if(result==0)throw new RentalException("반납완료에 실패되었습니다.");
	}

}
