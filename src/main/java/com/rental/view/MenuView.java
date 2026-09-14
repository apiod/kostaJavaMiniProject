package main.java.com.rental.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.common.util.DBManager;
import main.java.com.rental.common.util.ViewUtil;
import main.java.com.rental.item.controller.ItemController;
import main.java.com.rental.item.entity.Item;
import main.java.com.rental.rental.controller.RentalController;
import main.java.com.rental.rental.dto.RentalCreateRequest;
import main.java.com.rental.rental.enums.RentalStatus;
import main.java.com.rental.session.Session;
import main.java.com.rental.user.controller.UserController;
import main.java.com.rental.user.dto.FindIdRequest;
import main.java.com.rental.user.dto.PasswordChangeRequest;
import main.java.com.rental.user.dto.UserLoginRequest;
import main.java.com.rental.user.dto.UserSignUpRequest;

public class MenuView {
	// 콘솔 입력을 처리하기 위한 Scanner 객체
	Scanner sc = new Scanner(System.in);
	// 물품 컨트롤러 인스턴스 생성 (Controller - Service 계층 연결 진입점)
	ItemController itemController = new ItemController();
	// 대여 컨트롤러 인스턴스 생성
	RentalController rentalController = new RentalController();
	
	/**
	 * 
	 */
	public MenuView() {
		//메뉴뷰 시작시 접근할 LoginMenuView
		// TODO 로그아웃하면 다시 loginMenuView로 돌아가야함.
		new LoginMenuView();
		new AfterLoginView();

	}

	

	/**
	 * 로그인한 사용자가 등록한 물품 목록 조회 및 출력
	 */
	private void printMyItemList() {
		// MenuView(View) -> ItemController(Controller) -> ItemService(Service) ->
		// ItemRepository(Repository)
		itemController.itemSelect();
	}

	/**
	 * [대여 메뉴] 물품 목록 조회 및 대여 신청
	 */
	public static void rentItemMenu() {
		System.out.println("\n[물품 대여 - 목록 조회 및 대여 신청]");

		String userId = Session.getInstance().getLoginUser().getId();
		List<Integer> availablePostNums = printAvailablePostList(userId);
		if (availablePostNums.isEmpty()) {
			return;
		}

		System.out.print("대여 신청할 게시글 번호(PostNum) 입력 (취소: 0): ");
		try {
			int postNum = Integer.parseInt(sc.nextLine().trim());
			if (postNum == 0) {
				return;
			}
			if (!availablePostNums.contains(postNum)) {
				System.out.println("목록에 없는 게시글 번호입니다.");
				return;
			}

			RentalCreateRequest request = new RentalCreateRequest(postNum, RentalStatus.REQUESTED, userId);
			rentalController.rentalCreate(request);
		} catch (NumberFormatException e) {
			System.out.println("게시글 번호는 숫자만 입력 가능합니다.");
		}
	}

	/**
	 * 대여 가능한(Status = true) 물품의 게시글 목록 조회 및 출력 (본인이 등록한 물품은 제외)
	 */
	private static List<Integer> printAvailablePostList(String userId) {
		String sql = "SELECT p.PostNum, p.Title, p.RentDate, p.ReturnDate, p.Addr, i.ItemName " + "FROM Post p "
				+ "INNER JOIN Item i ON p.ItemNum = i.ItemNum " + "WHERE i.Status = true AND i.LenderID <> ? "
				+ "ORDER BY p.PostNum";

		List<Integer> postNums = new ArrayList<>();
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, userId);
			rs = ps.executeQuery();

			while (rs.next()) {
				int postNum = rs.getInt("PostNum");
				postNums.add(postNum);
				System.out.println(" - 게시글번호: " + postNum + " | 물품명: " + rs.getString("ItemName") + " | 제목: "
						+ rs.getString("Title") + " | 대여기간: " + rs.getString("RentDate") + " ~ "
						+ rs.getString("ReturnDate") + " | 위치: " + rs.getString("Addr"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, ps, rs);
		}

		if (postNums.isEmpty()) {
			System.out.println("현재 대여 가능한 물품이 없습니다.");
		}
		return postNums;
	}

