package main.java.com.rental.rental.enums;

public enum RentalStatus {	
	REQUESTED(100),		// 대여 신청
    APPROVED(101),		// 대여 승인
    REJECTED(102);		// 대여 거절
    
	private int status;
	
	RentalStatus(int status){
		this.status = status;
	}
	
	public int getStatus() {
		return status;
	}
}
