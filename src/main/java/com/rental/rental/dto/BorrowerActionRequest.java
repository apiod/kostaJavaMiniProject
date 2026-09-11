package main.java.com.rental.rental.dto;

public class ReturnRequest {

    private int rentalNum;

    public ReturnRequest(int rentalNum) {
        this.rentalNum = rentalNum;
    }

    public int getRentalNum() {
        return rentalNum;
    }

    public void setRentalNum(int rentalNum) {
        this.rentalNum = rentalNum;
    }
}