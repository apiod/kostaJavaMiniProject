package main.java.com.rental.user.service;

import java.sql.SQLException;

import main.java.com.rental.common.exception.PasswordUpdateException;
import main.java.com.rental.common.exception.UserNotFoundException;
import main.java.com.rental.session.Session;
import main.java.com.rental.user.dto.FindIdRequest;
import main.java.com.rental.user.dto.PasswordChangeRequest;
import main.java.com.rental.user.dto.UserLoginRequest;
import main.java.com.rental.user.dto.UserResponse;
import main.java.com.rental.user.dto.UserSignUpRequest;
import main.java.com.rental.user.entity.User;
import main.java.com.rental.user.repository.UserRepository;
import main.java.com.rental.user.repository.UserRepositoryImpl;


public class UserServiceImpl implements UserService{
	
	private Session session = Session.getInstance();
	UserRepository userRepository = new UserRepositoryImpl();
	
	
	
	@Override
		public UserResponse login(UserLoginRequest request ) throws UserNotFoundException , SQLException{
			User user=userRepository.login(request);
			if(user==null) {
				throw new UserNotFoundException("회원님의 정보를 찾을 수 없습니다");
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
	public String findId(FindIdRequest request) throws SQLException, UserNotFoundException {
		String result = userRepository.findId(request);
		
		if(result==null) {
			throw new UserNotFoundException("회원님의 ID를 찾을 수 없습니다");
		}
		
		return result;
		
	}
	
	@Override
	public int updatePassword(PasswordChangeRequest request)
	         throws SQLException, PasswordUpdateException {
		int result = userRepository.updatePassword(request);
		
		if(result==0) {
			throw new PasswordUpdateException("정보를 다시 입력해 주세요");
		
		}
		return result;
	}
	@Override
	public void logout() {
	    session.logout();
	}
	
	}