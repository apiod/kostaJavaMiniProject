package main.java.com.rental.view;

import java.util.List;

import main.java.com.rental.item.entity.Item;
import main.java.com.rental.post.entity.Post;
import main.java.com.rental.user.entity.User;

public class SuccessView {
	
	public static void printMessage(String message) {
		System.out.println(message);
	}
	
	//User, Item, Post, Rental상관없이 가능
	public static void printEntity(Object obj) {
		System.out.println(obj);
	}
	public static void printEntityList(List<?> list) {
		list.forEach(System.out::println);
	}
	
}
