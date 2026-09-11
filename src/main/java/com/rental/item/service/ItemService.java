package main.java.com.rental.item.service;

import java.sql.SQLException;
import java.util.List;
import main.java.com.rental.common.exception.ItemNotFoundException;
import main.java.com.rental.item.entity.Item;


public interface ItemService {

	//물품 전체 조회
	static List<Item> itemSelectAll() throws ItemNotFoundException;

	// 물품 번호 조회 
	Item itemSelectByitemNum(int itemNum) throws ItemNotFoundException, SQLException;

	// 키워드로 검색
	List<Item> itemSearch(String keyword) throws ItemNotFoundException, SQLException;

	// 물품 등록
	void itemInsert(Item item) throws SQLException;

	// 물품 정보 수정
	void itemUpdate(Item item) throws ItemNotFoundException, SQLException;

	// 대여 상태 변경
	int itemUpdateStatus(int itemNum, String status) throws ItemNotFoundException, SQLException;

	// 물품 삭제
	int itemDelete(int itemNum) throws SQLException;

	static Item boardSelectByNo(int itemNum) {
		// TODO Auto-generated method stub
		return null;
	}

	
	

}
