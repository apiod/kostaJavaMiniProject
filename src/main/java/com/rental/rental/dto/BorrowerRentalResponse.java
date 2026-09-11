package main.java.com.rental.rental.dto;

public class BorrowerRentalResponse {

	private int rentalNum;
	private String itemName;
	private String returnDate;
	private String addr;
	private int status;
	private String lenderNickName;
	private String lenderPhone;


	public BorrowerRentalResponse() {}


	public BorrowerRentalResponse(int rentalNum, String itemName, String returnDate, String addr, int status,
	        String lenderNickName, String lenderPhone) {
	    super();
	    this.rentalNum = rentalNum;
	    this.itemName = itemName;
	    this.returnDate = returnDate;
	    this.addr = addr;
	    this.status = status;
	    this.lenderNickName = lenderNickName;
	    this.lenderPhone = lenderPhone;
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


	public String getLenderNickName() {
	    return lenderNickName;
	}


	public void setLenderNickName(String lenderNickName) {
	    this.lenderNickName = lenderNickName;
	}


	public String getLenderPhone() {
	    return lenderPhone;
	}


	public void setLenderPhone(String lenderPhone) {
	    this.lenderPhone = lenderPhone;
	}

	@Override
	public String toString() {
	    return "물품명 : " + itemName
	            + ", 대여자 : " + lenderNickName
	            + ", 전화번호 : " + lenderPhone
	            + ", 반납 예정일 : " + returnDate
	            + ", 반납 장소 : " + addr
	            + ", 상태 : " + status;
	}
	}