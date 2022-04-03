package com.myweb.www.repos;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.repository.BoardDAO;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
// xml과는 다르게 경로를 class로 잡아줘야 한다
@ContextConfiguration(classes = {com.myweb.www.config.RootConfig.class})
public class BoardTest {
	@Inject
	private BoardDAO bdao;
	
	@Test
	public void insertBoardTest() throws Exception {
		BoardVO bvo = new BoardVO();
		bvo.setBno(1L);
		bvo.setTitle("Test Title");
		bvo.setContent("Test Content");
		bvo.setWriter("Test Writer");
		
		int isUp = bdao.insertBoard(bvo);
		log.debug(">>> isUp > {}", isUp);
	}
	
}
