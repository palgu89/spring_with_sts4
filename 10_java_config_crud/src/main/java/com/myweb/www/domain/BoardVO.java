package com.myweb.www.domain;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BoardVO {
	private long bno;
	private String title;
	private String content;
	private String writer;
	private String regAt;
	private String modAt;
	private int readCount;
	
	// register : title, content, writer
	// list : bno, title, writer, readCount, modAt
	// detail : *
	// modify : title, content
	// remove : bno;
	
	public BoardVO() {}
	
	public long getBno() {
		return bno;
	}
	public void setBno(long bno) {
		this.bno = bno;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
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
	public int getReadCount() {
		return readCount;
	}
	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}
	public static org.slf4j.Logger getLog() {
		return log;
	}
	@Override
	public String toString() {
		return "productVO [bno=" + bno + ", title=" + title + ", content=" + content + ", writer=" + writer + ", regAt="
				+ regAt + ", modAt=" + modAt + ", readCount=" + readCount + "]";
	}
	
	
}
