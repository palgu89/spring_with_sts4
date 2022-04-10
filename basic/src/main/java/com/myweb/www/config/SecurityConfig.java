package com.myweb.www.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.myweb.www.security.CustomAuthMemberService;
import com.myweb.www.security.LoginFailureHandler;
import com.myweb.www.security.LoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {	// WebConfig에 등록 함
	// 암호화를 제공해주는 객체를 bean으로 등록해야 함 > 내가 가져다 쓸 수 있게
	@Bean
	public PasswordEncoder bcPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationSuccessHandler authSuccessHandler() {
		return new LoginSuccessHandler();
	}
	@Bean
	public AuthenticationFailureHandler authFailureHandler() {
		return new LoginFailureHandler();
	}
	
	// alt + shift + s > Override/implement methods > configure(httpSecurity) 는 필수
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();	// 고유 토큰을 발행해서 로그인시 토큰을 붙여 안붙은건 처리 안해주는 기능 > 개발중이니 일단 없앰
		http.authorizeRequests()
		.antMatchers("/member/list").hasRole("ADMIN")	// 특정권한 부여 > 이미 hasRole이기 때문에 ROLE_ADMIN으로 안 해도 됨 > 쓰고 싶으면 hasAuthority()를 쓰면 됨
		.antMatchers("/", "/board/list", "/board/detail", "/resources/**", "/member/register", "/member/login").permitAll()	// 여기로 시작하는 주소는 다 아무나 들어올 수 있음
		.anyRequest().authenticated();	// 주소값 요청을 분석해서 매칭시켜서 맞으면 다 허용시킴 > 디스패쳐로 보냄
		// 권한 설정은 좁은 범위(막강)부터 시작해서 넓은 범위로 순서를 정해야 한다 > hasRole하고 permitAll하기
		
		// 커스텀 로그인 페이지를 구성해서 security 적용을 하기 위한 설정
		// Controller에 주소요청 매핑도 같이 해줘야 함
		http.formLogin()
		.usernameParameter("email")
		.passwordParameter("pwd")
		.loginPage("/member/login")
		.successHandler(authSuccessHandler())
		.failureHandler(authFailureHandler());	// id의 name값을 요구.pwd의 name값을 요구.어디에서.성공했을때.실패했을때
		
		// post 방식으로 로그아웃을 진행해야 함
		http.logout().logoutUrl("/member/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessUrl("/");
	}
	
	@Bean
	public UserDetailsService customUserService() {	// 그럼 그 id, pwd를 누가 비교하냐 > 내가 비교하면 의미 없으니 해주는 걸 부름
		return new CustomAuthMemberService(); 	// default로 주는 것도 있긴한데, 거기에 맞춰서 개발 해야 하고, 원하는대로 하려면 변경을 많이 해줘야 해서 아예 구현체를 새로 만들 것이다
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// 헌재 하려고 하는 인증을 어떻게 하라고 시킬 수 있음.
		auth.userDetailsService(customUserService()).passwordEncoder(bcPasswordEncoder());	// customUserService() 얘가 할 거임. 우리가 만든 것 > AuthMember 실행
		
		// Web Thread(server)가 돈다 > SecurityConfig의 configure가 돈다 > userDetailsService를 실행시킴(보통 Provider라고 부름): Auth(인증관련)
		// + UserDetails를 가지고 가라고 하고 + passwordEncoder를 같이 가지고 들어가라고 한다
	}
	
	
}
