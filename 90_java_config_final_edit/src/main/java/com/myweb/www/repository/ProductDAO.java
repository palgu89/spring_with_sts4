package com.myweb.www.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.myweb.www.domain.PagingVO;
import com.myweb.www.domain.ProductVO;

public interface ProductDAO {
	int insertProduct(ProductVO pvo);
	List<ProductVO> selectListProduct(PagingVO pgvo);
	ProductVO selectOneProduct(long pno);
	int updateProduct(ProductVO pvo);
	int deleteProduct(long pno);
	int selectOneTotalCount(PagingVO pgvo);
	
	void updateProductReadCount(@Param("pno")long pno, @Param("cnt")int cnt);
	void updateProductCmtQty(@Param("pno")long pno, @Param("cnt")int cnt);
	long selectOnePno();
}
