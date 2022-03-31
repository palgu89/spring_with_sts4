package com.myweb.www.repository;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.myweb.www.domain.MemberVO;

// db connecter 해야 되는데 이미 다 해놨고 우린 부르기만 하면 됨
// component로 scan해서 관리하기로 했기 때문에 @Repository를 선언해주기만 하면 된다

@Repository
public class MemberDAOImpl implements MemberDAO {
	private static Logger logger = LoggerFactory.getLogger(MemberDAOImpl.class);
	private final String NS = "MemberMapper.";
	
	@Inject
	private SqlSession sql;
	
	@Override
	public int insert(MemberVO mvo) {
		return sql.insert(NS+"reg", mvo);
	}

	@Override
	public MemberVO selectOne(MemberVO mvo) {
		return sql.selectOne(NS+"login", mvo);
	}

	@Override
	public List<MemberVO> selectList() {
		return sql.selectList(NS+"list");
	}

	@Override
	public MemberVO selectOne(String email) {
		return sql.selectOne(NS+"detail", email);
	}

	@Override
	public int update(MemberVO mvo) {
		return sql.update(NS+"mod", mvo);
	}

	@Override
	public int delete(String email) {
		return sql.delete(NS+"del", email);
	}

	@Override
	public int update(String email) {
		return sql.update(NS+"last", email);
	}

	@Override
	public int selectExist(String email) {
		return sql.selectOne(NS+"duple", email);
	}

	@Override
	public int updateGrade(MemberVO mvo) {
		return sql.update(NS+"grade", mvo);
	}

}
