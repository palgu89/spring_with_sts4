package com.myweb.www.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class AuthMember extends User {
	private static final long serialVersionUID = 1L;	// user 클래스를 받아다 구현하려는데 메모리에서 사라지면 안되니까 시리얼넘버를 준다
	private MemberVO mvo;
	
	public AuthMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	
	public AuthMember(MemberVO mvo) {	// 여기서 mvo를 받아와서 위 생성자에 던질 것이다 > 위에서 맞나 판단
		super(mvo.getEmail(), mvo.getPwd(), 
				mvo.getAuthList()
				.stream()
				.map(authVO -> new SimpleGrantedAuthority(authVO.getAuth()))
				.collect(Collectors.toList()));
		this.mvo = mvo;
	}
	

	
	
}
	
	
	/*
	 * public class AuthMember implements UserDetails { // UserDetails도 좋은데 우리가 사용
	 * 안하는 것도 뜨기 때문에 우리는 더 상위인터페이스를 사용 할 것이다.
	 * 
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	 * return null; }
	 * 
	 * @Override public String getPassword() { return null; }
	 * 
	 * @Override public String getUsername() { return null; }
	 * 
	 * @Override public boolean isAccountNonExpired() { return false; }
	 * 
	 * @Override public boolean isAccountNonLocked() { return false; }
	 * 
	 * @Override public boolean isCredentialsNonExpired() { return false; }
	 * 
	 * @Override public boolean isEnabled() { return false; }
	 * 
	 * }
	 */


