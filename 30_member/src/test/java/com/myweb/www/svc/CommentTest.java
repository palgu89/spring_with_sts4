package com.myweb.www.svc;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.service.CommentService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CommentTest {
	private static Logger logger = LoggerFactory.getLogger(CommentTest.class);

	@Inject
	private CommentService csv;
	
	// 원래 service는 controller가 부르기 때문에 이 부분은 controller와 비슷할 것이다
	
	@Test
	public void insertCommentDummies() throws Exception {
		for (int i = 512; i > 256; i--) {	// 512개의 게시물 중에 뒷번호부터 256개를 뽑아서
			int rcount = (int)(Math.random()*64);	// 0 ~ 63까지 랜덤으로 뽑아 게시물의 댓글 수를 정한다
			for (int j = 0; j < rcount; j++) {
				CommentVO cvo = new CommentVO();
				cvo.setContent("This is Comment of product" + i + " > comment No." + j);
				cvo.setWriter("tester" + (int)(Math.random()*100) + "@tester.com");	// writer를 member 100명 중 랜덤으로 골라서 넣는다
				cvo.setPno(i);
				csv.register(cvo);
			}
		}
	}
	@Test
	public void getListComment() throws Exception {
		csv.getList(412L);	// 512를 먼저 소환했기에 콘솔에 순서대로 나옴. 실제론 섞여 있음
	}
	@Test
	public void modifyComment() throws Exception {
		CommentVO cvo = new CommentVO();
		cvo.setCno(3233L);
		cvo.setContent("Modified Comment of 412 > 0");
		csv.modify(cvo);
	}
	@Test
	public void removeComment() throws Exception {
		csv.remove(3233L);
	}
}
