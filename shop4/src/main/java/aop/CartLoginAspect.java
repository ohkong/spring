package aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import exception.CartEmptyException;
import exception.LoginException;
import logic.Cart;
import logic.User;

@Component
@Aspect
@Order(1)
public class CartLoginAspect {
	@Around("execution(* controller.Cart*.loginitem*(..)) && args(..,session)")
	public Object cartLoginItemCheck(ProceedingJoinPoint joinPoint,
			HttpSession session) throws Throwable{
		User loginUser = (User)session.getAttribute("loginUser");
		Cart cart = (Cart)session.getAttribute("CART");
		if(loginUser == null) {
			throw new LoginException("로그인 후 거래하세요","../user/login.shop");
		}
		if(cart == null) {
			throw new CartEmptyException("장바구니가 비었습니다","../item/list.shop");
		}
		return joinPoint.proceed();
	}
}
