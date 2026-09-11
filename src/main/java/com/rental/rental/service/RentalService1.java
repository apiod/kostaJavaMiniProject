package main.java.com.rental.rental.service;

import java.sql.Connection;
import java.sql.SQLException;

import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.common.util.DBManager;
import main.java.com.rental.rental.repository.Rental;
import main.java.com.rental.rental.repository.RentalImpl;

public class RentalService1 {
	Rental rr = new RentalImpl();
	
	private RentalService1() {	}
	public static RentalService1 getInstance() {
		return new RentalService1();
	}
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
	public void rejectRental(int rentalNum)throws RentalException{
		int result = rr.rejectRental(rentalNum);
		if(result==0)throw new RentalException("거절에 실패되었습니다.");
	}
	public void confirmReturn(int rentalNum)throws RentalException{
		int result = rr.confirmReturn(rentalNum);
		if(result==0)throw new RentalException("반납완료에 실패되었습니다.");
	}

}
