package com.myweb.www.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	// Servlet(WebConfig)은 Initializer가 없는 이유가 extends로 AbstractAnnotationConfigDispatcherServletInitializer를 상속했기 때문에
	
	// 얘 기능은 모든 페이지에 security 걸어서 로그인부터 하고 사용 가능하게 만드는 기능
	// SecurityConfig에 허용가능한 주소체계를 설정해주면 로그인 없이 사용 가능하다

}
