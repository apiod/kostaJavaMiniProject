package main.java.com.rental.item.service;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.common.exception.InvalidRentalStatusException;
import main.java.com.rental.common.exception.ItemException;
import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.item.entity.Item;
import main.java.com.rental.item.repository.ItemRepository;
import main.java.com.rental.item.repository.ItemRepositoryImpl;

public class ItemServiceImpl implements ItemService {

    ItemRepository ir = new ItemRepositoryImpl();
    
 // Singleton
    private static ItemService is = new ItemServiceImpl();
    private ItemServiceImpl() {}
    public static ItemService getInstance() {
        return is;
    }

    // 물품 전체 조회
    @Override
    public List<Item> itemSelect() throws NotFoundException {

        List<Item> itemList = ir.itemSelect();

        if (itemList.isEmpty()) {
            throw new NotFoundException("목록에 물품이 없습니다");
        }

        return itemList;
    }

    // 물품 번호로 조회
    @Override
    public Item itemSelectByitemNum(int itemNum)throws NotFoundException {

        Item item = ir.itemSelectByitemNum(itemNum);

        if (item == null) {
            throw new NotFoundException("해당번호 물품 정보가 없습니다");
        }

        return item;
    }

    // 물품 검색
    @Override
    public List<Item> itemSearch(String keyword) throws NotFoundException {

        List<Item> itemList = ir.itemSearch(keyword);

        if (itemList.isEmpty()) {
            throw new NotFoundException("검색 결과가 없습니다");
        }

        return itemList;
    }

    // 물품 등록
    @Override
    public void itemInsert(Item item) throws ItemException {

        int result = ir.itemInsert(item);

        if (result == 0) {
            throw new InvalidRentalStatusException("등록되지 않았습니다");
        }
    }

    // 물품 수정
    @Override
    public void itemUpdate(Item item)throws ItemException {

        int result = ir.itemUpdate(item);

        if (result == 0) {
            throw new NotFoundException("수정되지 않았습니다");
        }
    }

    // 물품 상태 변경
    @Override
    public void itemUpdateStatus(int itemNum, boolean status) throws ItemException {

        int result = ir.itemUpdateStatus(itemNum, status);

        if (result == 0) {
            throw new NotFoundException("상태가 변경되지 않았습니다");
        }
    }

    // 물품 삭제
    @Override
    public void itemDelete(int itemNum) throws ItemException {
    	int result = ir.itemDelete(itemNum);

        if (result == 0) {
            throw new NotFoundException("삭제되지 않았습니다");
        }
    }
}

