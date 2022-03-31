package com.myweb.www.ctrl;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myweb.www.domain.MemberVO;
import com.myweb.www.service.MemberService;

@RequestMapping("/member/*")
@Controller	// nav.jspdptj sign-in 버튼 누르면 얘를 찾음 -> 일한 뒤 해석된 주소값으로 다시 돌려줌
public class MemberController {
	private static Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Inject
	private MemberService msv;
	
	
	// @RequestMapping(value = "/register", method = RequestMethod.GET) 
	//  get방식으로 오면 얘를 실행시키겠다는 의미 spring3버전까지는 이렇게 써야만 한다. 
	//  또 parameter가 필요한 경우에는 GetMapping 대신 얘들 써야한다
	
	// @GetMapping("/register")	// 4.3버전부턴 얘만 써도 가능
	// public String register() {
	//	logger.debug(">>> /member/register - GET");
	//	return "member/register";	// return하면 ViewResolver가 앞에는 /WEB-INF/view/ 붙여주고 뒤에는 .jsp 붙여준다
	// }
	
	// 만약 들어오는 주소값과 나가는 주소값이 같다면 아래와 같이 써줘도 된다.
	@GetMapping("/register")
	public void register() {
		logger.debug(">>> /member/register - GET");
	}
	
	/* 원래대로라면 이렇게 작성해야한다
	* @RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register() {
		String email = req.getParameter("email");
		String nickName = req.getParameter("nickName");
		String pwd = req.getParameter("pwd");
		MemberVO mvo = new MemberVO(email, nickName, pwd);
	}*/
	
	// @RequestMapping(value = "/register", method = RequestMethod.POST)
	@PostMapping("/register")	// 4.3버전부턴 얘만 써도 가능
	public String register(MemberVO mvo, RedirectAttributes reAttr) {	// 이렇게만 하면 java 쪽에서 알아서 받아줌. RedirectAttributes는 웹쪽으로 데이터를 보내 웹에서 볼 수 있게 해주는 용도
		logger.debug(">>> /member/register - POST");
		logger.debug(">>> mvo > {}", mvo);
		// controller -> service -> dao -> mapper -> db -> 1 || 0 return
		int isUp = msv.register(mvo);
		logger.debug(">>> Member Register > {}", isUp > 0 ? "Success" : "Fail");
		reAttr.addFlashAttribute("isUp", isUp);	// addFlashAttribute는 일회용 addAttribute는 페이지에 계속 남아있을 수가 있음
		return "redirect:/";	// 원래는 index라고 썼지만 RedirectAttributes를 썼을 땐 redirect:를 적어 @Controller 컴포넌트를 찾은 후 원래 있던 HomeController로 가서 / 인 index로 간다.
	}
//	컨트롤러는 디스패쳐서블릿에 의해 작동 디스패쳐가 컨트롤러에 뿌려주면 컨트롤러가 주소값을 받아 get/post 방식을 처리함 
//	post를 요청하면 매서드를 돌리고 원래는 form 안 내용을 보냈을 때 req....해서 VO객체에 넣었다 
//	이 과정을 스프링 웹이라는 라이브러리가 대신 해준다 (받아온 값들을 받을 객체와 매칭 시켜서 알아서 넣어줌 내부적으로 setter를 사용한다)
//	그걸 파라미터로 mvo를 받아서 쓰겠다고 선언한 것임
//	이 때문에 생성자를 안만들어도 됨. form 태그의 name이 그래서 진짜 중요함
	
	@GetMapping("/login")
	public void login() {
		logger.debug(">>> /member/login - GET");
	}
	
	@PostMapping("/login")
	public String login(MemberVO mvo, HttpSession ses, RedirectAttributes reAttr) {
		logger.debug(">>> mvo > {}", mvo);
		MemberVO loginMvo = msv.login(mvo);
		if(loginMvo != null) {
			ses.setAttribute("ses", loginMvo);
			ses.setMaxInactiveInterval(10*60);	// 10 Min동안 login
		}
		reAttr.addFlashAttribute("isLogin", ses.getAttribute("ses") != null ? 1 : 0);
		return "redirect:/";
	}
	
	@GetMapping("/logout")	// 원래 로그아웃은 제대로 하려면 post로 해야함 그러면 javascript로 해야하는데 지금은 javascript를 사용하기 싫어서 안함
	public String logout(HttpSession ses, RedirectAttributes reAttr) {
		ses.removeAttribute("ses");	// session 객체 내부 ses라는 이름을 갖는 객체 삭제
		reAttr.addFlashAttribute("isOut", ses.getAttribute("ses") == null ? 1 : 0);
		ses.invalidate();	// session 객체 만료 -> f12 -> application -> storage -> cookies에 기록이 남아있음 -> post로 해줘야 함
		return "redirect:/";
	}
	
	@GetMapping("/detail")
	public void detail(@RequestParam("email") String email, Model model) {	// 들어온 주소값(/member/detail)과 나가는 주소값이 같으니 string return 대신 void로
		// detail은 vo객체까지는 필요 없고 email만 있으면 된다 -> @RequestParam("email") 사용 -> 한 두개 필요할 때 쓰면 좋다 (회원가입 같은 경우 x)
		// view로 보내는 방법: Session, RedirectAttribute, ModelAndView ( 오래된 기술이라 잘 안 씀. 동시동작이 안 됨 ) -> Model ( 내부적으로 java hash map을 씀 )
		logger.debug(">>> email > {}", email);
		MemberVO mvo = msv.getDetail(email);
		logger.debug(">>> {}", mvo);
		model.addAttribute("mvo", mvo);	// model에 실어 mvo란 이름으로 mvo를 보낸다
	}
	
	@GetMapping("/remove")
	public String remove(@RequestParam("email")String email, HttpSession ses, RedirectAttributes reAttr) {
		ses.removeAttribute("ses");
		ses.invalidate();
		int isDel = msv.remove(email);
		reAttr.addFlashAttribute("isDel", isDel > 0 ? 1 : 0);
		return "redirect:/";
	}
	
	@PostMapping("/modify")
	public String modify(MemberVO mvo, RedirectAttributes reAttr) {
		int isUp = msv.modify(mvo);
		reAttr.addFlashAttribute("isUp", isUp);	// register의 isUp과 똑같지만 리턴 경로가 다르기 때문에 상관 없음
		return "redirect:/member/detail?email=" + mvo.getEmail();
	}
	
	@GetMapping("/list")
	public void list(Model model) {	// 들어오는건 없고 나가는것만 있기 때문에 model만
		model.addAttribute("list", msv.getList());
	}
}
