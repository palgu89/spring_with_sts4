package com.myweb.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

public interface BoardDAO {
	int insertBoard(BoardVO bvo);
	// List<BoardVO> selectListBoard();
	BoardVO selectOneBoard(long bno);
	int updateBoard(BoardVO bvo);
	int deleteBoard(long bno);
	
	List<BoardVO> selectListBoardPaging(PagingVO pgvo);
	int selectOneTotalCount(PagingVO pgvo);
	long selectOneBno();
	int updateBoardFileCount(@Param("bno")long bno, @Param("cnt")int cnt);	// hashMap으로 보내든지, param으로 보내든지
	
	void updateBoardReadCount(@Param("bno")long bno, @Param("cnt")int cnt);
	void updateBoardCmtQty(@Param("bno")long bno, @Param("cnt")int cnt);
	
}