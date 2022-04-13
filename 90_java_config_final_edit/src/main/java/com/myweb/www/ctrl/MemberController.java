package com.myweb.www.ctrl;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.security.MemberVO;
import com.myweb.www.service.MemberService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/member/*")
public class MemberController {
	@Inject
	private BCryptPasswordEncoder bcpEncoder;
	
	@Inject
	private MemberService msv;
	
	@GetMapping("/register")
	public void register() {}
	
	@PostMapping("/register")
	public String register(MemberVO mvo) {	// 공부차원에서 security에 memberVO를 넣었다. 실무에서는 domain에 넣는게 맞다. RedirectAttributes reAttr는 생략함
		// 암호화(원래는 id도 암호화 하지만 그렇게 되면 db가 너무 힘들어서 pwd만 하겠다)
		// vo객체가 페이지에서 넘어오면 그걸 꺼내서 pwd를 가지고 나옴 > 암호화 시켜서 > 다시 vo객체에 넣음 > db로 보냄
		mvo.setPwd(bcpEncoder.encode(mvo.getPwd()));
		return "redirect:"+(msv.register(mvo) > 0 ? "/member/register" : "/member/register");	// 성공하면 로그인, 실패하면 다시 회원가입
	}
	
	@GetMapping("/login")
	public void login() {}
	
	@PostMapping("/login")	// 실패하면 여기로 오게 된다
	public String login(HttpServletRequest req, RedirectAttributes reAttr) {
		reAttr.addFlashAttribute("email", req.getAttribute("email"));
		reAttr.addFlashAttribute("errMsg", req.getAttribute("errMsg"));
		return "redirect:/member/login";
	}
}
