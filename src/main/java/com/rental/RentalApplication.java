package main.java.com.rental;

import main.java.com.rental.post.controller.PostController;
import main.java.com.rental.user.controller.UserController;
import main.java.com.rental.user.dto.FindIdRequest;

public class RentalApplication {
	
	public static void main(String[] args) {
		PostController pc = new PostController();
		pc.selectByTitleKeyword("태블릿");
		
		
	}

}
