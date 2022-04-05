package com.myweb.www.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class PagingVO {
	private int pageNo;	// 현재 화면의 출력된 페이지네이션 번호 ( 1페이지, 2페이지 등)
	private int qty; 	// 한 페이지당 보여줄 페이지네이션 번호 개수(페이지 10페이지), 한 페이지에 보여줄 게시글 수 (글 10개) > 쉽게하려고 동기화
	
	public PagingVO() {
		this(1, 10);	// 1번 페이지에 10개 보여달라
	}
	
	public PagingVO(int pageNo, int qty) {
		this.pageNo = pageNo;
		this.qty = qty;
	}
	
	public int getPageStart() {
		// sql의 limit은 10번째 글부터 10개를 보고 싶으면 limit 9, 10이라고 해줘야 하기 때문에 선택한 페이지 번호에서 -1을 해줘야 원하는 페이지가 검색된다.
		// 단, Oracle은 다른 로직임 ( 지금건 MySQL, Maria 전용 )
		return (this.pageNo - 1) * qty;
	}
}
