package main.java.com.rental.item.dto;

public class ItemUpdateRequest {

	private int itemNum;
	private String itemName;
	private boolean status;
	private String num2;
	
	public ItemUpdateRequest() {}

	public ItemUpdateRequest(int itemNum, String itemName, boolean status, String num2) {
		super();
		this.itemNum = itemNum;
		this.itemName = itemName;
		this.status = status;
		this.num2 = num2;
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

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getCatagory() {
		return num2;
	}

	public void setCatagory(String catagory) {
		this.num2 = catagory;
	}

	
	

	
}
