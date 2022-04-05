package com.myweb.www.service;

import java.util.List;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

import lombok.extern.slf4j.Slf4j;

public interface BoardService {
	int register(BoardVO pvo);
	List<BoardVO> getList();
	BoardVO getDetail(long bno);
	int modify(BoardVO pvo);
	int remove(long bno);
	
	// paging
	List<BoardVO> getList(PagingVO pagingVO);
	int getTotalCount(PagingVO pagingVO);
}
