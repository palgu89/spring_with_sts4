package com.myweb.www.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ProductVO {
	private long pno;
	private String category;
	private String pname;
	private int price;
	private String writer;
	private String description;
	private String madeBy;
	private String regAt;
	private String modAt;
	private int readCount;
	private int cmtQty;
}
