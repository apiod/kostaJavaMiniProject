package main.java.com.rental.rental.dto;

public class ReturnSearchRequest {

	 private int status;
    private String borrowerId;
    
    public ReturnSearchRequest() {}

    public ReturnSearchRequest(String borrowerId, int status) {
        this.borrowerId = borrowerId;
        this.status = status;
    }

    public String getBorrowerId() {
        return borrowerId;
    }

    public int getStatus() {
        return status;
    }
}