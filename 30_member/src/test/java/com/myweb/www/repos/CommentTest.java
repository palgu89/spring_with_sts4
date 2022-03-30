package com.myweb.www.repos;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myweb.www.domain.CommentVO;
import com.myweb.www.repository.CommentDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "file:src/main/webapp/WEB-INF/spring/root-context.xml")
public class CommentTest {
	private static Logger logger = LoggerFactory.getLogger(CommentTest.class);

	@Inject
	private CommentDAO cdao;
	
	@Test
	public void insertCommentDummy() throws Exception {
		CommentVO cvo = new CommentVO();
		cvo.setPno(112L);
		cvo.setWriter("8989");
		cvo.setContent("hi this is my comment");
		cdao.insert(cvo);
	}
	
	@Test
	public void getListComments() throws Exception {
		List<CommentVO> list = cdao.selectList(259);
		for(CommentVO cvo : list) {
			logger.info(">>> {},{},{},{}", cvo.getCno(), cvo.getWriter(), cvo.getContent(), cvo.getModAt());
		}
	}
	
	@Test
	public void modifyComment() throws Exception {
		CommentVO cvo = new CommentVO();
		cvo.setCno(1L);
		cvo.setContent("bye this is my comment");
		int isUp = cdao.update(cvo);
		logger.info(">>> Modify {}", isUp > 0 ? "Success" : "Fail");
	}
	
	@Test
	public void removeComment() throws Exception {
		int isUp = cdao.deleteOne(8378L);
		logger.info(">>> Remove {}", isUp > 0 ? "Success" : "Fail");
	}
	
	@Test
	public void removeAllComment() throws Exception {
		int isUp = cdao.deleteAll(257L);
		logger.info(">>> RemoveAll {}", isUp > 0 ? "Success" : "Fail");
	}
}
