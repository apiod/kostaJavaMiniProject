package com.rental.common.util;

/**
 * JDBC를 위한 로드, 연결, 닫기
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

	/**
	 * 로드
	 * */
	static {
		try {
			Class.forName(DBProperties.DRIVER_NAME);
		} catch (ClassNotFoundException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	/**
	 * 연결
	 * */
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(DBProperties.URL,
				DBProperties.USER_ID, DBProperties.USER_PASS);
	}

	/**
	 * 닫기(DML전용)
	 * */
	public static void releaseConnection(Connection con, Statement st) {
		close(st);
		close(con);
	}

	/**
	 * 닫기(select전용)
	 * */
	public static void releaseConnection(Connection con, Statement st, ResultSet rs) {
		close(rs);
		close(st);
		close(con);
	}

	private static void close(AutoCloseable resource) {
		if (resource == null) {
			return;
		}

		try {
			resource.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
