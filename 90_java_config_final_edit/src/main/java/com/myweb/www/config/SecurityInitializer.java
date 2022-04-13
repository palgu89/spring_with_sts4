package com.myweb.www.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecurityInitializer extends AbstractSecurityWebApplicationInitializer {
	// Servlet(WebConfig)은 Initializer가 없는 이유가 extends로 AbstractAnnotationConfigDispatcherServletInitializer를 상속했기 때문에

}
