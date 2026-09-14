package main.java.com.rental.category.service;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.category.dto.CategoryRequest;
import main.java.com.rental.category.entity.Category;

public interface CategoryService {

	// 대분류 카테고리 목록 조회
	List<Category> getBigCategories() throws SQLException;

	// 선택한 대분류에 속한 소분류 카테고리 목록 조회
	List<Category> getSmallCategories(CategoryRequest request) throws SQLException;
}
