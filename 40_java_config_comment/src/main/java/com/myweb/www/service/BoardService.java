package com.myweb.www.service;

import java.util.List;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

public interface BoardService {
	int register(BoardVO bvo);
	List<BoardVO> getList();
	BoardVO getDetail(long bno);
	int modify(BoardVO bvo);
	int remove(long bno);
	
	List<BoardVO> getList(PagingVO pagingVO);
	int getTotalCount(PagingVO pagingVO);
}
