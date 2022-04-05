package com.myweb.www.ctrl;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.domain.BoardVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.service.BoardService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/board/*")
@Controller
public class BoardController {
	@Inject
	private BoardService bsv;
	
	@GetMapping("/register")
	public void register() {}	// 다시 내보내는 주소가 같기때문에 void로 그대로 내보낸다
	
	@PostMapping("/register")
	public String register(BoardVO bvo, RedirectAttributes reAttr) {
		log.debug(">>> {}", bvo);
		// 원래 하던것을 한번에 선언
		reAttr.addFlashAttribute("isUp", bsv.register(bvo) > 0 ? "1" : "0");
		return "redirect:/board/list";
	}
	
//	@GetMapping("/list")
//	public void list(Model model) {
//		model.addAttribute("list", bsv.getList());
//	}
	
	// paging
	@GetMapping("/list")
	public void list(Model model, PagingVO pagingVO) {
		model.addAttribute("list", bsv.getList(pagingVO));
		// total 개수를 알아야 페이지 개수도 알 수 있기 때문에 total도 요구하는 메서드 필요
		int totalCount = bsv.getTotalCount(pagingVO);
		// pagingVO에 모든걸 다넣으면 너무 복잡해지니까 몇번째 페이지번호인지, 몇개를 보여달라고 하는지, 키워드가 뭔지, 검색타입이 뭔지 이정도만 가지고 있고(VO객체처럼)
		// 실제적으로 연산해주는 객체는 따로 만든다
		model.addAttribute("pgn", new PagingHandler(pagingVO, totalCount));
	}
	
	
	
	// 이전엔 detail화면에서 바로 수정했다면, 이번엔 화면전환으로 modify를 해보자
	// detail과 똑같이 만들어지는데 modify는 input만 생긴다. -> detail과 modify를 병합할 수 있는 방법이 있다
	// -> 배열 형식으로 묶어주면 된다
	// 단, 메서드 내용이 아예 똑같아야 한다. (if문으로 나눌 순 있으나 그럴바엔 따로 작성하자)
	// paging 때문에 파라미터 추가 됐음
	@GetMapping({"/detail", "/modify"})
	public void detail(Model model, @RequestParam("bno")long bno, @ModelAttribute("pgvo") PagingVO pgvo) {
		model.addAttribute("bvo", bsv.getDetail(bno));
		// model.addAttribute("pgvo", pgvo);	> PagingVO 앞에 @ModelAttribute("pgvo")로 처리할 수 있음
		// 수정을 할 경우는 위의 주석처리 된 경우처럼 해서 보내야 함. 어노테이션은 받은 그대로 보내는 방식
	}
	
	@PostMapping("/modify")
	public String modify(RedirectAttributes reAttr, BoardVO bvo, PagingVO pgvo) {
		log.debug(">>> {}", bvo);
		reAttr.addFlashAttribute("isMod", bsv.modify(bvo) > 0 ? "1" : "0");
		// addFlashAttribute 써도 됨
		reAttr.addAttribute("pageNo", pgvo.getPageNo());
		reAttr.addAttribute("qty", pgvo.getQty());
		return "redirect:/board/detail?bno=" + bvo.getBno();
	}
	
	@PostMapping("/remove")
	public String remove(RedirectAttributes reAttr, @RequestParam("bno")long bno, PagingVO pgvo) {
		reAttr.addFlashAttribute("isDel", bsv.remove(bno) > 0 ? "1" : "0");
		// model처럼 그냥 나가도 되지만, RedirectAttributes를 해줬으므로 이렇게 썼다.
		reAttr.addAttribute("pageNo", pgvo.getPageNo());
		reAttr.addAttribute("qty", pgvo.getQty());
		return "redirect:/board/list";
	}
}
