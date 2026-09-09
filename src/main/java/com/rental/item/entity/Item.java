package main.java.com.rental.item.entity;

public class Item {
	private int itemnum;
	private String rentdate;
	private String returndate;
	private String addr;
	private String lenderID;
	private String title;
	private String itemname;
	private String itemcontent;
	private boolean status = true;
	private int itemcategory;


	public Item() {
		super();
	}

	public Item(int itemnum, String rentdate, String returndate, String addr, String lenderID, String title,
			String itemname, String itemcontent, boolean status, int itemcategory) {
		super();
		this.itemnum = itemnum;
		this.rentdate = rentdate;
		this.returndate = returndate;
		this.addr = addr;
		this.lenderID = lenderID;
		this.title = title;
		this.itemname = itemname;
		this.itemcontent = itemcontent;
		this.status = status;
		this.itemcategory = itemcategory;
	}
	
	
	public int getItemnum() {
		return itemnum;
	}
	public void setItemnum(int itemnum) {
		this.itemnum = itemnum;
	}
	public String getRentdate() {
		return rentdate;
	}
	public void setRentdate(String rentdate) {
		this.rentdate = rentdate;
	}
	public String getReturndate() {
		return returndate;
	}
	public void setReturndate(String returndate) {
		this.returndate = returndate;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getLenderID() {
		return lenderID;
	}
	public void setLenderID(String lenderID) {
		this.lenderID = lenderID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getItemname() {
		return itemname;
	}
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}
	public String getItemcontent() {
		return itemcontent;
	}
	public void setItemcontent(String itemcontent) {
		this.itemcontent = itemcontent;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getItemcategory() {
		return itemcategory;
	}
	public void setItemcategory(int itemcategory) {
		this.itemcategory = itemcategory;
	}
	
	
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	
	
	
}
