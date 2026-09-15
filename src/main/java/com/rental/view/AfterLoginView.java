package main.java.com.rental.view;

import java.util.Scanner;

import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.rental.controller.RentalController;
import main.java.com.rental.session.Session;

public class AfterLoginView {
	Scanner sc = new Scanner(System.in);
	RentalController rentalController = new RentalController();

	// 로그인 이후 실행될 뷰
	public AfterLoginView() {
		// 승인 대기중인 list 보여주기
		rentalController.getPendingApprovals();

	}

	/**
	 * [메인 메뉴] 로그인 성공 후 주요 서비스 진입
	 */
	public void mainMenu() {
		ItemMenuView itemMenuView = new ItemMenuView();
		RentItemMenuView rentItemMenuView = new RentItemMenuView();
		while (Session.getInstance().getLoginUser() != null) {
			System.out.flush();
			System.out.println("========================================");
			System.out.println("\t\t\t메인 메뉴");
			System.out.println("========================================");
			System.out.println(" 1. 물품 관리 (등록/조회/수정/삭제)");
			System.out.println(" 2. 게시글 관리 (등록/조회/수정/삭제)");
			System.out.println(" 3. 물품 대여 (목록 조회 및 대여 신청)");
			System.out.println(" 4. 물품 반납 및 대여 내역 관리");
			System.out.println(" 0. 로그아웃");
			System.out.println("----------------------------------------");
			System.out.print("메뉴를 선택해주세요 >> ");

			switch (sc.nextLine().trim()) {
			case "1":
				itemMenuView.itemMenu();
				break;
			case "2":
//				 TODO post
				break;
			case "3":
				rentItemMenuView.rentalMenu();
				break;
			case "4":
//				returnItemMenu();
				break;
			case "0":
				System.out.println("로그아웃 되었습니다.");
				Session.getInstance().logout();
				return;
			default:
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				break;
			}

		}
	}
}
