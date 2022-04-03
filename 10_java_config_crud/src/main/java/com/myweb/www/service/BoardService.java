package com.myweb.www.service;

import java.util.List;

import com.myweb.www.domain.BoardVO;

import lombok.extern.slf4j.Slf4j;

public interface BoardService {
	public int register(BoardVO pvo);
	public List<BoardVO> getList();
	public BoardVO getDetail(long bno);
	public int modify(BoardVO pvo);
	public int remove(long bno);
}
