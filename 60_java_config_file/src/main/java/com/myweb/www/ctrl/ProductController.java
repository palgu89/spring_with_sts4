package com.myweb.www.ctrl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.domain.PagingVO;
import com.myweb.www.domain.ProductVO;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.service.ProductService;

@RequestMapping("/product/*")
@Controller
public class ProductController {
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Inject
	private ProductService psv;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(ProductVO pvo, RedirectAttributes reAttr) {
		int isUp = psv.register(pvo);
		reAttr.addFlashAttribute("isUp", isUp);
		return "redirect:/product/list";
	}
	
	@GetMapping("/list")
	public void list(Model model, PagingVO pgvo) {
		model.addAttribute("list", psv.getList(pgvo));
		int totalCount = psv.getTotalCount(pgvo);
		model.addAttribute("pgn", new PagingHandler(pgvo, totalCount));
	}
	
	@GetMapping("/detail")
	public void detail(Model model, @RequestParam("pno")long pno) {
		model.addAttribute("pvo", psv.getDetail(pno));
	}
	
	@PostMapping("/modify")
	public String modify(ProductVO pvo, RedirectAttributes reAttr) {
		int isMod = psv.modify(pvo);
		reAttr.addFlashAttribute("isMod", isMod);
		return "redirect:/product/detail?pno="+pvo.getPno();
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("pno")long pno, RedirectAttributes reAttr) {
		int isDel = psv.remove(pno);
		reAttr.addFlashAttribute("isDel", isDel);
		return "redirect:/product/list";
	}
}
