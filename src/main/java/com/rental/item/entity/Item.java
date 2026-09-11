package main.java.com.rental.item.entity;

public class Item {
	private int itemNum;
	private String itemName;
	private boolean status;
	private String num2;
	private String lenderID;

	
	public Item() {}


	public Item(int itemNum, String itemName, boolean status, String num2, String lenderID) {
		super();
		this.itemNum = itemNum;
		this.itemName = itemName;
		this.status = status;
		this.num2 = num2;
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


	public String getNum2() {
		return num2;
	}


	public void setNum2(String num2) {
		this.num2 = num2;
	}


	public String getLenderID() {
		return lenderID;
	}


	public void setLenderID(String lenderID) {
		this.lenderID = lenderID;
	}


	@Override
	public String toString() {
		return "Item [itemNum=" + itemNum + ", itemName=" + itemName + ", status=" + status + ", num2=" + num2
				+ ", lenderID=" + lenderID + "]";
	}
	
	

	
	
		
}
