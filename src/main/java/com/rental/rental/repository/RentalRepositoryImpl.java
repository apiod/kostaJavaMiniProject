package main.java.com.rental.rental.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.rental.common.util.DBManager;
import main.java.com.rental.rental.dto.ReturnRequest;
import main.java.com.rental.rental.dto.ReturnResponse;
import main.java.com.rental.rental.dto.ReturnSearchRequest;
import main.java.com.rental.session.Session;


public class RentalRepositoryImpl implements RentalRepository {

	@Override
	public List<ReturnResponse> findRentedItems(ReturnSearchRequest request) throws SQLException {
		
		Connection con=null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		List<ReturnResponse> list = new ArrayList<>();
		String sql="SELECT RentalNum, itemName, returnDate, addr, status, "
			      + "lenderNickName, lenderPhone "
			      + "FROM v_rental_info "
			      + "WHERE BorrowerID = ? AND status = ?";
		
		//대여자 입장 
//		SELECT RentalNum, itemName, returnDate, addr, status,
//	       borrowerNickName, borrowerPhone
//	FROM v_rental_info
//	WHERE lenderID = ?
//	AND status = ?;
		
		
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			String loginId = Session.getInstance().getLoginUser().getId();

			ps.setString(1, loginId);
			ps.setInt(2, 100);
			
			 rs = ps.executeQuery();

		        while (rs.next()) {
		        	ReturnResponse response = new ReturnResponse(
		                     rs.getInt("RentalNum"),
		                     rs.getString("itemName"),
		                     rs.getString("returnDate"),
		                     rs.getString("addr"),
		                     rs.getInt("status"),
		                     rs.getString("lenderNickName"),
		                     rs.getString("lenderPhone")
		                  
		                 );

		                 list.add(response);
		        	}
		        
		        //대여자 입장 
//		        while (rs.next()) {
//		            LenderRentalResponse response = new LenderRentalResponse(
//		                rs.getInt("RentalNum"),
//		                rs.getString("itemName"),
//		                rs.getString("returnDate"),
//		                rs.getString("addr"),
//		                rs.getInt("status"),
//		                rs.getString("borrowerNickName"),
//		                rs.getString("borrowerPhone")
//		            );
//		        }
		        
		        } finally {
		            DBManager.close(con, ps, rs);
		        }

		        return list;
		}

	@Override
	public int requestReturn(ReturnRequest request) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ReturnResponse> findRequestedReturnItems() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
			
		
	
