package main.java.com.rental.rental.dto;

public class LenderRentalResponse {

	private int rentalNum;
	private String itemName;
	private String returnDate;
	private String addr;
	private int status;
	private String borrowerNickname;
	private String borrowerPhone;
	
	public LenderRentalResponse() {}

	public LenderRentalResponse(int rentalNum, String name, String returnDate, String addr, int status,
			String borrowerNickname, String borrowerPhone) {
		super();
		this.rentalNum = rentalNum;
		this.itemName = itemName;
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

	public void setName(String itemName) {
		this.itemName = itemName;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getBorrowerNickname() {
		return borrowerNickname;
	}

	public void setBorrowerNickname(String borrowerNickname) {
		this.borrowerNickname = borrowerNickname;
	}

	public String getBorrowerPhone() {
		return borrowerPhone;
	}

	public void setBorrowerPhone(String borrowerPhone) {
		this.borrowerPhone = borrowerPhone;
	}
	
	
	
}
