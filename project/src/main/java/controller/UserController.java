package controller;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
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
import logic.LsjService;
import logic.OjhService;
import logic.User;
import util.CiperUtil;


@Controller
@RequestMapping("user")
public class UserController {
	@Autowired
	private OjhService shopService;
	@Autowired
	private CiperUtil cipher;
	@Autowired LsjService service;
	@GetMapping("*")
	public String user(Model model) {
		model.addAttribute(new User());
		return null;
	}
	

	
	@PostMapping("emailcheck")
	public ModelAndView mailSend(String email) {
		ModelAndView mav = new ModelAndView();
		int code=(int)(Math.random()*100);
		System.out.println(code);
		mav.addObject("code",code);
		mav.addObject("email",email);
		return mav;
	}
	@PostMapping("userEntry")
	public ModelAndView userEntry(@Valid User user, BindingResult bindResult) throws ParseException {
		ModelAndView mav = new ModelAndView();
		if(bindResult.hasErrors()) {
			mav.getModel().putAll(bindResult.getModel());
			bindResult.reject("error.input.user");
			return mav;
		}
		String email = user.getEmail();
		String birth = user.getBirthday();
		SimpleDateFormat smf = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat smf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date =smf.parse(user.getBirthday());
		//db에 user 정보 useraccount 테이블에 저장.
		try {
			user.setPass(cipher.makehash(user.getPass()));
			user.setEmail(cipher.encrypt(user.getEmail(), cipher.makehash(user.getId())));
			user.setBirthday(smf2.format(date));
			shopService.insertUser(user);
			mav.addObject("user",user);
		}catch(DataIntegrityViolationException e) {	
			e.printStackTrace();
			bindResult.reject("error.duplicate.user");	
			mav.getModel().putAll(bindResult.getModel());
			user.setEmail(email);
			user.setBirthday(birth);
			return mav;
		}catch(Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("redirect:/user/login.shop");
		return mav;
	}
	@GetMapping({"update","delete"})
	public ModelAndView idCheckupdateform(String id , HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = shopService.selectUser(id);
		try {
			user.setEmail(cipher.decrypt(user.getEmail(), cipher.makehash(user.getId())));
			String[] birth =user.getBirthday().split("-");
			StringBuilder b = new StringBuilder(); 
			b.append(birth[0].charAt(2));
			b.append(birth[0].charAt(3)) ;
			b.append(birth[1]);
			b.append(birth[2]);
			System.out.println(b.toString());
			user.setBirthday(b.toString());
		} catch (NoSuchAlgorithmException e) {System.out.println("해시에러");}
		mav.addObject("user",user);
		return mav;
		
	}
	@PostMapping("update")
	public ModelAndView idCheckUpdate(@Valid User user,BindingResult bresult, HttpSession session) throws ParseException {
		ModelAndView mav = new ModelAndView();
		String hashpass="";
		SimpleDateFormat smf = new SimpleDateFormat("yyMMdd");
		SimpleDateFormat smf2 = new SimpleDateFormat("yyyy-MM-dd");
		Date date =smf.parse(user.getBirthday());
		if(bresult.hasErrors()) {
			bresult.reject("error.input.user");
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		User loginUser =(User) session.getAttribute("loginUser");
		loginUser = shopService.selectUser(loginUser.getId());
		try {
			 hashpass = cipher.makehash(user.getPass());
		} catch (NoSuchAlgorithmException e) {
			System.out.println("해쉬 에러");
		}
			
		if(hashpass.equals(loginUser.getPass())) {
			try {
				user.setEmail(cipher.encrypt(user.getEmail(), cipher.makehash(user.getId())));
			} catch (NoSuchAlgorithmException e) {System.out.println("해시에러");}
			user.setBirthday(smf2.format(date));
			service.userUpdate(user);
			if(user.getId().equals(loginUser.getId())) {
				user.setPass(hashpass);
				session.setAttribute("loginUser", user);
		}
		}
		else {
			bresult.reject("error.login.password");
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		mav.setViewName("redirect:mypage.shop?id="+user.getId());
		return mav;
		
	}
	
	@PostMapping("delete")
	public ModelAndView idCheckDelete(String userid,  String password,HttpSession session) throws Throwable{
		ModelAndView mav = new ModelAndView();
		User user = shopService.selectUser(userid);
		User loginuser = (User) session.getAttribute("loginUser");
		String hashpass="";
		try {
			hashpass = cipher.makehash(password);
		} catch (NoSuchAlgorithmException e) {
			System.out.println("해쉬 에러");
		}
		if(hashpass.equals(loginuser.getPass())) {
			
			try{
				shopService.userDelete(userid);
				
			}catch (Exception e) {
				e.printStackTrace();
				throw new LoginException("탈퇴시 오류 발생", "delete.shop?id="+user.getId()) ;
			}
			
			if(!loginuser.getId().equals("admin")) {
				session.invalidate();
				throw new LoginException("탈퇴완료", "login.shop") ;
			}else {
				mav.setViewName("redirect:../admin/list.shop");
			}
			
		}else {
			throw new LoginException("비밀번호가 틀렸습니다", "delete.shop?id="+user.getId()) ;
		}
		
		return mav;
	}
	@PostMapping("password")
	public ModelAndView loginCheckPassword(@RequestParam Map<String, String> param, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User loginuser = (User)session.getAttribute("loginUser");
		loginuser = shopService.selectUser(loginuser.getId());
		String hashpass="",hashchpass="";
		try {
			
			 hashpass = cipher.makehash(param.get("password"));
			 hashchpass = cipher.makehash(param.get("chgpass"));
		} catch (NoSuchAlgorithmException e) {
			System.out.println("해쉬 에러");
		}
			if(hashpass.equals(loginuser.getPass())) {
			try{
				service.userChangePass(loginuser.getId(),hashchpass);
				mav.addObject("msg",loginuser.getName()+"님 비밀번호 변경완료");
				mav.addObject("url","main.shop");
				mav.setViewName("alert");
			}catch (Exception e) {
				e.printStackTrace();
				throw new LoginException("수정시 오류 발생", "password.shop") ;
			}
		}else {
			throw new LoginException("비밀번호가 틀렸습니다", "password.shop") ;
		}
			return mav;
	}
	@PostMapping("{url}search")
	public ModelAndView searchUser(User user , BindingResult bindResult, @PathVariable String url,
			String naverid,String naverpw) throws NoSuchAlgorithmException {
		ModelAndView mav = new ModelAndView();
		String code = "error.userid.search";
		String title = "아이디";
		String result = "";
		if(url.equals("pw")) {
			code="error.password.search";
			title="비밀번호";
			if(user.getId()==null || user.getId().equals("")) {
				bindResult.rejectValue("userid", "error.required");
			}
			if(user.getEmail()!=null && !user.getEmail().equals(""))
			user.setEmail(cipher.encrypt(user.getEmail(),cipher.makehash(user.getId())));
		}
		
		if(user.getEmail()==null || user.getEmail().equals("")) {
			bindResult.rejectValue("email", "error.required");
		}
		if(user.getTel()==null || user.getTel().equals("")) {
			bindResult.rejectValue("phoneno", "error.required");
		}
		if(bindResult.hasErrors()) {
			mav.getModel().putAll(bindResult.getModel());
			return mav;
		}
		//검증끝
		if(user.getId()==null || user.getId().equals(""))
			user.setId(null);
		String email = user.getEmail();
		
		if(url.equals("id")) {
			List<User> ids=service.getidByTel(user.getTel());
		for(User id : ids) {
			String hashid = cipher.makehash(id.getId());
			String useremail=cipher.decrypt(id.getEmail(), hashid);
			if(user.getEmail().equals(useremail)) {	//입력한거랑 가져온거 비교하기
				user.setEmail(id.getEmail());//
				break;
			}
		}
		}
		result= service.getSearch(user);
		if(url.equals("pw")) {
			
		
		
		//id , pw 찾기 
//		if(result==null) {	//존재하지 않으면 빨간글씨
//			bindResult.reject(code);
//			mav.getModel().putAll(bindResult.getModel());
//			return mav;
//		}
//			if(url.equals("pw")) { //pw변경 , email 전송 부분
//				try {
//					String chpw = randomPassword();
//					String chpwhash=cipher.makehash(chpw);
//					service.userChangePass(user.getUserid(), chpwhash);
//					Mail mail = new Mail();
//					mail.setNaverid(naverid);
//					mail.setNaverpw(naverpw);
//					mail.setRecipient(email);
//					mail.setTitle("님의 goodee 쇼핑몰 비밀번호");
//					mail.setContents("님의 변경된 비밀번호 : "+chpw);
//					mail.setMtype("text/html; charset=utf-8");
//					
//					result = "비밀번호 : "+chpw;
//				} catch (NoSuchAlgorithmException e) {
//					System.out.println("해시오류");
//				}
			}
		mav.addObject("res",result);
		mav.addObject("title",title);
		mav.setViewName("/user/search");
		return mav;
	}
	@PostMapping("login")
	public ModelAndView login(@Valid User user, BindingResult bresult,
													HttpSession session) throws NoSuchAlgorithmException {
		ModelAndView mav = new ModelAndView();
		System.out.println(user);
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			bresult.reject("error.input.user");
			return mav;
		}
		try {
			User dbuser = shopService.selectUser(user.getId());
			String hashpass = cipher.makehash(user.getPass());
			System.out.println("찾은거"+dbuser);
			if(dbuser == null) {
				bresult.reject("error.login.id");	
				mav.getModel().putAll(bresult.getModel());
				return mav;
			}
			if(hashpass.equals(dbuser.getPass())) {	// 정상 로그인
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
	
	@GetMapping("mypage")
	public ModelAndView idCheckmypage(String id , HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = shopService.selectUser(id);
		try {
			user.setEmail(cipher.decrypt(user.getEmail(), cipher.makehash(user.getId())));
		} catch (NoSuchAlgorithmException e) {System.out.println("해시에러");}
		mav.addObject("user",user);
		return mav;
	}
	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:login.shop";
	}
	

}
