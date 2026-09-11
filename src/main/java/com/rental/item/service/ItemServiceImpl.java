package main.java.com.rental.item.service;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.common.exception.InvalidRentalStatusException;
import main.java.com.rental.common.exception.ItemNotFoundException;
import main.java.com.rental.item.entity.Item;
import main.java.com.rental.item.repository.ItemRepository;
import main.java.com.rental.item.repository.ItemRepositoryImpl;

public class ItemServiceImpl implements ItemService {

	private static ItemService is = new ItemServiceImpl();
	private ItemRepository ir = ItemRepositoryImpl.getInstance();

	private ItemServiceImpl() {}

	public static ItemService getInstance() {
		return is;
	}

	// 물품 전체 조회
	public List<Item> itemSelectAll() throws ItemNotFoundException {
		List<Item> itemList = ir.itemSelect();
		if (itemList == null) {
			throw new ItemNotFoundException("목록에 물품이 없습니다");
		}
		return itemList;
	}

	@Override
	public Item itemSelectByitemNum(int itemNum) throws ItemNotFoundException{
		Item item = ItemRepositoryImpl.itemSelectByitemNum(itemNum);
		if (item == null) {
			throw new ItemNotFoundException("해당번호 물품 정보가 없습니다");
		}
		return item;
	}

	@Override
	public List<Item> itemSearch(String keyword) throws ItemNotFoundException{
		List<Item> itemList = ItemRepositoryImpl.itemSearch(keyword);
		if (itemList == null) {
			throw new ItemNotFoundException("검색 결과가 없습니다");
		}
		return itemList;
	}

	@Override
	public void itemInsert(Item item) throws SQLException {
		int result = ItemRepositoryImpl.itemInsert(item);
		if (result <= 0) {
			throw new InvalidRentalStatusException("등록되지 않았습니다");
		}
	}

	@Override
	public void itemUpdate(Item item) throws ItemNotFoundException{
		int result = ItemRepositoryImpl.itemUpdate(item);
		if (result == 0) {
			throw new ItemNotFoundException("수정되지 않았습니다");
		}
	}

	@Override
	public int itemUpdateStatus(int itemNum, String status) throws ItemNotFoundException{
		int result = ItemRepositoryImpl.itemUpdateStatus(itemNum, Boolean.parseBoolean(status));
		if (result == 0) {
			throw new ItemNotFoundException("상태가 변경되지 않았습니다");
		}
		return result;
	}

	@Override
	public int itemDelete(int itemNum) throws SQLException {
		return ItemRepositoryImpl.itemDelete(itemNum);
	}
}