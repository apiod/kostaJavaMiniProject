package main.java.com.rental.rental.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.common.exception.RentalException;
import main.java.com.rental.common.util.DBManager;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.entity.Rental;
import main.java.com.rental.rental.repository.RentalRepository;
import main.java.com.rental.rental.repository.RentalRepositoryImpl;

public class RentalServiceImpl implements RentalService {

	private RentalRepository rr = new RentalRepositoryImpl();

	private RentalServiceImpl() {
	}

	public static RentalServiceImpl getInstance() {
		return new RentalServiceImpl();
	}

	@Override
	public void rentalCreate(RentalCreateRequest rentalCreateRequest) throws RentalException {
		int re = rr.rentalCreate(rentalCreateRequest);
		if (re == 0)
			throw new RentalException("생성에 실패했습니다.");
	}

	@Override
	public List<Rental> selectLendList(String lenderId) throws RentalException, NotFoundException {
		List<Rental> re = rr.selectLendList(lenderId);
		if (re.isEmpty())
			throw new NotFoundException();
		return re;
	}

	@Override
	public List<Rental> selectBorrowList(String borrowerId) throws RentalException, NotFoundException {
		List<Rental> re = rr.selectBorrowList(borrowerId);
		if (re.isEmpty())
			throw new NotFoundException();
		return re;
	}

	@Override
	public List<Rental> selectRentalRequestList(String userId) throws RentalException, NotFoundException {
		List<Rental> re = rr.selectRentalRequestList(userId);
		if (re.isEmpty())
			throw new NotFoundException();
		return re;
	}

	@Override
	public List<Rental> selectCurrentRentalList(String userId) throws RentalException, NotFoundException {
		List<Rental> re = rr.selectCurrentRentalList(userId);
		if (re.isEmpty())
			throw new NotFoundException();
		return re;
	}

	@Override
	public List<Rental> selectRentalHistoryList(String userId) throws RentalException, NotFoundException {
		List<Rental> re = rr.selectRentalHistoryList(userId);
		if (re.isEmpty())
			throw new NotFoundException();
		return re;
	}

	@Override
	public List<Rental> selectRequestList(String userId) throws RentalException, NotFoundException {
		List<Rental> re = rr.selectRequestList(userId);
		if (re.isEmpty())
			throw new NotFoundException();
		return re;
	}

	@Override
	public List<Rental> selectRentalRequestHistory(String userId) throws RentalException, NotFoundException {
		List<Rental> re = rr.selectRentalRequestHistory(userId);
		if (re.isEmpty())
			throw new NotFoundException();
		return re;
	}

	@Override
	public List<Rental> selectReturnRequestHistory(String userId) throws RentalException, NotFoundException {
		List<Rental> re = rr.selectReturnRequestHistory(userId);
		if (re.isEmpty())
			throw new NotFoundException();
		return re;
	}

	@Override
	public boolean approveRental(int rentalNum, int postNum) throws RentalException {
		Connection con = null;
		try {
			con = DBManager.getConnection();
			// 트랜잭션 시작
			con.setAutoCommit(false);
			// 1. 다른 대여 요청 거절
			if (!rr.rejectOtherRentals(con, postNum, rentalNum)) {
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
				if (con != null) {
					con.rollback();
				}
			} catch (SQLException rollbackException) {
				rollbackException.printStackTrace();
			}
			e.printStackTrace();
			return false;

		} finally {
			DBManager.close(con, null);
		}
	}

	@Override
	public void rejectRental(int rentalNum) throws RentalException {
		int result = rr.rejectRental(rentalNum);
		if (result == 0)
			throw new RentalException("거절에 실패되었습니다.");
	}

	@Override
	public void confirmReturn(int rentalNum) throws RentalException {
		int result = rr.confirmReturn(rentalNum);
		if (result == 0)
			throw new RentalException("반납완료에 실패되었습니다.");
	}

}
