package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.myweb.www.domain.ProductVO;
import com.myweb.www.repository.CommentDAO;
import com.myweb.www.repository.ProductDAO;

@Service
public class ProductServiceImpl implements ProductService {
	private static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	@Inject
	private ProductDAO pdao;
	
	@Inject
	private CommentDAO cdao;	// 게시물 삭제시 comment도 다 지워야 하므로 선언
	
	@Override
	public int register(ProductVO pvo) {
		return pdao.insert(pvo);
	}

	@Override
	public List<ProductVO> getList() {
		return pdao.selectList();
	}

	@Override
	public ProductVO getDetail(long pno) {
		pdao.updateRC(pno);	// read count 증가 후 detail -> 순서 바꾸면 detail 들어간 후 read count 증가
		return pdao.selectOne(pno);
	}

	@Override
	public int modify(ProductVO pvo) {
		return pdao.update(pvo);
	}

	@Override
	public int remove(long pno) {
		cdao.deleteAll(pno);
		return pdao.delete(pno);
	}

}
