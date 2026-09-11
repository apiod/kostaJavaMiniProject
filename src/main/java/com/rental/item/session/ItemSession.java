package main.java.com.rental.item.session;

import java.util.HashMap;
import java.util.Map;

public class ItemSession {
	private String itemNum;

	private Map<String, Object> attributes;

	public ItemSession() {
	}

	public ItemSession(String itemNum) {
		this.itemNum = itemNum;
		attributes = new HashMap<>();
	}

	public String getItemSession() {
		return itemNum;
	}

	public Object getAttribute(String itemNum, Object value) {
		return attributes.get(itemNum);
	}

	public void setItemSession(String itemNum) {
		this.itemNum = itemNum;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return "Session [itemNum=" + itemNum + ", attributes=" + attributes + "]" + "\n";
	}

	@Override
	public int hashCode() {
		return itemNum.hashCode();
	}

	/**
	 * 같은 객체라는 뜻은 hashCode가 같아야하고, equlas의 결과가 true여야한다.
	 * 
	 * hash코드가 다르면 무조건 다른 객체 hash코드가 같으면 같은 객체일수도, 다른 객체일수도 있다.
	 */
	@Override
	public boolean equals(Object obj) {
		ItemSession other = (ItemSession) obj;
		if (itemNum.equals(other.itemNum)) {
			return true;
		} else {
			return false;
		}
	}
}
