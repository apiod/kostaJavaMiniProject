package main.java.com.rental.item.repository;

import java.sql.SQLException;
import java.util.List;
import main.java.com.rental.item.entity.Item;

public interface ItemRepository {
	// 물품 전체 조회
	List<Item> itemSelect() throws SQLException;

	// 물품 번호 조회
	Item itemSelectByitemNum(int itemNum) throws SQLException;

	// 키워드로 검색
	List<Item> itemSearch(String keyword) throws SQLException;

	// 물품 등록
	int itemInsert(Item item) throws SQLException;

	// 물품 정보 수정
	int itemUpdate(Item item) throws SQLException;

	// 대여 상태 변경
	int itemUpdateStatus(int itemNum, String status) throws SQLException;

	// 물품 삭제
	int itemDelete(int itemNum) throws SQLException;
}
