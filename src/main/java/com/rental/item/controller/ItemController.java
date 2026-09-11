package main.java.com.rental.item.controller;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.common.exception.InvalidRentalStatusException;
import main.java.com.rental.common.exception.ItemNotFoundException;
import main.java.com.rental.item.entity.Item;
import main.java.com.rental.item.service.ItemService;
import main.java.com.rental.item.service.ItemServiceImpl;
import main.java.com.rental.view.FailView;
import main.java.com.rental.view.SuccessView;

public class ItemController implements ItemService {

	private ItemService itemService = ItemServiceImpl.getInstance();

	// 전체 목록 조회
	public void itemSelect() {
		try {
			List<Item> list = ItemService.itemSelectAll();
			SuccessView.printItemList(list);
		} catch (ItemNotFoundException e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	// 물품 번호 조회
	public void itemSelectByno(int itemNum) {
		try {
			Item item = itemService.itemSelectByitemNum(itemNum);
			SuccessView.printItem(item);
		} catch (ItemNotFoundException e) {
			FailView.FailMessage(e.getMessage());
		} catch (SQLException e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	// 키워드 검색
	public void itemSearch(String keyword) {
		try {
			List<Item> list = itemService.itemSearch(keyword);
			SuccessView.printItemList(list);
		} catch (ItemNotFoundException e) {
			FailView.FailMessage(e.getMessage());
		} catch (SQLException e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	// 물품 등록
	public void itemInsert(Item item) {
		try {
			itemService.itemInsert(item);
			SuccessView.printMessage("물품이 등록되었습니다");
		} catch (InvalidRentalStatusException e) {
			FailView.FailMessage(e.getMessage());
		} catch (SQLException e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	// 물품 수정
	public void itemUpdate(Item item) {
		try {
			itemService.itemUpdate(item);
			SuccessView.printMessage("물품이 수정되었습니다");
		} catch (ItemNotFoundException e) {
			FailView.FailMessage(e.getMessage());
		} catch (SQLException e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	// 대여 상태 변경
	public void itemUpdateStatus(int itemNum, String status) {
		try {
			itemService.itemUpdateStatus(itemNum, status);
			SuccessView.printMessage("상태가 변경되었습니다");
		} catch (ItemNotFoundException e) {
			FailView.FailMessage(e.getMessage());
		} catch (SQLException e) {
			FailView.FailMessage(e.getMessage());
		}
	}

	// 물품 삭제
	public void itemDelete(int itemNum) {
		try {
			itemService.itemDelete(itemNum);
			SuccessView.printMessage("물품이 삭제되었습니다");
		} catch (SQLException e) {
			FailView.FailMessage(e.getMessage());
		}
	}
}