package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import exception.LoginException;
import logic.User;

@Component
@Aspect
@Order(1)
public class UserLoginAspect {
	/*
	 * @Around : 핵심메서드 실행 전, 후에서 userLoginCheck메서드 실행
	 * execution(* controller.User*.loginCheck*(..))
	 * 		=> controller 패키지의 User로 시작하는 이름을 가진 클래스의 메서드의 이름이
	 * 			loginCheck로 시작하는 메서드
	 * args(..,session)
	 * 		=> 메서드의 매개변수의 마지막의 매겨변수가 session
	 */
	@Around("execution(* controller.User*.loginCheck*(..)) && args(..,session)")
	public Object userLoginCheck(ProceedingJoinPoint joinPoint,
			HttpSession session) throws Throwable{
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null) {
			throw new LoginException("로그인 후 거래하세요","login.shop");
		}
		return joinPoint.proceed();
	}
	
	@Around("execution(* controller.User*.idcheck*(..)) && args(id,session,..)")
	public Object userIdCheck(ProceedingJoinPoint joinPoint, String id,
						HttpSession session) throws Throwable{
		User loginUser = (User)session.getAttribute("loginUser");
		if(loginUser == null) {
			throw new LoginException("로그인 후 가능합니다.","login.shop");
		}
		if(!id.equals(loginUser.getUserid()) && !loginUser.getUserid().equals("admin")) {
			throw new LoginException("본인만 가능합니다.","main.shop");
		}
		return joinPoint.proceed();
	}
}
