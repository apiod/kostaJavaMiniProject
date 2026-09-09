package com.rental.user.service;

import java.sql.SQLException;

import com.rental.common.exception.PasswordUpdateException;
import com.rental.common.exception.UserNotFoundException;
import com.rental.session.Session;
import com.rental.user.dto.FindIdRequest;
import com.rental.user.dto.PasswordChangeRequest;
import com.rental.user.dto.UserLoginRequest;
import com.rental.user.dto.UserResponse;
import com.rental.user.dto.UserSignUpRequest;
import com.rental.user.entity.User;
import com.rental.user.repository.UserRepository;
import com.rental.user.repository.UserRepositoryImpl;


public class UserServiceImpl implements UserService{
	
	private Session session = Session.getInstance();
	UserRepository userRepository = new UserRepositoryImpl();
	
	
	
	@Override
		public UserResponse login(UserLoginRequest request ) throws UserNotFoundException , SQLException{
			User user=userRepository.login(request);
			if(user==null) {
				throw new UserNotFoundException("정보를 다시 확인해주세요.");
			}
			
			UserResponse response = new UserResponse(
				    user.getId(),
				    user.getNickName(),
				    user.getName(),
				    user.getPhoneNo()
				);
			
			session.setLoginUser(response);

			return response;
			
		}
	
	@Override
	public int signUp(UserSignUpRequest request) throws SQLException {
		int result = userRepository.signUp(request);
		
	    return result;
	}
	@Override
	public String findId(FindIdRequest request) throws SQLException {
		String result = userRepository.findId(request);
		
		return result;
		
	}
	
	@Override
	public int updatePassword(PasswordChangeRequest request)
	         throws SQLException, PasswordUpdateException {
		int result = userRepository.updatePassword(request);
		
		if(result==0) {
			throw new PasswordUpdateException("정보를 다시 확인해주세요.");
		
		}
		return result;
	}
	@Override
	public void logout() {
	    session.logout();
	}
	
	}