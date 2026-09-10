package main.java.com.rental;

import java.sql.Connection;
import main.java.com.rental.common.util.DBManeger;
import main.java.com.rental.item.controller.ItemController;
import main.java.com.rental.item.entity.Item;

public class RentalApplication {

	public static void main(String[] args) {
		// 1) DB 연결
		try (Connection con = DBManeger.getConnection()) {
			System.out.println("연결 성공: " + con.getCatalog());
		} catch (Exception e) {
			System.out.println("연결 실패: " + e);
			return;
		}

	}
}