	/**
	 * [반납 및 대여 내역 관리 메뉴]
	 */
	public static void returnItemMenu() {
		boolean status = true;
		String userId = Session.getInstance().getLoginUser().getId();

		while (status) {
			System.out.println("\n========================================");
			System.out.println("          반납 및 대여 내역 관리");
			System.out.println("========================================");
			System.out.println(" 1. 대여 신청 현황 조회");
			System.out.println(" 2. 대여 신청 승인");
			System.out.println(" 3. 대여 신청 거절");
			System.out.println(" 4. 대여 시작 처리 (물품 인도 확인)");
			System.out.println(" 5. 대여 중인 물품 목록 조회");
			System.out.println(" 6. 반납 신청 (내가 빌린 물품)");
			System.out.println(" 7. 반납 신청 승인 (내가 빌려준 물품)");
			System.out.println(" 8. 반납 확인 (임차인)");
			System.out.println(" 9. 반납 완료 확인 (대여자 최종 확인)");
			System.out.println("10. 전체 대여/반납 내역 조회");
			System.out.println(" 0. 상위 메뉴로 이동");
			System.out.println("----------------------------------------");
			System.out.print("메뉴를 선택해주세요 >> ");

			try {
				int menu = Integer.parseInt(sc.nextLine().trim());
				switch (menu) {
				case 1:
					printRentalListByStatus(userId, 100);
					break;
				case 2:
					if (!printRentalListByStatus(userId, 100)) {
						break;
					}
					System.out.print("승인할 대여번호(RentalNum): ");
					int approveRentalNum = Integer.parseInt(sc.nextLine().trim());
					System.out.print("해당 게시글 번호(PostNum): ");
					int approvePostNum = Integer.parseInt(sc.nextLine().trim());
					rentalController.approveRental(approveRentalNum, approvePostNum);
					break;
				case 3:
					if (!printRentalListByStatus(userId, 100)) {
						break;
					}
					System.out.print("거절할 대여번호(RentalNum): ");
					int rejectRentalNum = Integer.parseInt(sc.nextLine().trim());
					rentalController.rejectRental(rejectRentalNum);
					break;
				case 4:
					if (!printRentalListByStatus(userId, 101)) {
						break;
					}
					System.out.print("대여를 시작할 대여번호(RentalNum): ");
					int startRentalNum = Integer.parseInt(sc.nextLine().trim());
					updateRentalStatus(startRentalNum, 101, 110, "대여가 시작되었습니다.");
					break;
				case 5:
					printRentalListByStatus(userId, 110);
					break;
				case 6:
					if (!printRentalListByStatus(userId, 110)) {
						break;
					}
					System.out.print("반납 신청할 대여번호(RentalNum): ");
					int requestReturnNum = Integer.parseInt(sc.nextLine().trim());
					updateRentalStatus(requestReturnNum, 110, 200, "반납 신청이 완료되었습니다.");
					break;
				case 7:
					if (!printRentalListByStatus(userId, 200)) {
						break;
					}
					System.out.print("반납을 승인할 대여번호(RentalNum): ");
					int approveReturnNum = Integer.parseInt(sc.nextLine().trim());
					updateRentalStatus(approveReturnNum, 200, 201, "반납 신청을 승인했습니다.");
					break;
				case 8:
					if (!printRentalListByStatus(userId, 201)) {
						break;
					}
					System.out.print("반납을 확인할 대여번호(RentalNum): ");
					int confirmBorrowerNum = Integer.parseInt(sc.nextLine().trim());
					updateRentalStatus(confirmBorrowerNum, 201, 210, "반납 확인이 완료되었습니다.");
					break;
				case 9:
					if (!printRentalListByStatus(userId, 210)) {
						break;
					}
					System.out.print("반납 완료를 확인할 대여번호(RentalNum): ");
					int confirmReturnNum = Integer.parseInt(sc.nextLine().trim());
					rentalController.confirmReturn(confirmReturnNum);
					break;
				case 10:
					printAllMyRentalList(userId);
					break;
				case 0:
					status = false;
					break;
				default:
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("번호는 숫자만 입력 가능합니다.");
			} catch (NotFoundException e) {
				System.out.println("[알림] " + e.getMessage());
			} catch (Exception e) {
				System.out.println("[시스템 오류] " + e.getMessage());
			}
		}
	}

	/**
	 * 특정 상태(status)의 대여 내역 중 로그인 사용자가 임차인 또는 대여자로 관련된 건을 조회
	 */
	private static boolean printRentalListByStatus(String userId, int statusCode) {
		String sql = "SELECT r.RentalNum, r.BorrowerID, r.Status, r.Postnum AS PostNum, i.ItemName, i.LenderID "
				+ "FROM Rental r " + "INNER JOIN Post p ON r.Postnum = p.PostNum "
				+ "INNER JOIN Item i ON p.ItemNum = i.ItemNum "
				+ "WHERE r.Status = ? AND (r.BorrowerID = ? OR i.LenderID = ?) " + "ORDER BY r.RentalNum";

		return printRentalQuery(sql, userId, statusCode);
	}

	/**
	 * 로그인 사용자가 임차인 또는 대여자로 관련된 전체 대여/반납 내역 조회
	 */
	private static boolean printAllMyRentalList(String userId) {
		String sql = "SELECT r.RentalNum, r.BorrowerID, r.Status, r.Postnum AS PostNum, i.ItemName, i.LenderID "
				+ "FROM Rental r " + "INNER JOIN Post p ON r.Postnum = p.PostNum "
				+ "INNER JOIN Item i ON p.ItemNum = i.ItemNum " + "WHERE r.BorrowerID = ? OR i.LenderID = ? "
				+ "ORDER BY r.RentalNum DESC";

		return printRentalQuery(sql, userId, null);
	}

	/**
	 * 대여 내역 조회 쿼리 실행 및 출력 공통 처리 statusCode가 null이면 상태 필터 없이 전체 내역을 조회 반환값: 조회된 내역이
	 * 하나라도 있으면 true, 없으면 false
	 */
	private static boolean printRentalQuery(String sql, String userId, Integer statusCode) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		boolean found = false;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			int idx = 1;
			if (statusCode != null) {
				ps.setInt(idx++, statusCode);
			}
			ps.setString(idx++, userId);
			ps.setString(idx++, userId);
			rs = ps.executeQuery();

			while (rs.next()) {
				found = true;
				String role = userId.equals(rs.getString("LenderID")) ? "대여자" : "임차인";
				System.out.println(" - 대여번호: " + rs.getInt("RentalNum") + " | 물품명: " + rs.getString("ItemName")
						+ " | 게시글번호: " + rs.getInt("PostNum") + " | 상태: " + rentalStatusLabel(rs.getInt("Status"))
						+ " | 내 역할: " + role);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, ps, rs);
		}

