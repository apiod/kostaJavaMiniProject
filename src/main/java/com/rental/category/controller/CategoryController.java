package main.java.com.rental.category.controller;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.category.dto.CategoryRequest;
import main.java.com.rental.category.entity.Category;
import main.java.com.rental.category.service.CategoryService;
import main.java.com.rental.category.service.CategoryServiceImpl;

public class CategoryController {

	public CategoryService categoryService = new CategoryServiceImpl();

	// 대분류 카테고리 목록 조회
	public List<Category> getBigCategories() throws SQLException {
		return categoryService.getBigCategories();
	}

	// 선택한 대분류에 속한 소분류 카테고리 목록 조회
	public List<Category> getSmallCategories(CategoryRequest request) throws SQLException {
		return categoryService.getSmallCategories(request);
	}
}
