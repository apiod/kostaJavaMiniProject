package main.java.com.rental.item.repository;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.com.rental.common.util.DBManeger;
import main.java.com.rental.item.entity.Item;


public interface ItemRepositoryImpl{
 // db연결

	// 전체 조회
	public static List<Item> itemSelect() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Item> list = new ArrayList<>();

		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement("select * from Item order by ItemNum");
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(mapRow(rs));
			}
		} finally {
			DBManeger.close(con, ps, rs);
		}
		return list;
	}

	// 물품번호 검색
	public static Item itemSelectByitemNum(int itemNum) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Item item = null;

		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement("select * from Item where ItemNum = ?");
			ps.setInt(1, itemNum);
			rs = ps.executeQuery();

			if (rs.next()) {
				item = mapRow(rs);
			}
		} finally {
			DBManeger.close(con, ps, rs);
		}
		return item;
	}

	// 물품 검색
	public static List<Item> itemSearch(String keyword) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Item> list = new ArrayList<>();

		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement(
					"select * from item where ItemName like ?");
			String like = "%" + (keyword) + "%";
			ps.setString(1, like);
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(mapRow(rs));
			}
		} finally {
			DBManeger.close(con, ps, rs);
		}
		return list;
	}

	// 물품 등록
	public static int itemInsert(Item item) throws SQLException {
		String sql = "insert into item "
				+ "(ItemNum, ItemName, Status, Num2, LenderID) "
				+ "values (?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, item.getItemNum());
			ps.setString(2, item.getItemName());
			ps.setString(3, item.getStatus());
			ps.setString(4, item.getNum2());
			ps.setString(5, item.getLenderID());

			if (ps.executeUpdate() == 0) {
				return -1;
			}
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				return rs.getInt(1);
			}
			return -1;
		} finally {
			DBManeger.close(con, ps, rs);
		}
	}

	// 물품 수정
	public static int itemUpdate(Item item) throws SQLException {
		String sql = "update Item set "
				+ "ItemNum = ?, ItemName = ?, Status = ?, Num2 = ?"
				+ "where itemnum = ?";
 
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, item.getItemNum());
			ps.setString(2, item.getItemName());
			ps.setString(3, item.getStatus());
			ps.setString(4, item.getNum2());

			return ps.executeUpdate();
		} finally {
			DBManeger.close(con, ps, null);
		}
	}

	// 물품 삭제
	public static int itemDelete(int itemNum) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement("delete from Item where ItemNum = ?");
			ps.setInt(1, itemNum);
			return ps.executeUpdate();
		} finally {
			DBManeger.close(con, ps, null);
		}
	}

	// ResultSet 현재 행을 Item으로 매핑
	private static Item mapRow(ResultSet rs) throws SQLException {
		Item item = new Item();
		item.setItemNum(rs.getInt("ItemNum"));
		item.setItemName(rs.getString("ItemName"));
		item.setStatus(rs.getString("Status"));
		item.setNum2(rs.getString("Num2"));
		item.setLenderID(rs.getString("LenderID"));
		return item;
	}

	public static int itemUpdateStatus(int itemNum, boolean status) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}
}
