package main.java.com.rental.item.dto;

public class ItemCreateRequest {
	
	private int itemNum;
	private String itemName;
	private String status;
	private String category;
	private String lenderID;
	

	public ItemCreateRequest() {}


	public ItemCreateRequest(int itemNum, String itemName, String status, String category, String lenderID) {
		super();
		this.itemNum = itemNum;
		this.itemName = itemName;
		this.status = status;
		this.category = category;
		this.lenderID = lenderID;
	}


	public int getItemNum() {
		return itemNum;
	}


	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getLenderID() {
		return lenderID;
	}


	public void setLenderID(String lenderID) {
		this.lenderID = lenderID;
	}

	
		
}
