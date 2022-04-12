package com.myweb.www.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import com.myweb.www.service.MemberService;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {	// 원래면 security가 아니라 handler 쪽에 생성하자
	
	@Getter
	@Setter
	private String authEmail;
	
	@Getter
	@Setter
	private String authUrl;
	
	private RedirectStrategy rdStg = new DefaultRedirectStrategy();
	
	private RequestCache reqCache = new HttpSessionRequestCache();	// session 기억해주는 애
	
	@Inject
	private MemberService msv;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		setAuthEmail(authentication.getName());	// authentication에 성공한 계정들의 정보가 들어있음
		setAuthUrl("/board/list");
		
		boolean isUp = msv.updateLastLogin(getAuthEmail());
		
		HttpSession ses = request.getSession(false);	// 새로 생성된 세션이 아닌 기존에 존재하는 세션을 가져옴
		
		if(!isUp || ses == null) {
			return;
		}else {
			// security에서 시도한 인증 실패 기록 삭제
			ses.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);	// 인증실패 에러
		}
		SavedRequest savedReq = reqCache.getRequest(request, response);	// 기억했던 session을 요구함
		if(savedReq != null) {
			rdStg.sendRedirect(request, response, (savedReq != null ? savedReq.getRedirectUrl() : getAuthUrl()));	// detail에서 modify눌렀을 때 로그인 안했으면 로그인 하라고 함. 로그인 했으면 modify로 넘어감
			// 또는 response.sendRedirect(authUrl);
			// 또는 RequestDispatcher rdp = request.getRequestDispatcher(authUrl); rdp.forward(request, response);			
		}
		
	}

}
