package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import xml.MemberService;

@Configuration	//자바 설정 파일
@ComponentScan(basePackages = {"annotation","xml"})
@EnableAspectJAutoProxy	//AOP 사용시 어노테이션을 이용하여 설정.
public class AppCtx {
//	@Bean
//	public MemberService memberService2() {
//		return new MemberService();
//	}
}
