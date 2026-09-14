package main.java.com.rental.rental.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.rental.common.exception.NotFoundException;
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
	public List<Rental> selectLendList(String lenderId) throws RentalException {

		List<Rental> list = new ArrayList<>();

		String sql = """
				SELECT RentalNum,
				       BorrowerId,
				       Status,
				       PostNum
				FROM View_Rental_LenderID
				WHERE LenderId = ?
				""";

		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, lenderId);
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
	 * 내가 빌린 물품 조회
	 * 
	 * @throws RentalException
	 */
	@Override
	public List<Rental> selectBorrowList(String borrowerId) throws RentalException {

		List<Rental> list = new ArrayList<>();

		String sql = """
				SELECT RentalNum,
				       BorrowerId,
				       Status,
				       PostNum
				FROM Rental
				WHERE BorrowerId = ?
				""";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, borrowerId);
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
	public List<Rental> selectRentalRequestList(String userId) throws RentalException {
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
			ps.setString(1, userId);
			ps.setString(2, userId);
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
	public List<Rental> selectCurrentRentalList(String userId) throws RentalException {
		List<Rental> list = new ArrayList<>();
		String sql = """
				SELECT RentalNum,
				       BorrowerId,
				       Status,
				       PostNum
				FROM View_Rental_LenderID
				WHERE Status = 110
				  AND (BorrowerId = ?
				        OR LenderId = ?)
				""";

		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, userId);
			ps.setString(2, userId);

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
	 * 과거 대여 이력
	 *
	 * 210 : 반납 확인 중 211 : 반납 완료
	 *
	 */
	@Override
	public List<Rental> selectRentalHistoryList(String userId) throws RentalException {

		List<Rental> list = new ArrayList<>();

		String sql = """
				SELECT RentalNum,
				       BorrowerId,
				       Status,
				       PostNum
				FROM View_Rental_LenderID
				WHERE Status = 211
				  AND (BorrowerId = ? OR LenderID = ?)
				ORDER BY RentalNum DESC
				""";

		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, userId);
			ps.setString(2, userId);
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
	 * 대여/반납 요청 내역
	 *
	 * status % 100 == 0
	 */
	@Override
	public List<Rental> selectRequestList(String userId) throws RentalException {

		List<Rental> list = new ArrayList<>();
		String sql = """
				 SELECT RentalNum,
				        BorrowerId,
				        Status,
				        PostNum
				 FROM View_Rental_LenderID
				 WHERE MOD(Status, 100) = 0
				   AND ( BorrowerId = ? OR LenderID = ? )
				 ORDER BY RentalNum DESC
				""";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, userId);
			ps.setString(2, userId);
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
	 * 대여 요청 내역
	 *
	 * status / 100 == 1 status % 100 == 0
	 *
	 * 결과적으로 status == 100
	 */
	@Override
	public List<Rental> selectRentalRequestHistory(String userId) throws RentalException {

		List<Rental> list = new ArrayList<>();

		String sql = """
				SELECT RentalNum,
				       BorrowerId,
				       Status,
				       PostNum
				FROM View_Rental_LenderID
				WHERE MOD(Status, 100) = 0
				  AND FLOOR(Status / 100) = 1
				  AND (BorrowerId = ? OR LenderID = ? )
				ORDER BY RentalNum DESC
				""";
		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, userId);
			ps.setString(2, userId);
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
	 * 반납 요청 내역
	 *
	 * status / 100 == 2 status % 100 == 0
	 *
	 * 결과적으로 status == 200
	 */
	@Override
	public List<Rental> selectReturnRequestHistory(String userId) throws RentalException {

		List<Rental> list = new ArrayList<>();

		String sql = """
				SELECT RentalNum,
				       BorrowerId,
				       Status,
				       PostNum
				FROM View_Rental_LenderID
				WHERE MOD(Status, 100) = 0
				  AND FLOOR(Status / 100) = 2
				  AND ( BorrowerId = ? OR LenderID = ? )
				ORDER BY RentalNum DESC
				""";

		try (Connection conn = DBManager.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, userId);
			ps.setString(2, userId);
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
	public int deleteRental(int rentalNum) throws RentalException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		String sql = "DELETE FROM Rental " + "WHERE RentalNum = ? " + "AND status = 100";
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
	public int rejectRental(int rentalNum) throws RentalException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Rental " + "SET status = 102 " + "WHERE rentalNum = ? " + "AND status = 100";
		try {
			con = DBManager.getConnection();
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

	@Override
	public int confirmReturn(int rentalNum) throws RentalException {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Rental " + "SET status = 211 " + "WHERE rentalNum = ? " + "AND status = 210";

		try {
			con = DBManager.getConnection();
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
