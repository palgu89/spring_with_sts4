package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.repository.CommentDAO;
import com.myweb.www.repository.ProductDAO;

@Service
public class CommentServiceImpl implements CommentService {
	private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

	@Inject
	private CommentDAO cdao;
	
	@Inject
	private ProductDAO pdao;	// comment의 총 개수를 count하기 위해 선언
	
	@Override
	public int register(CommentVO cvo) {
		int isUp = cdao.insert(cvo);
		if(isUp > 0) {
			isUp = pdao.updateCQ(cvo.getPno());
		}
		return isUp;
		/*
		 * cdao.insert(cvo); return pdao.updateCQ(cvo.getPno()); 될걸 알기 때문에 이렇게 썼지만, 원래는 위처럼 해야 됨
		 */
	}

	@Override
	public List<CommentVO> getList(long pno) {
		return cdao.selectList(pno);
	}

	@Override
	public int modify(CommentVO cvo) {
		return cdao.update(cvo);
	}

	@Override
	public int remove(long cno) {
		long pno = cdao.selectOne(cno);
		int isUp = cdao.deleteOne(cno);
		if(isUp > 0) {
			isUp = pdao.updateDWCQ(pno);
		}
		return isUp;
	}

}
