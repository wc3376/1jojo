package net.crawl.db;

public class search_re_com_qual_Bean {	
	private int search_com_No;//번호
	private String com_qual;//업체지원자격
	private String com_preex;//업체우대사항
	private int com_frequncy;//빈도수
	private int No;//사용자번호
	
	public int getSearch_com_No() {
		return search_com_No;
	}
	public void setSearch_com_No(int search_com_No) {
		this.search_com_No = search_com_No;
	}
	public String getCom_qual() {
		return com_qual;
	}
	public void setCom_qual(String com_qual) {
		this.com_qual = com_qual;
	}
	public String getCom_preex() {
		return com_preex;
	}
	public void setCom_preex(String com_preex) {
		this.com_preex = com_preex;
	}
	public int getCom_frequncy() {
		return com_frequncy;
	}
	public void setCom_frequncy(int com_frequncy) {
		this.com_frequncy = com_frequncy;
	}
	public int getNo() {
		return No;
	}
	public void setNo(int no) {
		No = no;
	}
	
	
}