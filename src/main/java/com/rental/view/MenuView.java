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
import main.java.com.rental.item.controller.ItemController;
import main.java.com.rental.item.entity.Item;
import main.java.com.rental.rental.controller.RentalController;
import main.java.com.rental.session.Session;
import main.java.com.rental.user.controller.UserController;
import main.java.com.rental.user.dto.FindIdRequest;
import main.java.com.rental.user.dto.PasswordChangeRequest;
import main.java.com.rental.user.dto.UserLoginRequest;
import main.java.com.rental.user.dto.UserSignUpRequest;

public class MenuView {
    // 콘솔 입력을 처리하기 위한 Scanner 객체
    private static final Scanner sc = new Scanner(System.in);

    // 물품 컨트롤러 인스턴스 생성
    private static final ItemController itemController = new ItemController();

    /**
     * [시작 메뉴] 로그인, 회원가입, 아이디 찾기 처리
     */
    public static void loginMenu() {
        boolean status = true;
        while (status) {
            System.out.println("\n========================================");
            System.out.println("  개인 간 물품 대여 서비스");
            System.out.println("========================================");
            System.out.println(" 1. 회원가입");
            System.out.println(" 2. 로그인");
            System.out.println(" 3. 아이디 찾기");
            System.out.println(" 0. 프로그램 종료");
            System.out.println("----------------------------------------");
            System.out.print("메뉴를 선택해주세요 >> ");

            try {
                int menu = Integer.parseInt(sc.nextLine().trim());
                switch (menu) {
                    case 1:
                        userSignUp();
                        break;
                    case 2:
                        login();
                        // 세션에 로그인 사용자 정보가 저장되면 시작 메뉴 루프 종료
                        if (Session.getInstance().getLoginUser() != null) {
                            status = false;
                        }
                        break;
                    case 3:
                        findId();
                        break;
                    case 0:
                        System.out.println("프로그램을 종료합니다.");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("지원하지 않는 메뉴 번호입니다. 다시 입력해주세요.");
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

        // 로그인 성공 시 대여/반납 상태 알림 내역 출력
        checkMyNotifications();

        // 메인 메뉴 화면으로 진입
        mainMenu();
    }

    /**
     * 로그인 사용자의 대여 목록 중 확인이 필요한 상태(101, 102, 201, 202, 211) 조회
     * Rental -> Post -> Item 테이블을 조인하여 ItemName 컬럼을 정상 참조하도록 수정
     */
    private static void checkMyNotifications() {
        String borrowerId = Session.getInstance().getLoginUser().getId();

        // SQL 관계 반영: Rental(Postnum) -> Post(PostNum, ItemNum) -> Item(ItemNum, ItemName)
        String sql = "SELECT r.RentalNum, i.ItemName, r.Status "
                   + "FROM Rental r "
                   + "INNER JOIN Post p ON r.Postnum = p.PostNum "
                   + "INNER JOIN Item i ON p.ItemNum = i.ItemNum "
                   + "WHERE r.BorrowerID = ? AND r.Status IN (101, 102, 201, 202, 211)";

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, borrowerId);
            rs = ps.executeQuery();

            boolean hasNotification = false;
            while (rs.next()) {
                if (!hasNotification) {
                    System.out.println("\n[알림] 확인이 필요한 대여/반납 내역이 있습니다.");
                    hasNotification = true;
                }
                System.out.println(" - " + rs.getString("ItemName") + " (대여번호 " + rs.getInt("RentalNum") + ") : "
                        + rentalStatusText(rs.getInt("Status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // DB 연결 자원 해제
            DBManager.close(con, ps, rs);
        }
    }

    /**
     * 대여 상태 코드를 화면 표시용 명칭으로 변환
     */
    private static String rentalStatusText(int status) {
        switch (status) {
            case 101: return "대여 승인";
            case 102: return "대여 거절";
            case 201: return "반납 승인";
            case 202: return "반납 거절";
            case 211: return "반납 확인";
            default: return "상태 코드 " + status;
        }
    }

    /**
     * 아이디 찾기 요청 입력
     */
    public static void findId() {
        System.out.println("\n[아이디 찾기]");
        System.out.print("가입 시 등록한 핸드폰 번호: ");
        String phoneNo = sc.nextLine().trim();

        FindIdRequest request = new FindIdRequest(phoneNo);
        UserController.findId(request);
    }

    /**
     * 회원 가입 정보 입력
     */
    public static void userSignUp() {
        System.out.println("\n[회원가입]");
        System.out.print("ID: ");
        String id = sc.nextLine().trim();

        System.out.print("PW: ");
        String password = sc.nextLine().trim();

        System.out.print("닉네임: ");
        String nickName = sc.nextLine().trim();

        System.out.print("이름: ");
        String name = sc.nextLine().trim();

        System.out.print("핸드폰 번호: ");
        String phoneNo = sc.nextLine().trim();

        UserSignUpRequest signup = new UserSignUpRequest(id, password, nickName, name, phoneNo);
        UserController.signUp(signup);
    }

    /**
     * 로그인 계정 정보 입력
     */
    public static void login() {
        System.out.println("\n[로그인]");
        System.out.print("ID: ");
        String id = sc.nextLine().trim();

        System.out.print("PW: ");
        String password = sc.nextLine().trim();

        UserLoginRequest login = new UserLoginRequest(id, password);
        UserController.login(login);
    }

    /**
     * 로그인 계정의 비밀번호 변경 입력
     */
    public static void inputPasswordChange() {
        System.out.println("\n[비밀번호 변경]");
        String phoneNo = Session.getInstance().getLoginUser().getPhoneNo();

        System.out.print("새 비밀번호: ");
        String newPassword = sc.nextLine().trim();

        PasswordChangeRequest request = new PasswordChangeRequest(phoneNo, newPassword);
        UserController.updatePassword(request);
    }

    /**
     * [메인 메뉴] 로그인 성공 후 주요 서비스 진입
     */
    public static void mainMenu() {
        boolean status = true;
        while (status) {
            System.out.println("\n========================================");
            System.out.println("               메인 메뉴");
            System.out.println("========================================");
            System.out.println(" 1. 물품 관리 (등록/수정/삭제)");
            System.out.println(" 2. 물품 대여 (목록 조회 및 대여 신청)");
            System.out.println(" 3. 물품 반납 및 대여 내역 관리");
            System.out.println(" 4. 비밀번호 변경");
            System.out.println(" 0. 로그아웃");
            System.out.println("----------------------------------------");
            System.out.print("메뉴를 선택해주세요 >> ");

            try {
                int menu = Integer.parseInt(sc.nextLine().trim());
                switch (menu) {
                    case 1:
                        itemMenu();
                        break;
                    case 2:
                        rentItemMenu();
                        break;
                    case 3:
                        returnItemMenu();
                        break;
                    case 4:
                        inputPasswordChange();
                        break;
                    case 0:
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

    /**
     * [물품 관리 메뉴] 등록, 수정, 삭제 선택
     */
    public static void itemMenu() {
        boolean status = true;
        while (status) {
            System.out.println("\n========================================");
            System.out.println("              물품 관리 메뉴");
            System.out.println("========================================");
            System.out.println(" 1. 새 물품 등록");
            System.out.println(" 2. 등록 물품 정보 수정");
            System.out.println(" 3. 등록 물품 삭제");
            System.out.println(" 0. 상위 메뉴로 이동");
            System.out.println("----------------------------------------");
            System.out.print("메뉴를 선택해주세요 >> ");

            try {
                int menu = Integer.parseInt(sc.nextLine().trim());
                switch (menu) {
                    case 1:
                        inputItemInsert();
                        break;
                    case 2:
                        inputItemUpdate();
                        break;
                    case 3:
                        inputItemDelete();
                        break;
                    case 0:
                        status = false;
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

    /**
     * 새 물품 등록 정보 입력
     */
    public static void inputItemInsert() {
        try {
            System.out.println("\n[물품 등록]");
            System.out.print("물품명: ");
            String itemName = sc.nextLine().trim();

            // 대분류 -> 소분류 카테고리 코드(Num2) 선택
            String num2 = selectCategory();
            if (num2 == null) {
                System.out.println("카테고리 선택이 취소되어 등록을 중단합니다.");
                return;
            }

            // Item 엔티티 객체 생성 및 속성 주입
            Item item = new Item();
            item.setItemName(itemName);
            item.setNum2(num2);
            item.setStatus(true);
            item.setLenderID(Session.getInstance().getLoginUser().getId());

            itemController.itemInsert(item);
        } catch (NumberFormatException e) {
            System.out.println("숫자만 입력 가능합니다.");
        }
    }

    /**
     * 대분류 및 소분류 카테고리 선택 후 Num2 코드 반환
     */
    private static String selectCategory() {
        // 대분류 목록 조회
        List<String[]> bigList = queryCategory("SELECT Num, Category FROM BigCategory ORDER BY Num", null);
        if (bigList.isEmpty()) {
            System.out.println("등록된 대분류 카테고리가 없습니다.");
            return null;
        }

        System.out.println("\n[대분류 카테고리]");
        for (int i = 0; i < bigList.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + bigList.get(i)[1]);
        }
        System.out.print("대분류 번호를 선택하세요 >> ");
        int bigChoice = Integer.parseInt(sc.nextLine().trim());
        if (bigChoice < 1 || bigChoice > bigList.size()) {
            System.out.println("잘못된 번호입니다.");
            return null;
        }
        String selectedBigNum = bigList.get(bigChoice - 1)[0];
        String selectedBigName = bigList.get(bigChoice - 1)[1];

        // 선택한 대분류에 속한 소분류 목록 조회
        List<String[]> smallList = queryCategory(
                "SELECT Num2, Category FROM SmallCategory WHERE Num = ? ORDER BY Num2", selectedBigNum);
        if (smallList.isEmpty()) {
            System.out.println("등록된 소분류 카테고리가 없습니다.");
            return null;
        }

        System.out.println("\n[" + selectedBigName + " - 소분류 카테고리]");
        for (int i = 0; i < smallList.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + smallList.get(i)[1]);
        }
        System.out.print("소분류 번호를 선택하세요 >> ");
        int smallChoice = Integer.parseInt(sc.nextLine().trim());
        if (smallChoice < 1 || smallChoice > smallList.size()) {
            System.out.println("잘못된 번호입니다.");
            return null;
        }

        return smallList.get(smallChoice - 1)[0];
    }

    /**
     * 카테고리 조회 전용 DB 헬퍼 메서드
     */
    private static List<String[]> queryCategory(String sql, String filterValue) {
        List<String[]> list = new ArrayList<>();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBManager.getConnection();
            ps = con.prepareStatement(sql);
            if (filterValue != null) {
                ps.setString(1, filterValue);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new String[] { rs.getString(1), rs.getString(2) });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(con, ps, rs);
        }
        return list;
    }

    /**
     * 등록 물품 정보 수정 입력
     */
    public static void inputItemUpdate() {
        try {
            System.out.println("\n[물품 수정]");
            System.out.print("수정할 물품 번호: ");
            int itemNo = Integer.parseInt(sc.nextLine().trim());

            System.out.print("수정할 물품명: ");
            String updateName = sc.nextLine().trim();

            Item item = new Item();
            item.setItemNum(itemNo);
            item.setItemName(updateName);

            itemController.itemUpdate(item);
        } catch (NumberFormatException e) {
            System.out.println("물품 번호는 숫자만 입력 가능합니다.");
        }
    }

    /**
     * 물품 삭제 번호 입력
     */
    public static void inputItemDelete() {
        try {
            System.out.println("\n[물품 삭제]");
            System.out.print("삭제할 물품 번호: ");
            int itemNo = Integer.parseInt(sc.nextLine().trim());

            itemController.itemDelete(itemNo);
        } catch (NumberFormatException e) {
            System.out.println("물품 번호는 숫자만 입력 가능합니다.");
        }
    }

    /**
     * [대여 메뉴] 물품 목록 조회 및 대여 신청
     */
    public static void rentItemMenu() {
        System.out.println("\n[대여 신청]");
        itemController.itemSelect();
        System.out.println("(대여 신청 기능은 " + RentalController.class.getSimpleName() + " 구현 후 연결 예정입니다)");
    }

    /**
     * [반납 메뉴] 대여 중인 물품 반납 확인
     */
    public static void returnItemMenu() {
        System.out.println("\n[반납]");
        System.out.println("(반납 기능은 " + RentalController.class.getSimpleName() + " 구현 후 연결 예정입니다)");
    }
}