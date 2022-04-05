package com.myweb.www.handler;

import com.myweb.www.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
// getter setter가 있어야 정보는 주고 빼올수가 있으니까 선언
// 현재구조에서는 setter는 필요하지 않음 ( a link로 보내줄거라서 )
@Getter
@Setter
public class PagingHandler {
	private int startPage;	// 현재 화면에서 보여줄 가장 시작 페이지네이션(ex. 1,2,3,4,...,10 중 1) 번호
	private int endPage;	// 현재 화면에서 보여줄 가장 마지막 페이지네이션(ex. 11,12,13,14,...,20 중 20) 번호
	private boolean prev, next;	// 이전, 다음 존재 여부
	
	private int totalCount;	// 총 글 수
	private PagingVO pgvo;	// 사용자가 입력하는 페이징, 검색 관련 정보 값 객체
	
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		// 연산 ( 1 ~ 10 까지는 모두 10이 마지막 페이지가 된다. > / 10 한 후 ceil하면 무조건 1, *10하면 10이 되기 때문에 이 로직을 이용한다 )
		// 단, 1 / 10은 int / int 이므로 0.1이 아니라 0이니까 int / double을 해주기 위해 1.0을 뒤에 곱해준다. > 0.1을 얻는다
		this.endPage = (int)(Math.ceil(pgvo.getPageNo() / (pgvo.getQty()*1.0)))*pgvo.getQty();
		this.startPage = this.endPage - 9;
		
		// 이건 근데 만약 글이 172개면 18페이지가 끝이라 20개를 만족하지 못한다. 따라서 realEndPage로 다음과 같은 경우를 대비한다
		// 172 * 1.0 = 172.0 -> 172.0 / 10 = 17.2 -> ceil(17.2) = 18.0 -> int 18.0 = 18
		int realEndPage = (int)(Math.ceil(totalCount*1.0) / pgvo.getQty());
		
		if(realEndPage < this.endPage) {
			this.endPage = realEndPage;
		}
		this.prev = this.startPage > 1; 	// 10보다 크다고 설정해도 무방하긴 함
		this.next = this.endPage < realEndPage;
	}
	
}
