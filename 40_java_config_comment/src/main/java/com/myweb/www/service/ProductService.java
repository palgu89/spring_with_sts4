package com.myweb.www.service;

import java.util.List;

import com.myweb.www.domain.PagingVO;
import com.myweb.www.domain.ProductVO;

public interface ProductService {
	int register(ProductVO pvo);
	List<ProductVO> getList(PagingVO pgvo);
	ProductVO getDetail(long pno);
	int modify(ProductVO pvo);
	int remove(long pno);
	int getTotalCount(PagingVO pgvo);
}
