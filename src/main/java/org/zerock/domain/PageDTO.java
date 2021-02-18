package org.zerock.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {
	
	private int startPage;
	private int endPage;
	
	private boolean prev; //이전
	private boolean next; //다음
	
	private int total; //총 페이지
	
	private Criteria cri; //도메인에 있는 클래스/ 클래스가 클래스를 필드로 필드안에 있는값을 호출하려고
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		this.endPage = (int) Math.ceil((cri.getPageNum() / 10.0)) * 10;
		this.startPage = endPage - 9;
		
		int realEnd = (int) Math.ceil(total *1.0 / cri.getAmount());
		endPage = Math.min(realEnd, endPage);
		//마지막 페이지로 더이상 값이 안넘어가게 하는 코드
		
		this.prev = this.startPage > 1;
		this.next = endPage < realEnd;
		
	}
}
