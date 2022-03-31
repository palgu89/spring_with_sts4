package com.myweb.www.ctrl;

import java.util.HashMap;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
	
	@ResponseBody
	// 우리는 body에 실어서 요청했으니까 보낼때도 body에 실어서 보낸다라는 어노테이션이다
	@PostMapping(value = "/dupleCheck", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	// consumes에 들어온 값의 타입으로 하고, produces에 들어온 값으로 진행을 한다 -> ajax 통신으로 오는 값들을 받을 때 설정해줘야 함
	// TEXT_PLAIN_VALUE: 들어오는 값을 온전히 그 타입 그대로 받겠다
	public ResponseEntity<String> dupleCheck(@RequestBody HashMap<String, String> map) {
		// 우리가 body에서 값을 받기로 member.register.js에서 설정했으므로 @RequestBody를 이용해 받는다
		int isExist = msv.dupleCheck(map.get("email"));
		return isExist > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : new ResponseEntity<String>("0", HttpStatus.OK);
		// 일반적으로 여기에 isExist를 넣으면 ViewResolver로 가서 jsp로 가는데 이러면 페이지가 리로딩 될 것이다. 하지만 우리는 그냥 데이터만 받아오고 싶다
		// 문제는 나한테 보내줄때도 js 객체를 stringify 해서 보냈다. 마찬가지로 자바한테 줄 때도 js의 스트링을 ResponseEntity<String>로 변환해서 받아야 할 것이다 ( 왜냐면 xml로 왔다갔다 하기 때문에 변환이 필요하다 )
		// HttpStatus.OK는 현재 상황이 잘 돌아가냐고 물어보는 것 ( 정상적인 신호 번호는 200번 ) ex. 404: 페이지 로딩 x (?), 500: 인터넷 서버 오류
	}
	
	@ResponseBody
	@PostMapping(value = "/grade", consumes = "application/json", produces = {MediaType.TEXT_PLAIN_VALUE})
	public ResponseEntity<String> updateGrade(@RequestBody HashMap<String, String> map) {
		// HashMap 대신에 바로 MemberVO mvo 를 써도 가능하다. 밑은 msv.modifyGrade(mvo)라고 적어주면 된다.
		// 단 이름이 서로 다르면 HashMap을 사용해야 한다.
		int isUp = msv.modifyGrade(new MemberVO(map.get("email"), Integer.parseInt(map.get("grade"))));
		return isUp > 0 ? new ResponseEntity<String>("1", HttpStatus.OK) : new ResponseEntity<String>("0", HttpStatus.OK);
	}
}
