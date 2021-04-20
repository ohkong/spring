package controller;



import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import exception.LoginException;
import logic.ShopService;
import logic.User;
import logic.Item;
import logic.Sale;
import logic.SaleItem;

@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private ShopService shopService;
	
	@GetMapping("*")
	/*
	public ModelAndView user() {
		ModelAndView mav = new ModelAndView("user/userEntry");
		User u = new User();
		mav.addObject(u);
		return mav;
	}
	*/
	public String user(Model model) {
		model.addAttribute(new User());
		return null;
	}
	
	@RequestMapping({"main","password"})//login되어야 하는 메서드 이름을 loginCheck
	public String loginCheckmain(HttpSession session) {
		
		return null;
	}

	@PostMapping("userEntry")
	public ModelAndView userEntry(@Valid User user, BindingResult bindResult) {
		ModelAndView mav = new ModelAndView();
		if(bindResult.hasErrors()) {
			mav.getModel().putAll(bindResult.getModel());
			bindResult.reject("error.input.user");
			return mav;
		}
		//db에 user 정보 useraccount 테이블에 저장.
		try {
			shopService.insertUser(user);
			mav.addObject("user",user);
		}catch(DataIntegrityViolationException e) {	
			e.printStackTrace();
			bindResult.reject("error.duplicate.user");	
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		mav.setViewName("redirect:/user/login.shop");
		return mav;	
	}
	
	@PostMapping("login")
	public ModelAndView login(@Valid User user, BindingResult bresult,
													HttpSession session) {
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			bresult.reject("error.input.user");
			return mav;
		}
		//userid 맞는 User 객체 조회
		//아이디가 없는 경우 아이디 없음 화면에 출력
		//비밀번호 틀린경우 비밀번호 오류 화면에 출력
		//정상 : session.setAttribute("loginUser",User객체)
		//		main.shop으로 페이지 이동.
		try {
			User dbuser = shopService.selectUser(user.getUserid());
			//user.getPassword() : 입력된 비밀번호
			//dbuser.getPassword() : 등록된 비밀번호
			if(user.getPassword().equals(dbuser.getPassword())) {	// 정상 로그인
				session.setAttribute("loginUser", dbuser);
				mav.setViewName("redirect:main.shop");
			} else {	//비밀번호 오류
				bresult.reject("error.login.password");
				mav.getModel().putAll(bresult.getModel());
			}
		}catch(EmptyResultDataAccessException e) {	//해당 아이디 없는 경우 예외발생	
			bresult.reject("error.login.id");	
			mav.getModel().putAll(bresult.getModel());
		}
		return mav;
	}
	
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate(); //loginUser 속성, CART 속성 제거. 새로운 session 객체로 변경
		return "redirect:login.shop";
	}
	
	/*
	 * AOP 설정하기
	 * 1. UserController의 idcheck로 시작하는 메서드 + 
	 * 		매개변수가 id, session인 경우를 pointcut으로 설정
	 * 2. 로그인 안된경우 : 로그인하세요 메시지 출력 => login.shop 페이지 이동
	 * 	  admin이 아니고, 다른 아이디 정보 조회시 : 
	 * 					본인만 조회가능합니다. main.shop 페이지 이동
	 */
	@RequestMapping("mypage")
	public ModelAndView idcheckmypage(String id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		//userid에 맞는 User 정보 한개
		User user = shopService.selectUser(id);
		//userid가 주문한 주문 정보 목록 리턴
		List<Sale> salelist = shopService.salelist(id);
		for(Sale sa : salelist) {
			//주문번호에 주문한 주문상품 목록 리턴
			List<SaleItem> saleitemlist = shopService.saleItemList(sa.getSaleid());
			for(SaleItem si : saleitemlist) {
				//주문상품id에 해당하는 Item객체를 리턴
				Item item = shopService.getItem(Integer.parseInt(si.getItemid()));
				si.setItem(item);
			}
			sa.setItemList(saleitemlist);	//한개의 주문에 해당하는 주문상품 목록 추가
		}
		mav.addObject("user",user);
		mav.addObject("salelist",salelist);
		return mav;
	}
	
	@GetMapping({"update","delete"})
	public ModelAndView idcheckupdate(String id, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = shopService.selectUser(id);
		mav.addObject("user",user);
		return mav;
	}
	
	@PostMapping("update")
	public ModelAndView idcheckupdate(@Valid User user, BindingResult bresult,
							HttpSession session){
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			bresult.reject("error.input.user");
			return mav;
		}
		//비밀번호 검증. 비밀번호가 일치하는 경우 useraccount 테이블 수정
		//로그인한 사용자의 비밀번호와, 입력된 비밀번호가 일치 검증
		//비밀번호가 일치하면 user 정보로 db수정
		// 일치하지 않으면 : error.login.password 코드를 입력하여 update.jsp페이지에
		//						글로벌 오류 메시지 출력.
		try {
			User loginUser = (User)session.getAttribute("loginUser");
				if(user.getPassword().equals(loginUser.getPassword())) {	
					shopService.update(user);
					mav.setViewName("redirect:mypage.shop?id="+user.getUserid());
					if(user.getUserid().equals(loginUser.getUserid())) {
						session.setAttribute("loginUser", user);
					}
				} else {	//비밀번호 오류
					bresult.reject("error.login.password");
					mav.getModel().putAll(bresult.getModel());
				}
			
		}catch(EmptyResultDataAccessException e) {	
			bresult.reject("error.login.password");	
			mav.getModel().putAll(bresult.getModel());
		}
		return mav;
	}
	
	@PostMapping("delete")
	public ModelAndView idcheckdelete(String userid,HttpSession session,
												String password) {
		ModelAndView mav = new ModelAndView();
		User loginUser = (User)session.getAttribute("loginUser");
		if(userid.equals("admin")) {
			throw new LoginException("관리자 탈퇴는 불가합니다.","main.shop");
		}
		if(!password.equals(loginUser.getPassword())) {
			throw new LoginException("탈퇴시 비밀번호가 틀립니다.","delete.shop?id="+userid);
		}
		try {
			shopService.deleteUser(userid);
		}catch(Exception e) {
			e.printStackTrace();
			throw new LoginException("탈퇴시 오류가 발생했습니다.","delete.shop?id="+userid);
		}
		if(loginUser.getUserid().equals("admin")) {
			mav.setViewName("redirect:/admin/list.shop");
		}else {
			session.invalidate();
			throw new LoginException(userid+"회원님의 탈퇴 처리가 되었습니다", "login.shop");
		}
		return mav;
	}
	
	@PostMapping("{url}search")
	public ModelAndView search(User user,BindingResult bresult,
											@PathVariable String url) {
		ModelAndView mav = new ModelAndView();
		String code="error.userid.search";
		String title = "아이디";
		if(url.equals("pw")) {
			code = "error.password.search";
			title = "비밀번호";
			if(user.getUserid()==null || user.getUserid().equals("")) {
				bresult.rejectValue("userid","error.required");
			}
		}
		if(user.getEmail()==null || user.getEmail().equals("")) {
			bresult.rejectValue("email","error.required");
		}
		if(user.getPhoneno()==null || user.getPhoneno().equals("")) {
			bresult.rejectValue("phoneno","error.required");
		}
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		if(user.getUserid()!=null && user.getUserid().equals(""))
							user.setUserid(null);
		String result = null;
		try {
			result = shopService.selectsearch(user);
		}catch(EmptyResultDataAccessException e){
			bresult.reject(code);
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		mav.addObject("result",result);
		mav.addObject("title",title);
		mav.setViewName("search");
		return mav;
	}
	
	//@RequestMapping과 PostMapping이 같이 설정된 경우 Post방식 요청인 경우 이 메서드 호출
	//@RequestParam : 요청 파라미터 값을 저장하기 위한 객채 설정
	//		요청 파라미터 : 1. 파라미터 이름과 매개변수 이름이 같은 경우 
	//				    2. bean클래스의 프로퍼티와 파라미터가 같은 경우 Bean클래스의 객체에 저장
	//					3. Map객체를 이용하여 파라미터 저장
	@PostMapping("password")
	public ModelAndView loginCheckpassword(@RequestParam Map<String,String> param,
													HttpSession session) {
		ModelAndView mav = new ModelAndView();
		System.out.println(param);
		User loginUser = (User)session.getAttribute("loginUser");
		
		if(param.get("password").equals(loginUser.getPassword())) {
			try {
				shopService.userPasswordUpdate(loginUser.getUserid(),param.get("chgpass"));
				loginUser.setPassword(param.get("chgpass"));
				mav.addObject("message",loginUser.getUsername()+"님 비밀번호가 변경되었습니다.");
				mav.addObject("url","main.shop");
				mav.setViewName("alert");
			}catch(Exception e) {
				throw new LoginException("비밀번호 변경시 오류가 있습니다.", "password.shop");
			}
		}else {
			throw new LoginException("현재 비밀번호 틀립니다.", "password.shop");
		}
		return mav;
	}
}
