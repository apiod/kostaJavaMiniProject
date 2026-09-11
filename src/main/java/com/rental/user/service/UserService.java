package main.java.com.rental.user.service;

import java.sql.SQLException;

import main.java.com.rental.common.exception.PasswordUpdateException;
import main.java.com.rental.common.exception.NotFoundException;
import main.java.com.rental.user.dto.FindIdRequest;
import main.java.com.rental.user.dto.PasswordChangeRequest;
import main.java.com.rental.user.dto.UserLoginRequest;
import main.java.com.rental.user.dto.UserResponse;
import main.java.com.rental.user.dto.UserSignUpRequest;

public interface UserService {

	/**
	   * 로그인
	   * */
	UserResponse login(UserLoginRequest request) throws NotFoundException, SQLException;
	
	/**
	   * 회원 가입 
	   * */
	int signUp(UserSignUpRequest request) throws SQLException;
	
	/**
	   * ID 찾기 
	   * */
	String findId(FindIdRequest request) throws NotFoundException, SQLException;

	/**
	   * 비밀번호 재설정 
	   * */
	int updatePassword(PasswordChangeRequest request) throws SQLException, PasswordUpdateException;


	/**
	   * 로그아웃 
	   * */
	void logout();





}
