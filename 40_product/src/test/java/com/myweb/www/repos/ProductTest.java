package com.myweb.www.repos;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myweb.www.domain.ProductVO;
import com.myweb.www.repository.CommentDAO;
import com.myweb.www.repository.ProductDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class ProductTest {
	private static Logger logger = LoggerFactory.getLogger(ProductTest.class);

	@Inject
	private ProductDAO pdao;
	
	@Inject
	private CommentDAO cdao;
	
	@Test
	public void insertProductDummies() throws Exception{
		for(int i = 0; i < 512; i++) {			
			ProductVO pvo = new ProductVO();
			pvo.setCategory("A" + i);
			pvo.setPname("this is Test Product Name of " + i);
			pvo.setPrice((int)(Math.random()*50000)+50000);
			pvo.setWriter("tester" + (int)(Math.random()*100) + 1);
			pvo.setDescription("This is Test Product Description of " + i);
			pvo.setMadeBy("Tset Made By " + i);
			pdao.insert(pvo);
		}
	}
	
	@Test
	public void getListProduct() throws Exception{
		List<ProductVO> list = pdao.selectList();
	}
	
	@Test
	public void getDetailProduct() throws Exception{
		ProductVO pvo = pdao.selectOne(100L);
	}
	
	@Test
	public void modifyProduct() throws Exception{
		ProductVO pvo = new ProductVO();
		pvo.setCategory("A100");
		pvo.setPname("Modified Product Name");
		pvo.setPrice(12345);
		pvo.setDescription("Modified Product Description");
		pvo.setMadeBy("Modified Made By");
		pvo.setPno(1L);
		pdao.update(pvo);
	}
	
	@Test
	public void removeProduct() throws Exception{
		cdao.deleteAll(1L);	// 항상 그 안의 것들 지우고 메인을 지우자
		pdao.delete(1L);
	}
}
