package main.java.com.rental.item.entity;

public class Item {
	private int itemNum;
	private String itemName;
	private String status;
	private String num2;
	private String lenderID;

	
	public Item() {}
	public Item(int itemNum, String itemName, String status, String num2, String lenderID) {
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



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getCategory() {
		return num2;
	}



	public void setCategory(String category) {
		this.num2 = category;
	}



	public String getLenderID() {
		return lenderID;
	}



	public void setLenderID(String lenderID) {
		this.lenderID = lenderID;
	}



	public String getNum2() {
		// TODO Auto-generated method stub
		return null;
	}



	public void setNum2(String string) {
		// TODO Auto-generated method stub
		
	}

	
	

	
	
		
}
