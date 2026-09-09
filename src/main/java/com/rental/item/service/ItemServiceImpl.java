package main.java.com.rental.item.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import main.java.com.rental.common.util.DBManeger;
import main.java.com.rental.item.entity.Item;

public abstract class ItemServiceImpl implements ItemService {

	@Override
	public List<Item> itemSelect() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Item> list = new ArrayList<>();

		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement("select * from item");
			rs = ps.executeQuery();

			while (rs.next()) {
				list.add(mapRow(rs));
			}
		} finally {
			DBManeger.close(con, ps, rs);
		}
		return list;
	}

	@Override
	public Item itemSelectByitemNum(int itemNum) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Item item = null;

		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement("select * from item where itemnum = ?");
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

	@Override
	public List<Item> itemSearch(String keyword) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Item> list = new ArrayList<>();

		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement(
					"select * from item where title like ?");
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

	@Override
	public int itemInsert(Item item) throws SQLException {
		String sql = "insert into item "
				+ "(rentdate, returndate, addr, lenderID, title, itemname, itemcontent, status, itemcategory) "
				+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, item.getRentdate());
			ps.setString(2, item.getReturndate());
			ps.setString(3, item.getAddr());
			ps.setString(4, item.getLenderID());
			ps.setString(5, item.getTitle());
			ps.setString(6, item.getItemname());
			ps.setString(7, item.getItemcontent());
			ps.setBoolean(8, item.isStatus());
			ps.setInt(9, item.getItemcategory());

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

	@Override
	public int itemUpdate(Item item) throws SQLException {
		String sql = "update item set "
				+ "rentdate = ?, returndate = ?, addr = ?, title = ?, itemname = ?, "
				+ "itemcontent = ?, status = ?, itemcategory = ? "
				+ "where itemnum = ?";

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, item.getRentdate());
			ps.setString(2, item.getReturndate());
			ps.setString(3, item.getAddr());
			ps.setString(4, item.getTitle());
			ps.setString(5, item.getItemname());
			ps.setString(6, item.getItemcontent());
			ps.setBoolean(7, item.isStatus());
			ps.setInt(8, item.getItemcategory());
			ps.setInt(9, item.getItemnum());
			return ps.executeUpdate();
		} finally {
			DBManeger.close(con, ps, null);
		}
	}


	@Override
	public int itemDelete(int itemNum) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBManeger.getConnection();
			ps = con.prepareStatement("delete from item where itemnum = ?");
			ps.setInt(1, itemNum);
			return ps.executeUpdate();
		} finally {
			DBManeger.close(con, ps, null);
		}
	}

	// ResultSet 현재 행을 Item으로 매핑
	private Item mapRow(ResultSet rs) throws SQLException {
		Item item = new Item();
		item.setItemnum(rs.getInt("itemnum"));
		item.setRentdate(rs.getString("rentdate"));
		item.setReturndate(rs.getString("returndate"));
		item.setAddr(rs.getString("addr"));
		item.setLenderID(rs.getString("lenderID"));
		item.setTitle(rs.getString("title"));
		item.setItemname(rs.getString("itemname"));
		item.setItemcontent(rs.getString("itemcontent"));
		item.setStatus(rs.getBoolean("status"));
		item.setItemcategory(rs.getInt("itemcategory"));
		return item;
	}
}
