package main.java.com.rental.rental.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.common.util.DBManager;
import main.java.com.rental.rental.entity.Rental;
import main.java.com.rental.rental.enums.RentalStatus;
import main.java.com.rental.session.Session;
import main.java.com.rental.rental.dto.RentalCreateRequest;

public class RentalRepositoryImpl implements RentalRepository {
	/**
	 * Rental 생성
	 * 
	 * @throws RentalException
	 */
	@Override
	public int rentalCreate(RentalCreateRequest rentalCreateRequest) throws RentalException {

		String sql = """
				INSERT INTO Rental
				    (BorrowerId, PostNum)
				VALUES
				    (?, ?)
				""";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, rentalCreateRequest.getBorrowerId());
			ps.setInt(2, rentalCreateRequest.getPostNum());

			return ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RentalException();
		}
	}

	/**
	 * rentalRequest status ==100, LenderId가 session~getId()인
	 * 
	 * @throws RentalException
	 */
	public List<Rental> selectRentalRequestListByLender() throws RentalException {
		List<Rental> list = new ArrayList<>();
		String sql = """
				SELECT RentalNum,
				       BorrowerId,
				       Status,
				       PostNum
				FROM View_Rental_LenderID
				WHERE LenderId = ? AND Status =100
				""";

		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, Session.getInstance().getLoginUser().getId());
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRental(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RentalException();
		}

		return list;
	}

	@Override
	public Rental selectByRentalNum(int rentalNum) throws RentalException {
		Rental re = null;
		String sql = """
				SELECT RentalNum,
				       BorrowerId,
				       Status,
				       PostNum
				FROM View_Rental_LenderID
				WHERE LenderId = ? AND RentalNum = ?
				""";

		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, Session.getInstance().getLoginUser().getId());
			ps.setInt(2, rentalNum);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					re = mapRental(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RentalException();
		}

		return re;
	}

//사용
	@Override
	public List<Rental> selectByStatus(int status) throws RentalException {
		List<Rental> list = new ArrayList<>();
		String sql = """
				SELECT RentalNum,
				       BorrowerId,
				       Status,
				       PostNum
				FROM View_Rental_LenderID
				WHERE LenderId = ? AND Status =?
				""";

		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, Session.getInstance().getLoginUser().getId());
			ps.setInt(2, status);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRental(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RentalException();
		}

		return list;
	}

	/**
	 * 대여 신청 현황
	 *
	 * status == 100
	 * 
	 * @throws RentalException
	 */
	@Override
	public List<Rental> selectRentalRequestList() throws RentalException {
		List<Rental> list = new ArrayList<>();
		String sql = """
				SELECT RentalNum,
				       BorrowerID,
				       Status,
				       PostNum
				FROM View_Rental_LenderID
				WHERE Status = 100
				  AND (BorrowerId = ?OR LenderId =?)
				""";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			String id = Session.getInstance().getLoginUser().getId();
			ps.setString(1, id);
			ps.setString(2, id);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(mapRental(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RentalException();
		}

		return list;
	}

	/**
	 * 현재 대여 현황
	 *
	 * 실제 대여 중인 상태 = 110
	 */
	@Override
	public List<Rental> selectCurrentRentalList() throws RentalException {
		List<Rental> list = new ArrayList<>();
		String sql = """
				SELECT RentalNum,
				       BorrowerId,
				       Status,
				       PostNum
				FROM View_Rental_LenderID
				WHERE Status = 110 AND BorrowerId = ?
				""";

		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, Session.getInstance().getLoginUser().getId());
			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					list.add(mapRental(rs));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RentalException();
		}

		return list;
	}

	@Override
	public boolean approveRental(Connection con, int rentalNum) throws RentalException {
		String sql = "UPDATE Rental " + "SET status = 101 " + "WHERE rentalNum = ? " + "AND status = 100";
		PreparedStatement ps = null;
		int result = 0;
		try {
			ps = con.prepareStatement(sql);

			ps.setInt(1, rentalNum);

			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RentalException();
		}
		return result > 0;
	}

	// 같은 게시글의 다른 대여 요청 거절
	@Override
	public boolean rejectOtherRentals(Connection con, int postNum, int rentalNum) throws RentalException {
		String sql = "UPDATE Rental " + "SET status = 102 " + "WHERE postNum = ? " + "AND rentalNum <> ? "
				+ "AND status = 100";
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ps.setInt(1, postNum);
			ps.setInt(2, rentalNum);
			ps.executeUpdate();
			return true;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RentalException();
		} finally {
			DBManager.close(null, ps);
		}
	}

	@Override
	public int updateStatusRentalNum(int setStatus, int rentalNum, int status) throws RentalException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Rental " + "SET status = ? " + "WHERE rentalNum = ? " + "AND status = ?";
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setInt(1, setStatus);
			ps.setInt(2, rentalNum);
			ps.setInt(3, status);

			int result = ps.executeUpdate();
			return result;

		} catch (SQLException e) {
			e.printStackTrace();
			throw new RentalException();

		} finally {
			DBManager.close(con, ps);
		}
	}

	/**
	 * 로그인시 현재 승인 대기중인 목록 출력
	 * 
	 * @throws RentalException
	 */
	@Override
	public List<Rental> getPendingApprovals() throws RentalException {
		String sql = """
				SELECT *
				FROM View_Rental_LenderID
				WHERE status in (100,200) and LenderID =?
				order by LenderID, PostNum
				""";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String LenderID = Session.getInstance().getLoginUser().getId();
		List<Rental> list = new ArrayList<>();
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, LenderID);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(mapRental(rs));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RentalException();
		} finally {
			DBManager.close(con, ps, rs);
		}
		return list;
	}

	private Rental mapRental(ResultSet rs) throws SQLException {

		Rental rental = new Rental(rs.getInt("RentalNum"), rs.getString("BorrowerId"),
				RentalStatus.fromCode(rs.getInt("Status")), rs.getInt("PostNum"));

		return rental;
	}
}