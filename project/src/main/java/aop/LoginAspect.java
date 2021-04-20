package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import exception.LoginException;
import logic.User;

@Component	//aop클래스의 객체화
@Aspect		//aop클래스가 Aspect 클래스로 지정
@Order(1)
public class LoginAspect {
	/*
	 * @Around : 어드바이스 , 핵심메서드 실행 전, 후에서 user LoginCheck메서드 실행
	 * 			=> controller 패키지의 User로 시작하는 이름을 가진 클래스의 메서드의 이름이 loginCheck로 시작하는 메서드
	 * 	args(..,session)
	 * 		=> 메서드의 마지막 매개변수가 session인 메서드
	 */
	@Around("execution(* controller.*.loginCheck*(..)) && args(..,session)")
	public Object userLoginCheck(ProceedingJoinPoint joinPoint,HttpSession session) throws Throwable{
		User loginUser =(User) session.getAttribute("loginUser"); 
		if(loginUser==null) {
			throw new LoginException("로그인 후 거래하세요","../user/login.shop");
		}
		return joinPoint.proceed();
	}
	


}
