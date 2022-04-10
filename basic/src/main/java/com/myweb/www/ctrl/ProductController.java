package com.myweb.www.ctrl;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.domain.BFileVO;
import com.myweb.www.domain.PagingVO;
import com.myweb.www.domain.ProductDTO;
import com.myweb.www.domain.ProductVO;
import com.myweb.www.handler.FileHandler;
import com.myweb.www.handler.PagingHandler;
import com.myweb.www.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/product/*")
@Controller
public class ProductController {
	
	@Inject
	private ProductService psv;
	
	@Inject
	private FileHandler fhd;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(ProductVO pvo, RedirectAttributes reAttr, @RequestParam(name = "files", required = false)MultipartFile[] files) {
		List<BFileVO> fList = null;
		if(files[0].getSize() > 0) {
			fList = fhd.uploadFiles(files);
			pvo.setHasFile(fList.size());
		}
		reAttr.addFlashAttribute("isUp", psv.register(new ProductDTO(pvo, fList)) > 0 ? "1" : "0");
		return "redirect:/product/list";
	}
	
	@GetMapping("/list")
	public void list(Model model, PagingVO pgvo) {
		model.addAttribute("list", psv.getList(pgvo));
		int totalCount = psv.getTotalCount(pgvo);
		model.addAttribute("pgn", new PagingHandler(pgvo, totalCount));
	}
	
	@GetMapping({"/detail", "/modify"})
	public void detail(Model model, @RequestParam("pno")long pno, @ModelAttribute("pgvo")PagingVO pgvo) {
		model.addAttribute("pdto", psv.getDetail(pno));
	}
	
	@PostMapping("/modify")
	public String modify(RedirectAttributes reAttr, ProductVO pvo, PagingVO pgvo, @RequestParam(name = "files", required = false)MultipartFile[] files) {
		log.debug(">>> {}", pvo);
		List<BFileVO> fList = null;
		if(files[0].getSize() > 0) {
			fList = fhd.uploadFiles(files);
		}
		reAttr.addFlashAttribute("isMod", psv.modify(new ProductDTO(pvo, fList)) > 0 ? "1" : "0");
		reAttr.addAttribute("pageNo", pgvo.getPageNo());
		reAttr.addAttribute("qty", pgvo.getQty());
		reAttr.addAttribute("type", pgvo.getType());
		reAttr.addAttribute("keyword", pgvo.getKeyword());
		return "redirect:/product/list";
	}
	
	@PostMapping("/remove")
	public String remove(@RequestParam("pno")long pno, RedirectAttributes reAttr) {
		int isDel = psv.remove(pno);
		reAttr.addFlashAttribute("isDel", isDel);
		return "redirect:/product/list";
	}
}
