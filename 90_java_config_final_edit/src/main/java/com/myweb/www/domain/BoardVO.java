package com.myweb.www.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString

@Data	// edit 3. 저 위의 다섯개를 한번에 선언하는 방법 > 그러나 비추천 > 불필요한 생성자도 생성됨
public class BoardVO {
	private long bno;
	private String title;
	private String content;
	private String writer;
	private String regAt;
	private String modAt;
	private int readCount;
	
	// 파일 유무를 알기 위해 추가
	private int hasFile;
	
	private int cmtQty;
	
}
