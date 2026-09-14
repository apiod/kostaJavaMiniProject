package main.java.com.rental.item.entity;

public class Item {
	private int itemNum;
	private String itemName;
	private boolean status;
	private String smallCategoryCode;
	private String lenderID;

	
	public Item() {}


	public Item(int itemNum, String itemName, boolean status, String smallCategoryCode, String lenderID) {
		super();
		this.itemNum = itemNum;
		this.itemName = itemName;
		this.status = status;
		this.smallCategoryCode = smallCategoryCode;
		this.lenderID = lenderID;
	}


	public Item(int itemNum, String itemName, String smallCategoryCode, String lenderID) {
		super();
		this.itemNum = itemNum;
		this.itemName = itemName;
		this.smallCategoryCode = smallCategoryCode;
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


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}


	public String getSmallCategoryCode() {
		return smallCategoryCode;
	}


	public void setSmallCategoryCode(String smallCategoryCode) {
		this.smallCategoryCode = smallCategoryCode;
	}


	public String getLenderID() {
		return lenderID;
	}


	public void setLenderID(String lenderID) {
		this.lenderID = lenderID;
	}


	@Override
	public String toString() {
		return "Item [itemNum=" + itemNum + ", itemName=" + itemName + ", status=" + status
				+ ", smallCategoryCode=" + smallCategoryCode
				+ ", lenderID=" + lenderID + "]";
	}
	
	

	
	
		
}
