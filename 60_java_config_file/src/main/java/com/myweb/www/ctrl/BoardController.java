package com.myweb.www.ctrl;


import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.domain.BFileVO;
import com.myweb.www.domain.BoardDTO;
import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.FileHandler;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board/*")
@Controller
public class BoardController {
	
	@Inject
	private BoardService bsv;
	
	@Inject
	private FileHandler fhd;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(BoardVO bvo, RedirectAttributes reAttr, @RequestParam(name = "files", required = false)MultipartFile[] files) {
		log.debug(">>> {}", bvo);	// 원래는 toString이 출력 됐었음
		List<BFileVO> bfList = null;
		if(files[0].getSize() > 0) {
			// jsp때 처럼 일일이 꺼내주기보다 pgaehandler처럼 그 기능을 하는 클래스를 만들것임 > fileHandler
			bfList = fhd.uploadFiles(files);
			
			// bno는 file 등록할 때 알 수 없음 VO는 가지고 있음
			// oracle이면 VO에서 생성된 번호를 바로 알아서 나올 수가 있음
			
			// mysql, maria는 안됨. 어떻게 하냐면
			// 개별적으로 움직이는 방법이 있음 먼저 VO객체 던져서 집어 넣어 놓고 번호 생기면 바로 알려줄 수 있게 만듦(bno의 max를 가지고 나오면 제일 최근 것이 나옴)
			// 이 방법은 트랜잭션이 필수 > insert를 던지자마자 select를 해야하니 그 전엔 다른걸 못하니까
			// 근데 우리는 안 됨 > VO와 file을 DTO로 보내기로 했기 때문에
			// service에서 분리 된다 > service 쪽에서 VO객체 먼저 보내서 bno를 알아내고 기다리고 있는 file에 bno를 넣어주면 된다
			
			// 다른 방법은
			// 처음부터 bno를 알 수 있는 방법이 있음
			// 그러나 많은 사용자가 있을 시에는 사용하면 안됨
			// 현재 작성하려고 하는 게시글 번호는 현재 게시글 번호의 뒷번호
			// current max 번호를 받고 + 1 한 값을 file에 넣어주면 된다
			// vo 객체는 어차피 auto_increment로 들어 갈 것
			
		}
		reAttr.addFlashAttribute("isUp", bsv.register(new BoardDTO(bvo, bfList)) > 0 ? "1":"0");
		return "redirect:/board/list";
	}
	// 파일을 commons-io, commons-fileupload 라이브러리로 받을 거임
	// required = false 는 파일이 없을수도 있다는 것을 의미
	// MultipartFile[]는 얘로 파일을 받을것이다라는 의미. 받을게 여러개라 [](배열)로 받음
	// 파일이 여러개라는게 아니라 폼태그에 실어서 보낼 때 인코딩 타입이 multipart form 데이터로 보내는데
	// 폼태그 안에 정보값들이 여러개라 (string , file 등등) multi'part'
	
	@GetMapping("/list")
	public void list(Model model, PagingVO pagingVO) {
		model.addAttribute("list", bsv.getList(pagingVO));
		int totalCount = bsv.getTotalCount(pagingVO);
		model.addAttribute("pgn", new PagingHandler(pagingVO, totalCount));
	}
	
//	@GetMapping("/list")
//	public void list(Model model) {
//		model.addAttribute("list", bsv.getList());
//	}
	@GetMapping({"/detail", "/modify"})
	public void detail(Model model, @RequestParam("bno")long bno,
			@ModelAttribute("pgvo")PagingVO pgvo) {
		model.addAttribute("bdto", bsv.getDetail(bno));	// 우리는 여기에 파일 값도 필요하니 getDetail에 파일도 가져올 수 있게 하자.
//		model.addAttribute("pgvo", pgvo); > @ModelAttribute("pgvo")로 처리할 수 있음
	}
	@PostMapping("/modify")	// 파일 modify는 register와 똑같다
	public String modify(RedirectAttributes reAttr, BoardVO bvo, PagingVO pgvo, @RequestParam(name = "files", required = false)MultipartFile[] files) {
		log.debug(">>> {}", bvo);
		List<BFileVO> bfList = null;
		if(files[0].getSize() > 0) {
			bfList = fhd.uploadFiles(files);			
		}
		reAttr.addFlashAttribute("isMod", bsv.modify(new BoardDTO(bvo, bfList)) > 0 ? "1":"0");
		reAttr.addAttribute("pageNo", pgvo.getPageNo());		
		reAttr.addAttribute("qty", pgvo.getQty());
		reAttr.addAttribute("type", pgvo.getType());		
		reAttr.addAttribute("keyword", pgvo.getKeyword());
		
		return "redirect:/board/detail?bno="+bvo.getBno();
	}
	@PostMapping("/remove")
	public String remove(RedirectAttributes reAttr, @RequestParam("bno")long bno, 
			PagingVO pgvo) {
		reAttr.addFlashAttribute("isDel", bsv.remove(bno) > 0 ? "1":"0");
		reAttr.addAttribute("pageNo", pgvo.getPageNo());		
		reAttr.addAttribute("qty", pgvo.getQty());
		reAttr.addAttribute("type", pgvo.getType());		
		reAttr.addAttribute("keyword", pgvo.getKeyword());
		return "redirect:/board/list";
	}
	@DeleteMapping(value = "/file/{uuid}", produces = MediaType.TEXT_PLAIN_VALUE)	// 주소로 uuid가 들어왔으니까 ~body 필요 없음
	public ResponseEntity<String> removeFile(@PathVariable("uuid")String uuid){
		return bsv.removeFile(uuid) > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
