package main.java.com.rental.rental.dto;

import main.java.com.rental.rental.enums.RentalStatus;

public class RentalCreateRequest {

	private int postNum;
	private RentalStatus status;
	private String borrowerId;

	public RentalCreateRequest() {
	}

	public RentalCreateRequest(int postNum, RentalStatus status, String borrowerId) {
		super();
		this.postNum = postNum;
		this.status = status;
		this.borrowerId = borrowerId;
	}

	public int getPostNum() {
		return postNum;
	}

	public void setPostNum(int postNum) {
		this.postNum = postNum;
	}

	public RentalStatus getStatus() {
		return status;
	}

	public void setStatus(RentalStatus status) {
		this.status = status;
	}

	public String getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(String borrowerId) {
		this.borrowerId = borrowerId;
	}

}