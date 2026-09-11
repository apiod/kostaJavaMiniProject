package main.java.com.rental.post.dto;

public class PostSearch {
	//게시글 검색에 필요한 데이터 목록
	private int itemNum;		//번호검색
	private String titleKeyWord; //제목 키워드 검색
	private String contentKeyWord; //내용 키워드 검색
	private String rentDate; //대여일자 검색
	private String addr; //주소 검색
	
	public PostSearch() {}
	public PostSearch(int itemNum, String titleKeyWord, String contentKeyWord, String rentDate,
			String addr) {
		super();
		this.itemNum = itemNum;
		this.titleKeyWord = titleKeyWord;
		this.contentKeyWord = contentKeyWord;
		this.rentDate = rentDate;
		this.addr = addr;
	}
	public int getItemNum() {
		return itemNum;
	}
	public void setItemNum(int itemNum) {
		this.itemNum = itemNum;
	}
	public String getTitleKeyWord() {
		return titleKeyWord;
	}
	public void setTitleKeyWord(String titleKeyWord) {
		this.titleKeyWord = titleKeyWord;
	}
	public String getContentKeyWord() {
		return contentKeyWord;
	}
	public void setContentKeyWord(String contentKeyWord) {
		this.contentKeyWord = contentKeyWord;
	}
	public String getRentDate() {
		return rentDate;
	}
	public void setRentDate(String rentDate) {
		this.rentDate = rentDate;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	
	
}
