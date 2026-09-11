package main.java.com.rental.item.service;

import java.sql.SQLException;
import java.util.List;
import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.item.entity.Item;


public interface ItemService {

	//물품 전체 조회
	static List<Item> itemSelect() throws NotFoundException {
		return null;
	}

	// 물품 번호 조회 
	Item itemSelectByitemNum(int itemNum) throws NotFoundException;

	// 키워드로 검색
	List<Item> itemSearch(String keyword) throws NotFoundException;

	// 물품 등록
	void itemInsert(Item item) throws SQLException;

	// 물품 정보 수정
	void itemUpdate(Item item) throws NotFoundException;

	// 대여 상태 변경
	void itemUpdateStatus(int itemNum, boolean status) throws NotFoundException;

	// 물품 삭제
	void itemDelete(int itemNum) throws SQLException;

	static Item itemSelectByNo(int itemNum) {
		// TODO Auto-generated method stub
		return null;
	}


	

}
