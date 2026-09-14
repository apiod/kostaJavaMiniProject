package main.java.com.rental.view;

public class MenuView {

	public MenuView() {
		// 메뉴뷰 시작 시 접근할 LoginMenuView
		LoginMenuView lmv = new LoginMenuView();
		AfterLoginView alv = new AfterLoginView();
		while (true) {
			lmv.loginMenuView();
			alv.mainMenu();
		}
	}
}