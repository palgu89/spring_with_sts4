package com.myweb.www.service;

import java.util.List;

import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

public interface BoardService {
	// 파일을 위해 변경
	// int register(BoardVO bvo);
	int register(BoardDTO bdto);
	// List<BoardVO> getList();
	// BoardVO getDetail(long bno);
	BoardDTO getDetail(long bno);
	// int modify(BoardVO bvo);
	int modify(BoardDTO boardDTO);
	int remove(long bno);
	
	List<BoardVO> getList(PagingVO pgvo);
	int getTotalCount(PagingVO pgvo);
	
	int removeFile(String uuid);
}
