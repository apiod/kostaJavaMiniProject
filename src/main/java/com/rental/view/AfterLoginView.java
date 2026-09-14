package main.java.com.rental.view;

import java.util.Scanner;

import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.rental.controller.RentalController;

public class AfterLoginView {
	Scanner sc = new Scanner(System.in);
	RentalController userController = new RentalController();
	//로그인 이후 실행될 뷰
	public AfterLoginView() {
		//승인 대기중인 list보여주기
		userController.getPendingApprovals();
		
	}
	
	/**
	 * [메인 메뉴] 로그인 성공 후 주요 서비스 진입
	 */
	public void mainMenu() {
		boolean status = true;
		while (status) {
			System.out.println("\n========================================");
			System.out.println("\t\t\t메인 메뉴");
			System.out.println("========================================");
			System.out.println(" 1. 물품 관리 (등록/수정/삭제)");
			System.out.println(" 2. 물품 대여 (목록 조회 및 대여 신청)");
			System.out.println(" 3. 물품 반납 및 대여 내역 관리");
			System.out.println(" 4. 비밀번호 변경");
			System.out.println(" 0. 로그아웃");
			System.out.println("----------------------------------------");
			System.out.print("메뉴를 선택해주세요 >> ");

			try {
				switch (sc.nextLine().trim()) {
				case "1":
					itemMenu();
					break;
				case "2":
					rentItemMenu();
					break;
				case "3":
					returnItemMenu();
					break;
				case "4":
					changePassWord();
					break;
				case "0":
					System.out.println("로그아웃 되었습니다.");
					status = false;
					loginMenu();
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("메뉴 번호는 숫자만 입력 가능합니다.");
			} catch (NotFoundException e) {
				System.out.println("[알림] " + e.getMessage());
			} catch (Exception e) {
				System.out.println("[시스템 오류] " + e.getMessage());
			}
		}
	}
}
