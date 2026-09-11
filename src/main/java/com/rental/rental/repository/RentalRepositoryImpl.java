package main.java.com.rental.rental.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.rental.common.util.DBManager;
import main.java.com.rental.rental.dto.BorrowerActionRequest;
import main.java.com.rental.rental.dto.BorrowerRentalResponse;
import main.java.com.rental.rental.dto.LenderRentalResponse;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.dto.LenderActionRequest;
import main.java.com.rental.session.Session;

public class RentalRepositoryImpl implements RentalRepository {

	/*
	 * 임차인 입장의 물품 조회하기
	 */
	@Override
	public List<BorrowerRentalResponse> findBorrowedItems(BorrowerActionRequest request) throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<BorrowerRentalResponse> list = new ArrayList<>();
		String sql = "SELECT RentalNum, ItemName, ReturnDate, Addr, Status, " + "LenderNickName, LenderPhone "
				+ "FROM View_Rental_Info " + "WHERE BorrowerID = ? AND Status = ?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			String loginId = Session.getInstance().getLoginUser().getId();

			ps.setString(1, loginId);
			ps.setInt(2, request.getStatus().getCode());

			rs = ps.executeQuery();

			while (rs.next()) {
				BorrowerRentalResponse response = new BorrowerRentalResponse(rs.getInt("RentalNum"),
						rs.getString("ItemName"), rs.getString("ReturnDate"), rs.getString("Addr"), rs.getInt("Status"),
						rs.getString("LenderNickName"), rs.getString("LenderPhone")

				);

				list.add(response);
			}

		} finally {
			DBManager.close(con, ps, rs);
		}

		return list;
	}

	/*
	 * 대여자 입장의 물품 조회하기
	 */
	@Override
	public List<LenderRentalResponse> findLentItems(LenderActionRequest request) throws SQLException {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<LenderRentalResponse> list = new ArrayList<>();
		String sql = "SELECT RentalNum, ItemName, ReturnDate, Addr, Status, " + "BorrowerNickName, BorrowerPhone "
				+ "FROM View_Rental_Info " + "WHERE LenderID = ? AND Status = ?";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			String loginId = Session.getInstance().getLoginUser().getId();

			ps.setString(1, loginId);
			ps.setInt(2, request.getStatus().getCode());
			rs = ps.executeQuery();

			while (rs.next()) {
				LenderRentalResponse response = new LenderRentalResponse(rs.getInt("RentalNum"),
						rs.getString("ItemName"), rs.getString("ReturnDate"), rs.getString("Addr"), rs.getInt("Status"),
						rs.getString("BorrowerNickName"), rs.getString("BorrowerPhone"));

				list.add(response);
			}

		} finally {
			DBManager.close(con, ps, rs);
		}

		return list;
	}

	/*
	 * 대여 신청하기
	 */
	@Override
	public int requestRental(RentalCreateRequest request) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;

		String sql = "INSERT INTO Rental(BorrowerID, Status, PostNum) " + "VALUES (?, ?, ?)";

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			String loginId = Session.getInstance().getLoginUser().getId();

			ps.setString(1, loginId);
			ps.setInt(2, request.getStatus().getCode());
			ps.setInt(3, request.getPostNum());

			result = ps.executeUpdate();

		} finally {
			DBManager.close(con, ps);
		}

		return result;
	}

}
