package main.java.com.rental.post.controller;


import main.java.com.rental.post.dto.PostCreate;
import main.java.com.rental.post.dto.PostUpdate;
import main.java.com.rental.post.entity.Post;
import main.java.com.rental.post.service.PostService;
import main.java.com.rental.post.service.PostServiceImpl;
import main.java.com.rental.view.FailView;
import main.java.com.rental.view.SuccessView;

public class PostController {
	PostService ps = PostServiceImpl.getInstance();
	public void postCreate(PostCreate postCreate) {
		try {
			ps.postCreate(postCreate);
			SuccessView.printMessage("게시글이 생성되었습니다.");
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}
	
	public void postUpdate(PostUpdate postUpdate) {
		try {
			ps.postUpdate(postUpdate);
			SuccessView.printMessage("게시글이 수정되었습니다.");
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}
	
	public void postDelete(int postNum) {
		try {
			ps.postDelete(postNum);
			SuccessView.printMessage("게시글이 삭제되었습니다.");
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}
	public void selectByItemNum(int itemNum) {
		try {
			Post post = ps.selectByItemNum(itemNum);
			SuccessView.printEntity(post);
		} catch (Exception e) {
			FailView.FailMessage(e.getMessage());
		}
	}
	public void selectByTitleKeyword(String titleKeyword) {
		
	}
	public void selectByContentKeyword(String contentKeyword) {
		
	}
	public void selectByRentDate(String rentDate) {
		
	}
	public void selectByAddr(String addr) {
		
	}
}
