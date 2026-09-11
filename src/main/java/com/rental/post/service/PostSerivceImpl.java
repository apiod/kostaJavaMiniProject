package main.java.com.rental.post.service;

import main.java.com.rental.post.dto.PostCreate;
import main.java.com.rental.post.repository.PostRepository;
import main.java.com.rental.post.repository.PostRepositoryImpl;

public class PostSerivceImpl implements PostService{

	PostRepository pr = new PostRepositoryImpl();
	public int postCreate(PostCreate postCreate) {
		pr.postCreate(postCreate);
		
		return 0;
	}
	
}
