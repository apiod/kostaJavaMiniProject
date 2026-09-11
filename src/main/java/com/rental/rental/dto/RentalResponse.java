package main.java.com.rental.rental.dto;

public class RentalResponse {

	private int rentalNum;
	private String itemName;
	private String returnDate;
	private String addr;
	private int status;
	private String NickName;
	private String Phone;


	public RentalResponse() {}


	public RentalResponse(int rentalNum, String itemName, String returnDate, String addr, int status,
	        String NickName, String Phone) {
	    super();
	    this.rentalNum = rentalNum;
	    this.itemName = itemName;
	    this.returnDate = returnDate;
	    this.addr = addr;
	    this.status = status;
	    this.NickName = NickName;
	    this.Phone = Phone;
	}


	public int getRentalNum() {
	    return rentalNum;
	}


	public void setRentalNum(int rentalNum) {
	    this.rentalNum = rentalNum;
	}


	public String getItemName() {
	    return itemName;
	}


	public void setItemName(String itemName) {
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


	public String getNickName() {
	    return NickName;
	}


	public void setNickName(String NickName) {
	    this.NickName = NickName;
	}


	public String getPhone() {
	    return Phone;
	}


	public void setPhone(String Phone) {
	    this.Phone = Phone;
	}

	@Override
	public String toString() {
	    return "물품명 : " + itemName
	            + ", 닉네임 : " + NickName
	            + ", 전화번호 : " + Phone
	            + ", 반납 예정일 : " + returnDate
	            + ", 반납 장소 : " + addr
	            + ", 상태 : " + status;
	}
	}