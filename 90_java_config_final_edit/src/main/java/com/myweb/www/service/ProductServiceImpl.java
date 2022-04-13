package com.myweb.www.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.myweb.www.domain.BFileVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.domain.ProductDTO;
import com.myweb.www.domain.ProductVO;
import com.myweb.www.repository.FileDAO;
import com.myweb.www.repository.ProductDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
	
	@Inject
	private ProductDAO pdao;
	
	@Inject
	private FileDAO fdao;
	
	@Override
	public int register(ProductDTO pdto) {
		int isUp = pdao.insertProduct(pdto.getPvo());
		if(isUp > 0 && pdto.getFList().size() > 0) {
			long pno = pdao.selectOnePno();
			for(BFileVO fvo : pdto.getFList()) {
				fvo.setBno(pno);
				isUp *= fdao.insertFile(fvo);
			}
		}
		return isUp;
	}

	@Override
	public List<ProductVO> getList(PagingVO pgvo) {
		return pdao.selectListProduct(pgvo);
	}

	@Override
	public ProductVO getDetail(long pno) {
		pdao.updateProductReadCount(pno, 1);
		return pdao.selectOneProduct(pno);
	}

	@Override
	public int modify(ProductVO pvo) {
		return pdao.updateProduct(pvo);
	}

	@Override
	public int remove(long pno) {
		return pdao.deleteProduct(pno);
	}

	@Override
	public int getTotalCount(PagingVO pgvo) {
		return pdao.selectOneTotalCount(pgvo);
	}
}
