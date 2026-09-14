package main.java.com.rental.category.dto;

public class CategoryRequest {

	public String bigCategoryCode;

	public CategoryRequest() {
	}

	public CategoryRequest(String bigCategoryCode) {
		this.bigCategoryCode = bigCategoryCode;
	}

	public String getBigCategoryCode() {
		return bigCategoryCode;
	}

	public void setBigCategoryCode(String bigCategoryCode) {
		this.bigCategoryCode = bigCategoryCode;
	}
}
