package main.java.com.rental.view;

import java.util.List;

import main.java.com.rental.item.entity.Item;
import main.java.com.rental.user.entity.User;

public class SuccessView {
	
	public static void printMessage(String message) {
		System.out.println(message);
	}
	public static void printItemList(List<Item> item) {
		item.forEach(System.out::println);
	}
	public static void printItem(Item item) {
		System.out.println(item);
	}
	public static void printUser(User user) {
		System.out.println(user);
	}
	
}
