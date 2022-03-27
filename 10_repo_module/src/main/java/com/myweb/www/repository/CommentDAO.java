package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.CommentVO;

public interface CommentDAO {
	int insert(CommentVO cvo);
	List<CommentVO> selectList(long pno);
	int update(CommentVO cvo);
	int delete(long cno);
	int deleteAll(long pno);
}
