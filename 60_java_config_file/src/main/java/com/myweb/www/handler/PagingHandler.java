package com.myweb.www.handler;

import java.util.List;

import com.myweb.www.domain.BCommentVO;
import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter // 현재 구조에서는 필요치 않음
public class PagingHandler {
	private int startPage; // 현재 화면에서 보여줄 시작 페이지네이션 번호
	private int endPage; // 현재 화면에서 보여줄 마지막 페이지네이션 번호
	private boolean prev, next; // 이전, 다음 존재 여부
	
	private int totalCount; // 총 글 수
	private PagingVO pgvo; // 사용자가 입력하는 페이징, 검색 관련 정보 값 객체
	
	// db에서 불러오는게 중요하지, 객체를 같이 써도 문제 없다 db만 다르면 된다.
	// 게임서버 나누는거를 생각하면 된다. 서버 다르다고 케릭터가 다른건 아니니까
	private List<BCommentVO> cmtListB;
	private List<CommentVO> cmtList;
	
	public PagingHandler(PagingVO pgvo, int totalCount) {
		this.pgvo = pgvo;
		this.totalCount = totalCount;
		
		this.endPage 
			= (int)(Math.ceil(pgvo.getPageNo() / (pgvo.getQty()*1.0)))*pgvo.getQty();
		this.startPage = this.endPage - 9;
		
		int realEndPage = (int)(Math.ceil((totalCount*1.0) / pgvo.getQty()));
		
		if(realEndPage < this.endPage) {
			this.endPage = realEndPage;
		}
		this.prev = this.startPage > 1;
		this.next = this.endPage < realEndPage;
	}
	public PagingHandler(List<CommentVO> cmtList, PagingVO pgvo, int totalCount) {
		this(pgvo, totalCount);
		this.cmtList = cmtList;
	}
	public PagingHandler(PagingVO pgvo, int totalCount, List<BCommentVO> cmtListB) {
		this(pgvo, totalCount);
		this.cmtListB = cmtListB;
	}
}
