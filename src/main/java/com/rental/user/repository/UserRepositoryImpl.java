package main.java.com.rental.user.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.rental.user.dto.FindIdRequest;
import main.java.com.rental.user.dto.PasswordChangeRequest;
import main.java.com.rental.user.dto.UserLoginRequest;
import main.java.com.rental.user.dto.UserSignUpRequest;
import main.java.com.rental.user.entity.User;
import main.java.com.rental.common.exception.UserNotFoundException;
import main.java.com.rental.common.util.DBManager;

public class UserRepositoryImpl implements UserRepository{
	
		@Override
		public User login(UserLoginRequest request) throws SQLException {
			  User user = null;
			  Connection con=null;
			  PreparedStatement ps=null;
			  ResultSet rs=null;
			  
			  
			 try {
			   con = DBManager.getConnection();
			   ps= con.prepareStatement("select * from User where ID=? and PassWord=?");
			   ps.setString(1, request.getId());
			   ps.setString(2, request.getPassword());
			   
		        rs = ps.executeQuery(); 
		        
		        
		      if(rs.next()) {
		        	user = new User(rs.getString("ID"),rs.getString("PassWord"),rs.getString("NickName"), rs.getString("Name")
		        			, rs.getString("Phone"));
		        }
		        
	        } finally {
	        	DBManager.close(con, ps, rs);
	        }
			return user;
		}
		
	 @Override
	 public int signUp(UserSignUpRequest request) throws SQLException {
		  Connection con=null;
		  PreparedStatement ps=null;
		  int result = 0;
		   
		  
			   String sql="insert into User(ID, PassWord, NickName, Name, Phone) values (?, ?, ?, ?, ?) ";
				try {
					con = DBManager.getConnection();
					ps = con.prepareStatement(sql);
					
					ps.setString(1, request.getId());
					ps.setString(2, request.getPassword());
					ps.setString(3, request.getNickName());
					ps.setString(4, request.getName());
					ps.setString(5, request.getPhoneNo());
					
					
					result =  ps.executeUpdate();


				 } finally {
					 DBManager.close(con, ps);
				 
				 }

				return  result;
				
	}
	 
	 
	 @Override
	 public String findId(FindIdRequest  request) throws SQLException {
	
		
			  Connection con=null;
			  PreparedStatement ps=null;
			  ResultSet rs=null;
			  String result = null;
			  
			  
			 try {
			   con = DBManager.getConnection();
			   ps= con.prepareStatement("select ID from User where Phone = ?");
			   ps.setString(1, request.getPhoneNo());
			   
		        rs = ps.executeQuery(); 
		        
		        
		        if(rs.next()) {
		        	result = rs.getString("ID");
		        }
	        }finally {
	        	DBManager.close(con, ps, rs);
	        }
			return result;
		}
		  
	 
	 @Override
	 public int updatePassword(PasswordChangeRequest request)
	         throws SQLException {

	    
	         Connection con = null;
	         PreparedStatement ps = null;
	         int result = 0;

	         try {
	             con = DBManager.getConnection();

	             ps = con.prepareStatement(
	                 "update User set PassWord = ? where Phone = ?"
	             );

	             ps.setString(1, request.getNewPassword());
	             ps.setString(2, request.getPhoneNo());

	             result = ps.executeUpdate();

	         } finally {
	             DBManager.close(con, ps);
	         }
	         return result;
	 
	 }
}
