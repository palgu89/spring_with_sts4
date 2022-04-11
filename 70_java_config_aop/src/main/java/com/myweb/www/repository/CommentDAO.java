package com.myweb.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.domain.PagingVO;

public interface CommentDAO {
	int insertComment(CommentVO cvo);
	// mapper에 전달해야 하는 값이 두개이기 때문에 parameterType에 두개가 들어가야한다
	// 하지만 parameterType은 두개가 들어갈 수 없으므로
	// @Param을 해줘야 mapper가 두개를 받음 (mybatis에서 제공하는 어노테이션 이용)
	List<CommentVO> selectListComment(@Param("pno") long pno, @Param("pgvo") PagingVO pgvo);
	int updateComment(CommentVO cvo);
	int deleteOneComment(long cno);
	int deleteAllComment(long pno);
	long selectOneComment(long cno); // getPno by cno
	int selectOneCommentTotalCount(long pno);
}
