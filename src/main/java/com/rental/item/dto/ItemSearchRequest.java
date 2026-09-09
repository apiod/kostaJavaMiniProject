package main.java.com.rental.item.dto;


public class ItemSearchRequest {
	private String keyword;      
	private String addr;         
	private int itemcategory;   
	private Boolean status;      
	
	public ItemSearchRequest() {}

	public ItemSearchRequest(String keyword, String addr, int itemcategory, Boolean status) {
		this.keyword = keyword;
		this.addr = addr;
		this.itemcategory = itemcategory;
		this.status = status;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public int getItemcategory() {
		return itemcategory;
	}

	public void setItemcategory(int itemcategory) {
		this.itemcategory = itemcategory;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	

	
}
