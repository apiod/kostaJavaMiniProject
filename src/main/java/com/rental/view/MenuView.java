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
    // 콘솔 입력을 받기 위한 Scanner 인스턴스
    private static final Scanner sc = new Scanner(System.in);

    // ItemController 가 인스턴스 메서드로 되어 있어 객체를 미리 만들어 둔다
    private static final ItemController itemController = new ItemController();

    /**
     * 프로그램 진입점 - 시작 메뉴(로그인/회원가입)부터 실행한다
     */
    public static void main(String[] args) {
        loginMenu();
    }

    /**
     * [시작 메뉴] 로그인 및 회원가입
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
                        // 로그인 성공(세션에 사용자 정보가 저장됨) 시에만 루프 탈출
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

        // 로그인한 본인의 대여 목록 중 확인이 필요한 상태(101,102,201,202,211)가 있으면 알림 출력
        checkMyNotifications();

        // 로그인 성공 후 메인 메뉴로 진입
        mainMenu();
    }

    /**
     * 로그인한 본인의 대여 목록(v_rental_info) 중 확인이 필요한 상태(101,102,201,202,211)를 조회해 알림을 출력한다.
     */
    private static void checkMyNotifications() {
        String borrowerId = Session.getInstance().getLoginUser().getId();
        String sql = "select RentalNum, ItemName, Status from Rental "
                + "where BorrowerID = ? and Status in (101, 102, 201, 202, 211)";

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
                System.out.println(" - " + rs.getString("itemName") + " (대여번호 " + rs.getInt("RentalNum") + ") : "
                        + rentalStatusText(rs.getInt("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.close(con, ps, rs);
        }
    }

    /**
     * 대여 상태 코드를 안내 문구로 변환한다.
     */
    private static String rentalStatusText(int status) {
        switch (status) {
            case 101: return "대여 승인";
            case 102: return "대여 거절";
            case 201: return "반납 승인";
            case 202: return "반납 거절";
            case 211: return "반납 확인 완료";
            default: return "상태 코드 " + status;
        }
    }

    /**
     * 아이디 찾기 입력 (UserRepositoryImpl.findId 활용)
     */
    public static void findId() {
        System.out.println("\n[아이디 찾기]");
        System.out.print("가입 시 등록한 핸드폰 번호: ");
        String phoneNo = sc.nextLine().trim();

        FindIdRequest request = new FindIdRequest(phoneNo);
        UserController.findId(request);
    }

    /**
     * 회원 가입 입력
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

        // 입력 데이터 DTO 변환 및 컨트롤러 전달
        UserSignUpRequest signup = new UserSignUpRequest(id, password, nickName, name, phoneNo);
        UserController.signUp(signup);
    }

    /**
     * 로그인 입력
     */
    public static void login() {
        System.out.println("\n[로그인]");
        System.out.print("ID: ");
        String id = sc.nextLine().trim();

        System.out.print("PW: ");
        String password = sc.nextLine().trim();

        // 입력 데이터 DTO 변환 및 컨트롤러 전달
        UserLoginRequest login = new UserLoginRequest(id, password);
        UserController.login(login);
    }

    /**
     * 비밀번호 변경 입력 (로그인 상태에서만 진입 가능)
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
     * [메인 메뉴] 로그인 성공 후 진입하는 메인 메뉴
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
                        loginMenu(); // 시작 메뉴로 복귀
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
     * [물품 관리 메뉴]
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
                        // 루프를 종료하고 mainMenu로 복귀
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
     * 새 물품 등록 입력
     */
    public static void inputItemInsert() {
        try {
            System.out.println("\n[물품 등록]");
            System.out.print("물품명: ");
            String itemName = sc.nextLine().trim();

            // 카테고리(대분류 -> 소분류) 선택
            String num2 = selectCategory();
            if (num2 == null) {
                System.out.println("카테고리 선택이 취소되어 등록을 중단합니다.");
                return;
            }

            // 입력 데이터를 Item 엔티티로 변환 후 컨트롤러 전달
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
     * 물품 등록 시 대분류 -> 소분류 순서로 카테고리를 선택받아 소분류 코드(Num2)를 반환한다.
     * 선택이 취소되거나 목록이 없으면 null을 반환한다.
     */
    private static String selectCategory() {
        // 대분류 목록 조회 ([0]=Num, [1]=Category)
        List<String[]> bigList = queryCategory("select Num, Category from BigCategory order by Num", null);
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

        // 소분류 목록 조회 ([0]=Num2, [1]=Category)
        List<String[]> smallList = queryCategory(
                "select Num2, Category from SmallCategory where Num = ? order by Num2", selectedBigNum);
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
     * 카테고리 조회 공용 헬퍼. 결과 행을 [코드, 이름] 문자열 배열 목록으로 반환한다.
     * filterValue가 null이면 파라미터 없는 조회, 아니면 첫 번째 ? 자리에 바인딩한다.
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


            // 수정 데이터를 Item 엔티티로 변환 후 컨트롤러 전달
            Item item = new Item();
            item.setItemNum(itemNo);
            item.setItemName(updateName);


            itemController.itemUpdate(item);
        } catch (NumberFormatException e) {
            System.out.println("물품 번호와 대여 가능 여부는 형식에 맞게 입력해주세요.");
        }
    }

    /**
     * 등록 물품 삭제 입력
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
     * [대여 메뉴] 물품 대여 신청
     * RentalController 에 아직 기능이 구현되어 있지 않아 목록 조회까지만 연결한다.
     */
    public static void rentItemMenu() {
        System.out.println("\n[대여 신청]");
        // 대여 가능 물품을 확인할 수 있도록 전체 목록을 먼저 보여준다
        itemController.itemSelect();
        System.out.println("(대여 신청 기능은 " + RentalController.class.getSimpleName() + " 구현 후 연결 예정입니다)");
    }

    /**
     * [반납 메뉴] 대여 중인 물품 확인 및 반납 처리
     * RentalController 에 아직 기능이 구현되어 있지 않아 안내 메시지만 출력한다.
     */
    public static void returnItemMenu() {
        System.out.println("\n[반납]");
        System.out.println("(반납 기능은 " + RentalController.class.getSimpleName() + " 구현 후 연결 예정입니다)");
    }
}
