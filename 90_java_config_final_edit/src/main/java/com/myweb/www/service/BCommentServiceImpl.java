package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweb.www.domain.BCommentVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.repository.BCommentDAO;
import com.myweb.www.repository.BoardDAO;
import com.myweb.www.repository.ProductDAO;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class BCommentServiceImpl implements BCommentService {
	
//	@Inject
//	private BCommentDAO cdao;
//	
//	@Inject
//	private BoardDAO bdao;
	private final BCommentDAO cdao;	// edit 5.
	private final BoardDAO bdao;	// edit 6.
	
	
	@Transactional
	@Override
	public int register(BCommentVO cvo) {
		int isUp = cdao.insertBComment(cvo);
		bdao.updateBoardCmtQty(cvo.getBno(), 1);
		return isUp;
	}

	@Override
	public PagingHandler getList(long bno, PagingVO pgvo) {
		int totalCount = cdao.selectOneBCommentTotalCount(bno);
		List<BCommentVO> list = cdao.selectListBComment(bno, pgvo);
		PagingHandler phd = new PagingHandler(pgvo, totalCount, list);
		return phd; 
	}

	@Override
	public int modify(BCommentVO bcvo) {
		return cdao.updateBComment(bcvo);
	}

	@Override
	public int remove(long cno) {
		long bno = cdao.selectOneBno(cno);	// 일부러 만드는 것임. 프론트 쪽에서 가져오는게 맞음
		int isUp = cdao.deleteOneBComment(cno);	// 순서에 유의
		bdao.updateBoardCmtQty(bno, -1);
		return isUp;
	}
}
