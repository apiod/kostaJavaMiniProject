package main.java.com.rental.category.service;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.category.dto.CategoryRequest;
import main.java.com.rental.category.entity.Category;
import main.java.com.rental.category.repository.CategoryRepository;
import main.java.com.rental.category.repository.CategoryRepositoryImpl;

public class CategoryServiceImpl implements CategoryService {

	public CategoryRepository categoryRepository = new CategoryRepositoryImpl();

	@Override
	public List<Category> getBigCategories() throws SQLException {
		return categoryRepository.getBigCategories();
	}

	@Override
	public List<Category> getSmallCategories(CategoryRequest request) throws SQLException {
		return categoryRepository.getSmallCategories(request.getBigCategoryCode());
	}
}
