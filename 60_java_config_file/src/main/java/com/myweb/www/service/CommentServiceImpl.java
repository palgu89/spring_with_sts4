package com.myweb.www.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.repository.CommentDAO;
import com.myweb.www.repository.ProductDAO;

@Service
public class CommentServiceImpl implements CommentService {
	private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Inject
	private CommentDAO cdao;
	
	@Inject
	private ProductDAO pdao;

	@Override
	public int register(CommentVO cvo) {
		int isUp = cdao.insertComment(cvo);
		if (isUp > 0) {
			isUp = pdao.updateProductCQ(cvo.getPno());
		}
		return isUp;
	}

	@Override
	public PagingHandler getList(long pno, PagingVO pgvo) {		
		return new PagingHandler(cdao.selectListComment(pno, pgvo), pgvo,
				cdao.selectOneCommentTotalCount(pno));
	}

	@Override
	public int modify(CommentVO cvo) {
		return cdao.updateComment(cvo);
	}

	@Override
	public int remove(long cno) {
		long pno = cdao.selectOneComment(cno);
		int isUp = cdao.deleteOneComment(cno);
		if(isUp > 0) {
			isUp =  pdao.updateProductDWCQ(pno);
		}		
		return isUp;
	}
}
