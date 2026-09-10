package main.java.com.rental.user.repository;

import java.sql.SQLException;

import main.java.com.rental.user.dto.FindIdRequest;
import main.java.com.rental.user.dto.PasswordChangeRequest;
import main.java.com.rental.user.dto.UserLoginRequest;
import main.java.com.rental.user.dto.UserSignUpRequest;
import main.java.com.rental.user.entity.User;

public interface UserRepository {
	
	  /**
	   * 로그인하기
	   * */

	  User login(UserLoginRequest request) throws SQLException;
	  
	  /**
	   * 회원 가입 하기 
	   * */
	  
	  int signUp(UserSignUpRequest request) throws SQLException;

	  /**
	   * 아이디 찾기
	   * */
	  
	  String findId(FindIdRequest request) throws SQLException;
	  
	  /**
	   * 비밀번호 바꾸기
	   * */
	  
	  int updatePassword(PasswordChangeRequest request) throws SQLException;

	 
			
	}

