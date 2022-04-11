package com.myweb.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BFileVO {
	private String uuid;
	private String saveDir;
	private String fileName;
	private int fileType;
	private long bno;
	private long pno;
	private long fileSize;
	private String regAt;
}
