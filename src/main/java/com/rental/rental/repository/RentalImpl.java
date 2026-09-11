package main.java.com.rental.rental.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.common.util.DBManager;

public class RentalImpl implements Rental{
	
	
	@Override
    public int deleteRental(int rentalNum)throws RentalException {
		Connection con=null;
		PreparedStatement ps = null;
        int result =0;
        String sql =
                "DELETE FROM Rental " +
                "WHERE RentalNum = ? " +
                "AND status = 100";
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, rentalNum);
            result = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RentalException();
        } finally {
        	DBManager.close(con, ps);
        }
        return result;
    }
	@Override
	public boolean approveRental(Connection con, int rentalNum) throws RentalException{
	    String sql =
	            "UPDATE Rental " +
	            "SET status = 101 " +
	            "WHERE rentalNum = ? " +
	            "AND status = 100";
	    PreparedStatement ps =null;
	    int result =0;
	    try {
	    	ps = con.prepareStatement(sql);

	    	ps.setInt(1, rentalNum);

	    	result = ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RentalException();
	    }
	    return result>0;
	}
	//같은 게시글의 다른 대여 요청 거절
	@Override
	public boolean rejectOtherRentals(Connection con, int postNum, int rentalNum) throws RentalException {
	    String sql =
	            "UPDATE Rental " +
	            "SET status = 102 " +
	            "WHERE postNum = ? " +
	            "AND rentalNum <> ? " +
	            "AND status = 100";
	    PreparedStatement ps =null;
	    try {
	        ps = con.prepareStatement(sql);
	        ps.setInt(1, postNum);
	        ps.setInt(2, rentalNum);
	        ps.executeUpdate();
	        return true;

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RentalException();
	    }finally {
			DBManager.close(null,ps);
		}
	}
	
	@Override
	public int rejectRental(int rentalNum) throws RentalException{
		Connection con =null;
		PreparedStatement ps =null;
		String sql =
                "UPDATE Rental " +
                "SET status = 102 " +
                "WHERE rentalNum = ? " +
                "AND status = 100";
        try {
        	con=DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, rentalNum);
            int result = ps.executeUpdate();
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RentalException();
        } finally {
            DBManager.close(con,ps);
        }
	}

	@Override
	public int confirmReturn(int rentalNum)throws RentalException {
		Connection con =null;
		PreparedStatement ps =null;
		String sql =
                "UPDATE Rental " +
                "SET status = 211 " +
                "WHERE rentalNum = ? " +
                "AND status = 210";

        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, rentalNum);

            int result = ps.executeUpdate();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RentalException();

        } finally {
        	DBManager.close(con, ps);
        }
	}

}
