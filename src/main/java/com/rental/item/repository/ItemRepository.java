package main.java.com.rental.item.repository;

import java.util.List;

import main.java.com.rental.common.exception.ItemException;
import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.item.entity.Item;

public interface ItemRepository {
	
	// 물품 전체 조회
	List<Item> itemSelect() throws NotFoundException;

	// 물품 번호 조회
	Item itemSelectByitemNum(int itemNum) throws NotFoundException;

	// 키워드로 검색
	List<Item> itemSearch(String keyword) throws NotFoundException;

	// 물품 등록
	int itemInsert(Item item) throws ItemException;

	// 물품 정보 수정
	int itemUpdate(Item item) throws ItemException;

	// 대여 상태 변경
	int itemUpdateStatus(int itemNum, boolean status) throws ItemException;

	// 물품 삭제
	int itemDelete(int itemNum) throws ItemException;
}
