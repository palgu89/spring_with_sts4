package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.security.AuthVO;
import com.myweb.www.security.MemberVO;

public interface MemberDAO {
	int insertMember(MemberVO mvo);
	
	// email을 security에서 비교하기 위해 만든 메서드
	MemberVO selectEmail(String email);

	int insertAuthInit(String email);

	List<AuthVO> selectAuths(String email);

	int updateLastLogin(String Email);
}