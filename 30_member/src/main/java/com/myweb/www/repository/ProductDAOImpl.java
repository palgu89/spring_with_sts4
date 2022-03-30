package com.myweb.www.repository;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.myweb.www.domain.ProductVO;

@Repository
public class ProductDAOImpl implements ProductDAO {
	private static Logger logger = LoggerFactory.getLogger(ProductDAOImpl.class);
	private final String NS = "ProductMapper.";
	
	@Inject
	private SqlSession sql;
	
	@Override
	public int insert(ProductVO pvo) {
		return sql.insert(NS+"reg", pvo);
	}

	@Override
	public List<ProductVO> selectList() {
		return sql.selectList(NS+"list");
	}

	@Override
	public ProductVO selectOne(long pno) {
		return sql.selectOne(NS+"detail", pno);
	}

	@Override
	public int update(ProductVO pvo) {
		return sql.update(NS+"mod", pvo);
	}
	
	@Override
	public int updateRC(long pno) {
		return sql.update(NS+"upRC", pno);
	}

	@Override
	public int delete(long pno) {
		return sql.delete(NS+"del", pno);
	}

	@Override
	public int updateCQ(long pno) {
		return sql.update(NS+"upCQ", pno);
	}

	@Override
	public int updateDWCQ(long pno) {
		return sql.update(NS+"downCQ", pno);
	}

}
