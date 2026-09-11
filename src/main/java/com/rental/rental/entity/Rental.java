package main.java.com.rental.rental.entity;

import main.java.com.rental.rental.enums.RentalStatus;

public class Rental {
	private int rentalNum;		//대여번호
	private int postNum;		//물품번호
	private String borrowerID;	//임차인ID
	private RentalStatus status;//상태값
	
	public Rental() {}
	public Rental(int rentalNum, int postNum, String borrowerID, RentalStatus status) {
		super();
		this.rentalNum = rentalNum;
		this.postNum = postNum;
		this.borrowerID = borrowerID;
		this.status = status;
	}
	public int getRentalNum() {
		return rentalNum;
	}
	public void setRentalNum(int rentalNum) {
		this.rentalNum = rentalNum;
	}
	public int getPostNum() {
		return postNum;
	}
	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}
	public String getBorrowerID() {
		return borrowerID;
	}
	public void setBorrowerID(String borrowerID) {
		this.borrowerID = borrowerID;
	}
	public RentalStatus getStatus() {
		return status;
	}
	public void setStatus(RentalStatus status) {
		this.status = status;
	}
	
}