		if (!found) {
			System.out.println("해당 내역이 없습니다.");
		}
		return found;
	}

	/**
	 * 대여 상태 코드를 화면 표시용 명칭으로 변환 (Rental 관리 메뉴 전용, 전체 코드 포함)
	 */
	private static String rentalStatusLabel(int status) {
		switch (status) {
		case 100:
			return "대여 신청";
		case 101:
			return "대여 승인";
		case 102:
			return "대여 거절";
		case 110:
			return "대여중";
		case 200:
			return "반납 신청";
		case 201:
			return "반납 승인";
		case 210:
			return "임차인 반납확인";
		case 211:
			return "반납 완료";
		default:
			return "상태 코드 " + status;
		}
	}

	/**
	 * 대여 상태를 fromStatus일 때만 toStatus로 변경 (RentalRepository와 동일한 가드 조건 방식)
	 */
	private static void updateRentalStatus(int rentalNum, int fromStatus, int toStatus, String successMessage) {
		String sql = "UPDATE Rental SET Status = ? WHERE RentalNum = ? AND Status = ?";

		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBManager.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, toStatus);
			ps.setInt(2, rentalNum);
			ps.setInt(3, fromStatus);

			int result = ps.executeUpdate();
			if (result > 0) {
				System.out.println(successMessage);
			} else {
				System.out.println("처리할 수 없습니다. 대여번호와 현재 상태를 확인해주세요.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("[시스템 오류] 처리 중 문제가 발생했습니다.");
		} finally {
			DBManager.close(con, ps);
		}
	}
}