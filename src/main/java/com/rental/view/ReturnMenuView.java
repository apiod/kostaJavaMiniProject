package main.java.com.rental.view;

import java.util.List;
import java.util.Scanner;

import main.java.com.rental.rental.controller.RentalController;
import main.java.com.rental.rental.entity.Rental;

public class ReturnMenuView {

	Scanner sc = new Scanner(System.in);
	RentalController rentalController = new RentalController();

	public void returnMenuView() {
		System.out.flush();
		while (true) {
			System.out.println("=================================");
			System.out.println("       물품 반납 내역 관리");
			System.out.println("=================================");
			System.out.println("1. 반납 신청 (내가 빌린 물품)");
			System.out.println("2. 반납 신청 승인 (내가 빌려준 물품)");
			System.out.println("3. 반납 완료 (임차인)");
			System.out.println("4. 반납 확인 및 평가 (대여자 최종 확인)");
			System.out.println("0. 메인 메뉴로 이동");
			System.out.println("=================================");
			System.out.print("메뉴 선택 : ");
			switch (sc.nextLine().trim()) {
			case "1":
				returnRequest();
				break;

			case "2":
				approveReturn();
				break;

			case "3":
				confirmReturn();
				break;

			case "4":
				confirmReturnFinal();
				break;

			case "0":
				System.out.flush();
				return;

			default:
				System.out.println("잘못된 메뉴를 선택했습니다.");
			}
		}
	}

	/**
	 * 6. 반납 신청 내가 빌린 물품의 대여 번호를 입력하여 반납 신청
	 */
	private void returnRequest() {

		System.out.flush();
		System.out.println("========== 반납 신청 ==========");

		List<Rental> list = rentalController.selectByStatus(101);
		System.out.print("반납할 대여 번호를 입력하세요 (취소: 0) : ");

		String input = sc.nextLine().trim();

		if ("0".equals(input)) {
			return;
		}

		try {
			int rentalNum = Integer.parseInt(input);

			rentalController.requestReturn(rentalNum);

			System.out.println("현재 반납 신청 기능은 Controller에 메소드가 없습니다.");

		} catch (NumberFormatException e) {

			System.out.println("대여 번호는 숫자로 입력해주세요.");
		}
	}

	/**
	 * 7. 반납 신청 승인 내가 빌려준 물품의 반납 신청을 승인
	 */
	private void approveReturn() {

		System.out.println();
		System.out.println("====== 반납 신청 승인 ======");

		// 대여자가 받은 반납 요청 목록 조회
		rentalController.selectByStatus(200);

		System.out.print("승인할 대여 번호를 입력하세요 (0. 취소) : ");

		String input = sc.nextLine();

		if ("0".equals(input)) {
			return;
		}

		try {

			int rentalNum = Integer.parseInt(input);

			// TODO
			// 반납 승인 메소드가 RentalController에 추가되어야 함
			//
			// rc.approveReturn(rentalNum);

			System.out.println("현재 반납 승인 기능은 Controller에 메소드가 없습니다.");

		} catch (NumberFormatException e) {

			System.out.println("대여 번호는 숫자로 입력해주세요.");
		}
	}

	/**
	 * 8. 반납 완료 임차인이 실제 물품을 반납한 후 반납 완료 처리
	 */
	private void confirmReturn() {

		System.out.println();
		System.out.println("========== 반납 완료 ==========");

		System.out.print("반납 완료할 대여 번호를 입력하세요 (0. 취소) : ");

		String input = sc.nextLine();

		if ("0".equals(input)) {
			return;
		}

		try {

			int rentalNum = Integer.parseInt(input);

			rentalController.confirmReturn(rentalNum);

		} catch (NumberFormatException e) {

			System.out.println("대여 번호는 숫자로 입력해주세요.");
		}
	}

	/**
	 * 9. 반납 확인 및 평가 대여자가 최종적으로 반납을 확인
	 */
	private void confirmReturnFinal() {

		System.out.println();
		System.out.println("====== 반납 확인 및 평가 ======");

		// TODO
		// 대여자의 반납 확인 대상 조회
		//
		// rc.selectByStatus(210);

		System.out.print("확인할 대여 번호를 입력하세요 (0. 취소) : ");

		String input = sc.nextLine();

		if ("0".equals(input)) {
			return;
		}

		try {

			int rentalNum = Integer.parseInt(input);

			// TODO
			// 최종 반납 확인 메소드가 RentalController에 추가되어야 함
			//
			// rc.finalConfirmReturn(rentalNum);

			System.out.println("현재 최종 반납 확인 기능은 Controller에 메소드가 없습니다.");

		} catch (NumberFormatException e) {

			System.out.println("대여 번호는 숫자로 입력해주세요.");
		}
	}
}