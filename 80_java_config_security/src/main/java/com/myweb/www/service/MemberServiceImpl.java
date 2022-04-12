package com.myweb.www.service;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myweb.www.repository.MemberDAO;
import com.myweb.www.security.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {
	
	@Inject
	private MemberDAO mdao;
	
	@Transactional	// db를 두번 도니까 혹시모르니 걸어두자
	@Override
	public int register(MemberVO mvo) {
		mdao.insertMember(mvo);
		return mdao.insertAuthInit(mvo.getEmail());	// email로 인증값을 처음 초기화 시키겠다
	}

	@Override
	public boolean updateLastLogin(String authEmail) {
		return mdao.updateLastLogin(authEmail) > 0 ? true : false;
	}

}
