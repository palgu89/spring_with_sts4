package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.CommentVO;

public interface CommentDAO {
	int insert(CommentVO cvo);
	List<CommentVO> selectList(long pno);
	int update(CommentVO cvo);
	int deleteOne(long cno);
	int deleteAll(long pno);
	long selectOne(long cno);	// getPno by cno 하는 용도
}
