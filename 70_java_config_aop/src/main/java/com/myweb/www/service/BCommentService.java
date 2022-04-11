package com.myweb.www.service;

import com.myweb.www.domain.BCommentVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;

public interface BCommentService {
	int register(BCommentVO cvo);
	PagingHandler getList(long bno, PagingVO pgvo);
	int modify(BCommentVO cvo);
	int remove(long cno);
}
