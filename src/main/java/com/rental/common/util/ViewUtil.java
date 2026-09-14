package main.java.com.rental.common.util;

public class ViewUtil {
	// 010-1234-5678 형태로 변환을 위한 메소드
	public String formatPhone(String phone) {
		phone = phone.replace("-", "");
		if (phone.matches("\\d{11}")) {
			phone = phone.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");
//            System.out.println("전화번호 : " + phoneNo);
			return phone;
		} else {
			System.out.println("올바른 휴대폰 번호를 입력해주세요.");
			return null;
		}
	}
}
