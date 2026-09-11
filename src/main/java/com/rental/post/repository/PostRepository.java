package main.java.com.rental.post.repository;

import java.util.List;

import main.java.com.rental.common.exception.PostException;
import main.java.com.rental.post.dto.PostCreate;
import main.java.com.rental.post.dto.PostUpdate;
import main.java.com.rental.post.entity.Post;

public interface PostRepository {
	//DB연결
	/**
	 * 게시글 생성
	 * @return 
	 */
	int postCreate(PostCreate postCreate)throws PostException;
	
	/**
	 * 게시글 수정
	 * @return 
	 */
	int postUpdate(PostUpdate postUpdate)throws PostException;
	
	
	/**
	 * 게시글 삭제
	 * @return 
	 */
	int postDelete(int postNum)throws PostException;
	
	
	///검색

	Post selectByItemNum(int itemNum)throws PostException;

    List<Post> selectByTitleKeyword(String titleKeyword)throws PostException;

    List<Post> selectByContentKeyword(String contentKeyword)throws PostException;

    List<Post> selectByRentDate(String rentDate)throws PostException;

    List<Post> selectByAddr(String addr)throws PostException;
}
