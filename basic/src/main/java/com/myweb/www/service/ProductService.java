package com.myweb.www.service;

import java.util.List;

import com.myweb.www.domain.PagingVO;
import com.myweb.www.domain.ProductDTO;
import com.myweb.www.domain.ProductVO;

public interface ProductService {
	int register(ProductDTO pdto);
	List<ProductVO> getList(PagingVO pgvo);
	ProductVO getDetail(long pno);
	int modify(ProductDTO pdto);
	int remove(long pno);
	int getTotalCount(PagingVO pgvo);
}
