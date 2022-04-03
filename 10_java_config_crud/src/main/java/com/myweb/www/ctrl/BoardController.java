package com.myweb.www.ctrl;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.domain.BoardVO;
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
	
	@GetMapping("/list")
	public void list(Model model) {
		model.addAttribute("list", bsv.getList());
	}
	
	// 이전엔 detail화면에서 바로 수정했다면, 이번엔 화면전환으로 modify를 해보자
	// detail과 똑같이 만들어지는데 modify는 input만 생긴다. -> detail과 modify를 병합할 수 있는 방법이 있다
	// -> 배열 형식으로 묶어주면 된다
	// 단, 메서드 내용이 아예 똑같아야 한다. (if문으로 나눌 순 있으나 그럴바엔 따로 작성하자)
	@GetMapping({"/detail", "/modify"})
	public void detail(Model model, @RequestParam("bno")long bno) {
		model.addAttribute("bvo", bsv.getDetail(bno));
	}
	
	@PostMapping("/modify")
	public String modify(RedirectAttributes reAttr, BoardVO bvo) {
		log.debug(">>> {}", bvo);
		reAttr.addFlashAttribute("isMod", bsv.modify(bvo) > 0 ? "1" : "0");
		return "redirect:/board/detail?bno=" + bvo.getBno();
	}
	
	@PostMapping("/remove")
	public String remove(RedirectAttributes reAttr, @RequestParam("bno")long bno) {
		reAttr.addFlashAttribute("isDel", bsv.remove(bno) > 0 ? "1" : "0");
		return "redirect:/board/list";
	}
}
