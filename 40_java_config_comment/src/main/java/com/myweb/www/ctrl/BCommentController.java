package com.myweb.www.ctrl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.myweb.www.domain.BCommentVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.service.BCommentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/bcomment/*")
@Controller
public class BCommentController {
	
	@Inject
	private BCommentService csv;
	
	@PutMapping(value = "/{cno}", consumes = "application/json",
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> edit(@PathVariable("cno") long cno, 
										@RequestBody BCommentVO cvo){
		return csv.modify(cvo) > 0 ?
				new ResponseEntity<String>("1", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@DeleteMapping(value = "/{cno}", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> erase(@PathVariable("cno") long cno){
		return csv.remove(cno) > 0 ?
				new ResponseEntity<String>("1", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/{bno}/{page}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<PagingHandler> spread(@PathVariable("bno") long bno,
			@PathVariable("page")int page){
		log.debug(">>> bcooment bno :{}/{}", bno, page);
		// 몇번째 페이지에 몇개
		PagingVO pgvo = new PagingVO(page, 10);
		return new ResponseEntity<PagingHandler>(csv.getList(bno, pgvo),
									HttpStatus.OK);
	}
	// comment에서 page 하나를 담아서 몇번째를 보여달라 한거를
	// db까지 갔다가 오는건데 문제는 내가 10개만 가지고 나오는거랑
	// 다음이 있냐 없냐까지의 값을 다 가져와야한다
	// 근데 중복되니까 아예 pagingHandler 쪽에다가 끼워서 같이 가져갈래?라고 해버림
	// board나 product가 pagingHandler를 같이 쓰긴 하지만 cmtList를 쓰지 않기 때문에 null로 셋팅 돼서 상관없음
	// 다시 js로 보내면 cmtList -> pageHandler로 이름을 바꿔 이해를 쉽게 가져가자 (cmtList만 오는게 아니기 때문에)
	// result로 주니까 result 안에는 여러개가 담겨 있다 > 객체가 되어있음(next, qty, cmtList ...)
	// 그래서 if 안 result.length를 result.cmtList.length로 바꾼다
	
	@PostMapping(value = "/post", consumes = "application/json", 
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> post(@RequestBody BCommentVO cvo){
		log.debug(">>> {}", cvo);		
		return csv.register(cvo) > 0 ?
				new ResponseEntity<String>("1", HttpStatus.OK)
				: new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
