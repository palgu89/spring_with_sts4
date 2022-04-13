package com.myweb.www.security;

import javax.inject.Inject;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.myweb.www.repository.MemberDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomAuthMemberService implements UserDetailsService {	// security가 제공하는 UserDetailsService를 상속한 내 커스텀
	
	@Inject
	private MemberDAO mdao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// jsp페이지 이메일 입력 > 시큐리티 필터체인 중 유저디테일서비스를 받아다가 내 프레임워크에다가 커스텀한거임 - loadUserByUsername이 전달 > 디스패쳐 서블릿에다가
		// 디스패쳐서블릿에 가지 않았는데 어떻게 작동하냐? > securityConfig에서 http.formLogin .....loginPgae("/member/login"); > 단, pwd는 여기로 오지 않고 다른 필터에 들어가 있음
		
		MemberVO mvo = mdao.selectEmail(username);
		mvo.setAuthList(mdao.selectAuths(username));
		return new AuthMember(mvo);	// 인증을 위한 객체
	}

}
