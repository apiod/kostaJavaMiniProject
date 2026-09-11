package main.java.com.rental.item.service;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.common.exception.InvalidRentalStatusException;
import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.item.entity.Item;
import main.java.com.rental.item.repository.ItemRepository;
import main.java.com.rental.item.repository.ItemRepositoryImpl;

public class ItemServiceImpl implements ItemService {

	private static ItemService is = new ItemServiceImpl();
	ItemRepository ir = new ItemRepositoryImpl();

	private ItemServiceImpl() {
	}

	public static ItemService getInstance() {
		return is;
	}

	// 물품 전체 조회
	public List<Item> itemSelectAll() throws NotFoundException {
		List<Item> itemList = ir.itemSelect();

		if (itemList == null) {
			throw new NotFoundException("목록에 물품이 없습니다");
		}

		return itemList;
	}

	@Override
	public Item itemSelectByitemNum(int itemNum) throws SQLException {
		Item item = ir.itemSelectByitemNum(itemNum);
		try {
			if (item == null) {
				throw new NotFoundException("해당번호 물품 정보가 없습니다");
			}
		} finally {

		}
		return item;
	}

	@Override
	public List<Item> itemSearch(String keyword) throws NotFoundException {
		List<Item> itemList = ir.itemSearch(keyword);
		if (itemList == null) {
			throw new NotFoundException("검색 결과가 없습니다");
		}
		return itemList;
	}

	@Override
	public void itemInsert(Item item) throws SQLException {
		int result = ir.itemInsert(item);
		if (result <= 0) {
			throw new InvalidRentalStatusException("등록되지 않았습니다");
		}
	}

	@Override
	public void itemUpdate(Item item) throws SQLException {
		int result = ir.itemUpdate(item);
		if (result == 0) {
			throw new NotFoundException("수정되지 않았습니다");
		}
	}

	@Override
	public int itemUpdateStatus(int itemNum, boolean status) throws NotFoundException {
		int result = ir.itemUpdateStatus(itemNum, status);
		if (result == 0) {
			throw new NotFoundException("상태가 변경되지 않았습니다");
		}
		return result;
	}

	@Override
	public int itemDelete(int itemNum) throws SQLException {
		return ir.itemDelete(itemNum);
	}
}