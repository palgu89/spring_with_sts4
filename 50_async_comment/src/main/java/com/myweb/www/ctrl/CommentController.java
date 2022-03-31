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

import com.myweb.www.domain.CommentVO;
import com.myweb.www.service.CommentService;

@RequestMapping("/comment/*")
@Controller
public class CommentController {
	private static Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Inject
	private CommentService csv;
	
	@PostMapping(value = "/post", consumes = "application/json", 
			produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> post(@RequestBody CommentVO cvo){
		logger.debug(">>> {}", cvo);
		// isUp을 안만들고 한번에 하는 방법
		// HttpStatus.INTERNAL_SERVER_ERROR는 아예 에러를 보내는 방법 > 기존 방법처럼 0으로 해도 된다.
		return csv.register(cvo) > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@GetMapping(value = "/{pno}", produces = {MediaType.APPLICATION_JSON_VALUE})
	// 그냥 정보를 받는것이기 때문에 get을 사용했다
	// config(들어오는것) 안받으니 consumes 필요 없음. produces(처리)만 하면 됨 > 객체 타입으로 오기 때문에 APPLICAITON_JSON_VALUE
	// {}로 감싸게 되면 변수 이름이 됨 > 주소값에 변수가 있는 경우 > 경로 변수(path variable)라고 부름
	public ResponseEntity<List<CommentVO>> spread(@PathVariable("pno")long pno){
		return new ResponseEntity<List<CommentVO>>(csv.getList(pno), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{cno}", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> erase(@PathVariable("cno")long cno){
		return csv.remove(cno) > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PutMapping(value = "/{cno}", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> edit(@PathVariable("cno")long cno, @RequestBody CommentVO cvo) {
		return csv.modify(cvo) > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
