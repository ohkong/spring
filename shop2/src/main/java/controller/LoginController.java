package controller;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.ShopService;
import logic.User;
import util.LoginValidator;

public class LoginController {
	private ShopService shopService;
	private Validator validator;
	
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	
	public void setValidator(Validator validator) {
		this.validator = validator;
	}
	
	@GetMapping
	//리턴타입 : String => 뷰의 이름전달.
	public String loginform(Model model) {	//model : 데이터 저장
		model.addAttribute(new User());
		return null;	//url : login.shop => view : login.jsp 설정
	}
	
	@PostMapping	
	public ModelAndView login(User user, BindingResult bresult,
						HttpSession session) {
		ModelAndView mav = new ModelAndView();
		validator.validate(user, bresult);
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		/*
		 * 1. db에서 userid에 해당하는 고객 정보를 일어 User객채에 저장
		 * 2. 입력된 비밀번호와 db의 비밀번호 비교 하여 일치하는 경우
		 * 		session.setAttribute("loginUser",dbuser)실행
		 * 3. 입력된 비밀번호와 db의 비밀번호 비교 하여 불일치 하는 경우
		 * 	    유효성 검증으로 "비밀번호를 확인하세요." 메세지를 login.jsp 페이지로 전송
		 * 4. 로그인이 정상적인 경우 loginSuccess.jsp 페이지로 이동.
		 */
		try {
			//1
			//dbuser가 없는 경우 : id에 해당하는 사용자 없는 경우.
			User dbuser = shopService.getUser(user.getUserid());
			//2,4
			if(user.getPassword().equals(dbuser.getPassword())) {//정상로그인
				session.setAttribute("loginUser", dbuser);//속석에 User객체 저장.
				mav.setViewName("loginSuccess");
			} else {	//3. 비밀번호 오류
				bresult.reject("error.login.password");
				mav.getModel().putAll(bresult.getModel());
			}
			//spring jdbc에서 발생되는 예외. mybatis에서 발생안됨.
			//조회되는 레코드가 없는 경우 발생
		}catch(EmptyResultDataAccessException e) {	//아이디가 없는 경우
			bresult.reject("error.login.id");	//글로벌 오류
			mav.getModel().putAll(bresult.getModel());
		}
		return mav;	
	}
}
