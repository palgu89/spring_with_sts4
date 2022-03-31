package com.myweb.www.domain;

public class CommentVO {
	private long cno;
	private long pno;
	private String writer;
	private String content;
	private String regAt;
	private String modAt;
	
	public CommentVO() {}

	// insert: pno, writer, content
	// list: cno, writer, content, modAt, pno
	// modify: cno, content
	// remove: cno
	// deleteAll: pno
	
	public long getCno() {
		return cno;
	}

	public void setCno(long cno) {
		this.cno = cno;
	}

	public long getPno() {
		return pno;
	}

	public void setPno(long pno) {
		this.pno = pno;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegAt() {
		return regAt;
	}

	public void setRegAt(String regAt) {
		this.regAt = regAt;
	}

	public String getModAt() {
		return modAt;
	}

	public void setModAt(String modAt) {
		this.modAt = modAt;
	}

	@Override
	public String toString() {
		return "CommentVO [cno=" + cno + ", pno=" + pno + ", writer=" + writer + ", content=" + content + ", regAt="
				+ regAt + ", modAt=" + modAt + "]";
	}
	
	
}
