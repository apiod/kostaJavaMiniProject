package main.java.com.rental.user.controller;

import java.sql.SQLException;


import main.java.com.rental.common.exception.PasswordUpdateException;
import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.user.dto.FindIdRequest;
import main.java.com.rental.user.dto.PasswordChangeRequest;
import main.java.com.rental.user.dto.UserLoginRequest;
import main.java.com.rental.user.dto.UserSignUpRequest;
import main.java.com.rental.user.service.UserService;
import main.java.com.rental.user.service.UserServiceImpl;
import main.java.com.rental.view.FailView;
import main.java.com.rental.view.SuccessView;


public class UserController {

	  private static UserService userService = new UserServiceImpl();
	  
	  /**
	    * 로그인
	    * */
	   public static void login(UserLoginRequest request) {
		   
		   try {
			   userService.login(request);
				 SuccessView.printMessage("로그인이 완료 되었습니다.");
				}catch (SQLException e) {
					e.printStackTrace();
				}catch (NotFoundException e) {
					FailView.FailMessage(e.getMessage());
				}
		   
		}
	   
	   /**
	    * 회원 가입
	    * */
	   public static void signUp(UserSignUpRequest request) {
		   
		   try {
			   userService.signUp(request);
				 SuccessView.printMessage("회원 가입이 완료 되었습니다");
				}catch (SQLException e) {
					e.printStackTrace();
				
				}
		   
	   }

	   /**
	    * 아이디 찾기 
	    * */
	   public static void findId(FindIdRequest request) {
		   
		   try {
			  String result= userService.findId(request);
				 SuccessView.printMessage("회원님의 ID = "+ result);
				}catch (SQLException e) {
					e.printStackTrace();
				
				}catch (NotFoundException e) {
					FailView.FailMessage(e.getMessage());
				}
		   
	   }
	   
	   /**
	    * 비밀 번호 바꾸기 
	    * */
	   public static void updatePassword(PasswordChangeRequest request) {
		   
		   try {
			   userService.updatePassword(request);
				 SuccessView.printMessage("비밀 번호가 변경이 되었습니다");
				}catch (SQLException e) {
					e.printStackTrace();
				
				}catch (PasswordUpdateException e) {
					FailView.FailMessage(e.getMessage());
				
				}
		   
	   }
	   
	   /**
	    * 로그아웃
	    */
	   public static void logout() {

	       userService.logout();
	       SuccessView.printMessage("로그아웃 되었습니다.");
	   }
	   
}
		   

