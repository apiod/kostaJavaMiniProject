package main.java.com.rental.rental.enums;

public enum RentalStatus {

    REQUESTED(0),          // 대여 신청
    APPROVED(1),           // 대여 승인
    REJECTED(2),           // 대여 거절

    RENTED(100),           // 대여 중

    RETURN_REQUESTED(200), // 임차인 반납 신청
    RETURN_APPROVED(201),  // 대여자 반납 승인

    BORROWER_CONFIRMED(210), // 임차인 반납 확인
    COMPLETED(211);          // 대여자 확인까지 완료 = 최종 완료

    private final int code;

    RentalStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}