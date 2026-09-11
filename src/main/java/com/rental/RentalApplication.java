package main.java.com.rental;

import main.java.com.rental.post.controller.PostController;

public class RentalApplication {
	
	public static void main(String[] args) {
		
		PostController pc = new PostController();
		pc.selectByTitleKeyword("태블릿");
	}
}