package com.myweb.www.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Getter
@Setter
public class LoginFailureHandler implements AuthenticationFailureHandler {
	private String authEmail;
	private String errorMessage;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		setAuthEmail(request.getParameter("email"));	// 얘는 인증이 되어있는게 아니기 때문에 security 인증객체로 들어가는게 아니고 request값에 남아 있다 > request.getParameter
		
		if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
			// BadCredentialsException : 이메일이나 비밀번호 틀리면 발생
			// InternalAuthenticationServiceException : 내부적으로 인증에 관련된 허가를 안한다거나(비밀번호 실패 횟수 등) 할 때 발생
			
			setErrorMessage(exception.getMessage().toString());	// 우리는 그냥 에러메세지만 띄울 에정
		}
		log.debug(">>> errorMessage: {}", errorMessage);
		request.setAttribute("email", getAuthEmail());
		request.setAttribute("errMsg", getErrorMessage());
		request.getRequestDispatcher("/member/login?error").forward(request, response);
	}

}
