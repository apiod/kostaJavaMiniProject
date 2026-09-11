package main.java.com.rental.post.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.com.rental.common.util.DBManager;
import main.java.com.rental.post.dto.PostCreate;
import main.java.com.rental.post.dto.PostUpdate;
import main.java.com.rental.post.entity.Post;

public class PostRepositoryImpl implements PostRepository {

	@Override
	public int postCreate(PostCreate postCreate) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "INSERT INTO Post" + "(ItemNum, Title, Content," + "RentDate, ReturnDate, Addr)"
				+ "VALUES (?, ?, ?, ?, ?, ?)";
		int result = 0;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, postCreate.getItemNum());
			ps.setString(2, postCreate.getTitle());
			ps.setString(3, postCreate.getContent());
			ps.setString(4, postCreate.getRentDate());
			ps.setString(5, postCreate.getReturnDate());
			ps.setString(6, postCreate.getAddr());

			result = ps.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			DBManager.close(con, ps);
		}
		return result;
	}

	@Override
	public int postUpdate(PostUpdate postUpdate) {
		Connection con = null;
		PreparedStatement ps = null;
		String sql = "UPDATE Post SET Title = ?," 
		+ "Content = ?, RentDate = ?,ReturnDate = ?,"
		+ "Addr = ?,WHERE Postnum = ?";
		int result = 0;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);

			ps.setString(1, postUpdate.getTitle());
			ps.setString(2, postUpdate.getContent());
			ps.setString(3, postUpdate.getRentDate());
			ps.setString(4, postUpdate.getReturnDate());
			ps.setString(5, postUpdate.getAddr());
			ps.setInt(6, postUpdate.getPostNum());

			result = ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, ps);
		}

		return result;
	}

	@Override
	public int postDelete(int postNum) {
		String sql = " DELETE FROM Post WHERE Postnum = ?";
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, postNum);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, ps);
		}

		return result;

	}
	
	@Override
	public Post selectByItemNum(int itemNum) {

	    String sql = "SELECT * FROM Post WHERE ItemNum = ?";
	    Connection con =null;
	    PreparedStatement ps = null;
	    ResultSet rs =null;
	    try {
	    	con = DBManager.getConnection();
	    	ps = con.prepareStatement(sql);
	    	ps.setInt(1, itemNum);
	    	rs= ps.executeQuery();
	    	if (rs.next()) return mapPost(rs);

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
			DBManager.close(con, ps, rs);
		}

	    return null;
	}
	
	@Override
	public List<Post> selectByTitleKeyword(String titleKeyword) {

	    String sql = """
	            SELECT *
	            FROM Post
	            WHERE Title LIKE ?
	            ORDER BY Postnum DESC
	            """;

	    List<Post> posts = new ArrayList<>();

	    try (Connection conn = DBManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, "%" + titleKeyword + "%");

	        try (ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                posts.add(mapPost(rs));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return posts;
	}
	
	@Override
	public List<Post> selectByContentKeyword(String contentKeyword) {

	    String sql = """
	            SELECT *
	            FROM Post
	            WHERE Content LIKE ?
	            ORDER BY Postnum DESC
	            """;

	    List<Post> posts = new ArrayList<>();

	    try (Connection conn = DBManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, "%" + contentKeyword + "%");

	        try (ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                posts.add(mapPost(rs));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return posts;
	}
	@Override
	public List<Post> selectByRentDate(String rentDate) {

	    String sql = """
	            SELECT *
	            FROM Post
	            WHERE DATE(RentDate) = ?
	            ORDER BY Postnum DESC
	            """;

	    List<Post> posts = new ArrayList<>();

	    try (Connection conn = DBManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, rentDate);

	        try (ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                posts.add(mapPost(rs));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return posts;
	}
	
	@Override
	public List<Post> selectByAddr(String addr) {

	    String sql = """
	            SELECT *
	            FROM Post
	            WHERE Addr LIKE ?
	            ORDER BY Postnum DESC
	            """;

	    List<Post> posts = new ArrayList<>();

	    try (Connection conn = DBManager.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, "%" + addr + "%");

	        try (ResultSet rs = pstmt.executeQuery()) {

	            while (rs.next()) {
	                posts.add(mapPost(rs));
	            }
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return posts;
	}
	
	private Post mapPost(ResultSet rs) throws SQLException {

	    Post post = new Post();

	    post.setPostNum(rs.getInt("Postnum"));
	    post.setItemNum(rs.getInt("ItemNum"));
	    post.setTitle(rs.getString("Title"));
	    post.setContent(rs.getString("Content"));
	    post.setCreateAt(rs.getString("CreateAt"));
	    post.setUpdateAt(rs.getString("UpdateAt"));
	    post.setRentDate(rs.getString("RentDate"));
	    post.setReturnDate(rs.getString("ReturnDate"));
	    post.setAddr(rs.getString("Addr"));

	    return post;
	}

}
