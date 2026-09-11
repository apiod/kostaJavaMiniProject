package main.java.com.rental.post.repository;

import java.sql.SQLException;
import java.util.List;

import main.java.com.rental.post.dto.PostCreate;
import main.java.com.rental.post.dto.PostSearch;
import main.java.com.rental.post.dto.PostUpdate;
import main.java.com.rental.post.entity.Post;

public interface PostRepository {
	//DB연결
	/**
	 * 게시글 생성
	 * @return 
	 */
	int postCreate(PostCreate postCreate);
	
	/**
	 * 게시글 수정
	 * @return 
	 */
	int postUpdate(PostUpdate postUpdate);
	
	
	/**
	 * 게시글 삭제
	 * @return 
	 */
	int postDelete(int postNum);
	
	
	///검색

	Post selectByItemNum(int itemNum);

    List<Post> selectByTitleKeyword(String titleKeyword);

    List<Post> selectByContentKeyword(String contentKeyword);

    List<Post> selectByRentDate(String rentDate);

    List<Post> selectByAddr(String addr);
}
