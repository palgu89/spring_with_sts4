package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.MemberVO;

public interface MemberDAO {
	int insert(MemberVO mvo);	// register
	MemberVO selectOne(MemberVO mvo);	// login
	List<MemberVO> selectList();	// list
	MemberVO selectOne(String email);	// detail
	int update(MemberVO mvo);	// modify
	int delete(String email);	// remove
	int update(String email);	// last_login
}
