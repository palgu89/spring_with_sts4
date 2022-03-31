package com.myweb.www.repository;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.myweb.www.domain.CommentVO;

@Repository
public class CommentDAOImpl implements CommentDAO {
	private static Logger logger = LoggerFactory.getLogger(CommentDAOImpl.class);
	private final String NS = "CommentMapper.";
	
	@Inject
	private SqlSession sql;
	
	@Override
	public int insert(CommentVO cvo) {
		return sql.insert(NS+"reg", cvo);
	}

	@Override
	public List<CommentVO> selectList(long pno) {
		return sql.selectList(NS+"list", pno);
	}

	@Override
	public int update(CommentVO cvo) {
		return sql.update(NS+"mod", cvo);
	}

	@Override
	public int deleteOne(long cno) {
		return sql.delete(NS+"del", cno);
	}

	@Override
	public int deleteAll(long pno) {
		return sql.delete(NS+"delAll", pno);
	}

	@Override
	public long selectOne(long cno) {
		return sql.selectOne(NS+"getPno", cno);
	}

}
