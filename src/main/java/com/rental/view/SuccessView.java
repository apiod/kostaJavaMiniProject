package main.java.com.rental.view;

import java.util.List;

import main.java.com.rental.item.entity.Item;
import main.java.com.rental.post.entity.Post;
import main.java.com.rental.rental.entity.Rental;
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
	
	public static void printPendingApprovals(List<Rental> list) {
		System.out.println("\n[알림] 확인이 필요한 대여/반납 내역 총"+list.size()+"개 있습니다.");
		list.forEach(System.out::println);
	}
	
}
