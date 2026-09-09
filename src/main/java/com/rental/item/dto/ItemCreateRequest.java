package main.java.com.rental.item.dto;



public class ItemCreateRequest {
	private String title;        
	private String itemname;     
	private String itemcontent;  
	private String addr;         
	private String lenderID;    
	private int itemcategory;    
	private String rentdate;     
	private String returndate;   
	private boolean status;

	public ItemCreateRequest() {}

	public ItemCreateRequest(String title, String itemname, String itemcontent, String addr, String lenderID,
			int itemcategory, String rentdate, String returndate, boolean status) {
		super();
		this.title = title;
		this.itemname = itemname;
		this.itemcontent = itemcontent;
		this.addr = addr;
		this.lenderID = lenderID;
		this.itemcategory = itemcategory;
		this.rentdate = rentdate;
		this.returndate = returndate;
		this.status = status;
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

	public int getItemcategory() {
		return itemcategory;
	}

	public void setItemcategory(int itemcategory) {
		this.itemcategory = itemcategory;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	
	
	
}
