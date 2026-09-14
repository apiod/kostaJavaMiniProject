package main.java.com.rental.category.entity;

public class Category {

	public String code;
	public String name;

	public Category() {
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("카테고리 명: ");
		builder.append(name);
		return builder.toString();
	}

	public Category(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
