package main.java.com.rental.rental.dto;

import main.java.com.rental.rental.enums.RentalStatus;

public class ActionRequest  {

    private int rentalNum;
    private RentalStatus status;
    
    public ActionRequest() {}

    public ActionRequest(int rentalNum, RentalStatus status) {
        this.rentalNum = rentalNum;
        this.status = status;
    }

    public int getRentalNum() {
        return rentalNum;
    }

    public void setRentalNum(int rentalNum) {
        this.rentalNum = rentalNum;
    }

	public RentalStatus getStatus() {
		return status;
	}

	public void setStatus(RentalStatus status) {
		this.status = status;
	}
    
    
}