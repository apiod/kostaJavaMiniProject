package com.rental.user.service;

import java.sql.SQLException;

import com.rental.common.exception.PasswordUpdateException;
import com.rental.common.exception.UserNotFoundException;
import com.rental.user.dto.FindIdRequest;
import com.rental.user.dto.PasswordChangeRequest;
import com.rental.user.dto.UserLoginRequest;
import com.rental.user.dto.UserResponse;
import com.rental.user.dto.UserSignUpRequest;

public interface UserService {

	/**
	   * 로그인
	   * */
	UserResponse login(UserLoginRequest request) throws UserNotFoundException, SQLException;
	
	/**
	   * 회원 가입 
	   * */
	int signUp(UserSignUpRequest request) throws SQLException;
	
	/**
	   * ID 찾기 
	   * */
	String findId(FindIdRequest request) throws UserNotFoundException, SQLException;

	/**
	   * 비밀번호 재설정 
	   * */
	int updatePassword(PasswordChangeRequest request) throws SQLException, PasswordUpdateException;


	/**
	   * 로그아웃 
	   * */
	void logout();





}
