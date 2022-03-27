package com.myweb.www.domain;

public class MemberVO {
	private String email;
	private String pwd;
	private String nickName;
	private String regAt;
	private String lastLogin;
	private int grade;
	
	// Spring에서는 생성자를 만들지 않아도 된다(만들어도 되긴 한다). default 생성자만 만들어 본다
	public MemberVO() {}

	// 원래 있었어야 했던 것들 -> 이 주석들도 나중에 다 지워야 한다
	// insert: email, pwd, nickName
	// login: email, pwd
	// list: email, nickName, regAt, lastLogin, grade
	// detail: *
	// modify: pwd, nickName, grade, email
	// delete: email
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRegAt() {
		return regAt;
	}

	public void setRegAt(String regAt) {
		this.regAt = regAt;
	}

	public String getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return "MemberVO [email=" + email + ", pwd=" + pwd + ", nickName=" + nickName + ", regAt=" + regAt
				+ ", lastLogin=" + lastLogin + ", grade=" + grade + "]";
	}
}
