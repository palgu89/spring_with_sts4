package com.myweb.www.repository;

import java.util.List;

import com.myweb.www.domain.PagingVO;
import com.myweb.www.domain.ProductVO;

public interface ProductDAO {
	int insertProduct(ProductVO pvo);
	List<ProductVO> selectListProduct(PagingVO pgvo);
	ProductVO selectOneProduct(long pno);
	int updateProductRC(long pno);
	int updateProduct(ProductVO pvo);
	int deleteProduct(long pno);
	int updateProductCQ(long pno);
	int updateProductDWCQ(long pno);
	int selectOneTotalCount(PagingVO pgvo);
}
