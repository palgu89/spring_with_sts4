package com.myweb.www.handler;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import com.myweb.www.domain.BCommentVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Aspect
public class LogAdvice {
	// 어떠한 대상을 실행하기 전 또는 후에 실행하라
	
	@Before("execution(* com.myweb.www.service.BCommentService*.*(..))")	// * : root 밑으로 있는 모든 ~, *.* : BCommentService아래 모든클래스, (..) : 모든 메서드()
	public void commentLogBefore() {
		log.info(">>>>>>>>>>>>>>>>> Comment Log Strart >>>>>>>>>>>>>>>>>");	// 실제론 내가 넣을 로직을 넣으면 된다
	}
	
	@Before("execution(* com.myweb.www.service.BCommentService*.register(com.myweb.www.domain.BCommentVO)) && args(cvo)")	// int register(BCommentVO cvo);
	public void commentLogBeforeRegister(BCommentVO cvo) {
		log.info(">>>>>>>>>>>>>>>>> {}", cvo);
	}
	
	@After("execution(* com.myweb.www.service.BCommentService*.*(..))")
	public void commentLogAfter() {
		log.info(">>>>>>>>>>>>>>>>> Comment Log Finish >>>>>>>>>>>>>>>>>");
	}
}
