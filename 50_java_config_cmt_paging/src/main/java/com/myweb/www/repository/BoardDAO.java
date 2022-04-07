package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

public interface BoardDAO {
	int insertBoard(BoardVO bvo);
	List<BoardVO> selectListBoard();
	BoardVO selectOneBoard(long bno);
	int updateBoard(BoardVO bvo);
	int deleteBoard(long bno);
	
	List<BoardVO> selectListBoardPaging(PagingVO pagingVO);
	int selectOneTotalCount(PagingVO pagingVO);
}
