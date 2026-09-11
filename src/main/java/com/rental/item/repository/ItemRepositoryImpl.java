package main.java.com.rental.item.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.common.util.DBManager;
import main.java.com.rental.item.entity.Item;

public class ItemRepositoryImpl implements ItemRepository {


	// 전체 조회
	public List<Item> itemSelect() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Item> list = new ArrayList<>();

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement("select * from Item order by ItemNum");
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(mapRow(rs));
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new NotFoundException();
		}
		finally {
			DBManager.close(con, ps, rs);
		}
		return list;
	}

	// 물품번호 검색
	public Item itemSelectByitemNum(int itemNum) throws NotFoundException {
		String sql = "select * from Item where ItemNum = ?";
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, itemNum);
			rs = ps.executeQuery();

			if (rs.next()) {
				return mapRow(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotFoundException();
		} finally {
			DBManager.close(con, ps, rs);
		}
		return null;
	}

	// 물품 검색
	public List<Item> itemSearch(String keyword) throws NotFoundException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Item> list = new ArrayList<>();

		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement("select * from Item where ItemName like ?");
			String like = "%" + (keyword) + "%";
			ps.setString(1, like);
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(mapRow(rs));
			}
		}catch (SQLException e) {
			e.printStackTrace();
			throw new NotFoundException();
		}
		finally {
			DBManager.close(con, ps, rs);
		}
		return list;
	}

	// 물품 등록
	public int itemInsert(Item item) throws NotFoundException {
		String sql = "insert into item " + "(ItemNum, ItemName, Status, Num2, LenderID) " + "values (?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, item.getItemNum());
			ps.setString(2, item.getItemName());
			ps.setBoolean(3, item.isStatus());
			ps.setString(4, item.getNum2());
			ps.setString(5, item.getLenderID());

			if (ps.executeUpdate() == 0) {
				return -1;
			}
			return -1;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotFoundException();
		}finally {
			DBManager.close(con, ps);
		}
	}

	// 물품 수정
	public int itemUpdate(Item item) throws NotFoundException {
		String sql = "update Item set " + "ItemNum = ?, ItemName = ?, Status = ?, Num2 = ?" + "where ItemNum = ?";

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, item.getItemNum());
			ps.setString(2, item.getItemName());
			ps.setBoolean(3, item.isStatus());
			ps.setString(4, item.getNum2());

			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotFoundException();
		}finally {
			DBManager.close(con, ps);
		}
	}

	// 물품 삭제
	public int itemDelete(int itemNum) throws NotFoundException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement("delete from Item where ItemNum = ?");
			ps.setInt(1, itemNum);
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NotFoundException();
		}finally {
			DBManager.close(con, ps);
		}
	}
	
	// 대여 상태 변경
	public int itemUpdateStatus(int itemNum, boolean status) throws NotFoundException{
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement("update Item set Status = ? where ItemNum=?");
			ps.setBoolean(1, status);
			ps.setInt(2, itemNum);
			return ps.executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
			throw new NotFoundException();
		}finally {
			DBManager.close(con,ps);
		}
	}

	// ResultSet 현재 행을 Item으로 매핑
	private static Item mapRow(ResultSet rs) throws SQLException {
		Item item = new Item();
		item.setItemNum(rs.getInt("ItemNum"));
		item.setItemName(rs.getString("ItemName"));
		item.setStatus(rs.getBoolean("Status"));
		item.setNum2(rs.getString("Num2"));
		item.setLenderID(rs.getString("LenderID"));
		return item;
	}


}
