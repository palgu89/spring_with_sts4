package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.repository.BoardDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO bdao;
	
	@Override
	public int register(BoardVO bvo) {
		return bdao.insertBoard(bvo);
	}

	@Override
	public List<BoardVO> getList() {
		return bdao.selectListBoard();
	}
	
	@Override
	public BoardVO getDetail(long bno) {
		return bdao.selectOneBoard(bno);
	}

	@Override
	public int modify(BoardVO bvo) {
		return bdao.updateBoard(bvo);
	}

	@Override
	public int remove(long bno) {
		return bdao.deleteBoard(bno);
	}
}
