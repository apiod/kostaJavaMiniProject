package main.java.com.rental.rental.dto;

public class BorrowerReturnResponse {

	private int rentalNum;
	private String itemName;
	private String returnDate;
	private String addr;
	private String status;
	private String borrowerNickname;
	private int borrowerPhone;
	
	public BorrowerReturnResponse() {}

	public BorrowerReturnResponse(int rentalNum, String name, String returnDate, String addr, String status,
			String borrowerNickname, int borrowerPhone) {
		super();
		this.rentalNum = rentalNum;
		this.itemName = name;
		this.returnDate = returnDate;
		this.addr = addr;
		this.status = status;
		this.borrowerNickname = borrowerNickname;
		this.borrowerPhone = borrowerPhone;
	}

	public int getRentalNum() {
		return rentalNum;
	}

	public void setRentalNum(int rentalNum) {
		this.rentalNum = rentalNum;
	}

	public String getName() {
		return itemName;
	}

	public void setName(String name) {
		this.itemName = name;
	}

	public String getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBorrowerNickname() {
		return borrowerNickname;
	}

	public void setBorrowerNickname(String borrowerNickname) {
		this.borrowerNickname = borrowerNickname;
	}

	public int getBorrowerPhone() {
		return borrowerPhone;
	}

	public void setBorrowerPhone(int borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}
	
	
	
}
