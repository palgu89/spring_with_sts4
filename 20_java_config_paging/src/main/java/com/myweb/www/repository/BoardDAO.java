package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;

import lombok.extern.slf4j.Slf4j;

public interface BoardDAO {
	int insertBoard(BoardVO pvo);
	List<BoardVO> selectListBoard();
	BoardVO selectOneBoard(long bno);
	int updateBoard(BoardVO pvo);
	int deleteBoard(long bno);
	
	// RootConfig의 MapperScan으로 인해 DAOImpl 없어도 됨 대신 기존 이름과 좀 다르게 저장 함
	// 다른 다오들의 메서드들과 겹치지 않기 위해서
	// 그리고 mapper에 위의 이름들을 id에 넣어줌 > auto mapping 됨
	
	// paging
	List<BoardVO> selectListBoardPaging(PagingVO pagingVO);
	int selectOneTotalCount(PagingVO pagingVO);
}
