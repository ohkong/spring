package chap3;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main1 {

	public static void main(String[] args) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppCtx.class);
		Executor exec = ctx.getBean("executor",Executor.class);
		exec.addUnit(new WorkUnit());
		exec.addUnit(new WorkUnit());
		
		HomeController home = 
				ctx.getBean("homeController",HomeController.class);
		home.checkSensorAndAlarm();
		//창문에 침입
		System.out.println("====== 창문에 침입자 발견 ======");
		InfraredRaySensor sensor = 
				ctx.getBean("windowSensor",InfraredRaySensor.class);
		sensor.foundObject();
		home.checkSensorAndAlarm();
		System.out.println("====== 현관에 침입자 발견 ======");
		sensor = ctx.getBean("doorSensor",InfraredRaySensor.class);
		sensor.foundObject();
		home.checkSensorAndAlarm();
		System.out.println("====== 전등에 침입자 발견 ======");
		sensor = new InfraredRaySensor("전등센서");
		sensor.foundObject();
		home.checkSensorAndAlarm();
		
		home = new HomeController();	//의존성 주입이 안됨.
		home.checkSensorAndAlarm();
	} 
}
/*
  1. 객체 생성 : @Component
  	   xml : <bean id="소문자로 시작하는 클래스이름" class="패키지명.클래스명" ../>
  	   => <context:component-scan base-package="chap2" /> 설정이 필요함
  	   	  @ComponentScan(basePackages = {"chap2","chap3"})
 
  2. 객체 주입 : @Autowired, @Resource, @Required
  		@Autowired : 자료형이 맞는 객체를 주입.
  					  변수, 메서드에 사용가능
  					  컨테이너내부에 같은 자료형을 가진 객체가 없는 경우 오류발생가능.
  					  컨테이너내부에 같은 자료형을 가진 객체가 여러개인 경우 오류발생가능.
  					 (required=false) : 객체가 없는 경우 null로 가능.
  					 
  		@Resource(이름) : 객체 중 이름으로 객체를 주입.
  		
  		@Required : 자료형 기준으로 객체를 주입. 단 무조건 주입되어야함.
  					주입되는 객체가 없는 경우 오류 발생. => 거의 사용안함.
  					
  3. 기능
  	  @PostConstruct : 객체 생성 후에 호출되는 메서드의 위에 정의.
  	  				     객체 생성 후에 실행되는 메서드.
  	  				     객체 생성 이란 주입되는 객체가 완료 된경우.
  	  				     
	  @Qualifier : 객체의 id 외에 다른 이름을 지정할 수 있음.
	  			   @Bean 과 함께 사용시는 이름 지정
	  			   @Autowired 와 함께 사용시는 객체를 주입.
	  			   
	  @Bean : 객체화 시킴. 이름은 메서드명이 객체의 이름이 된다.
	  		  @Configuration 가진 클래스에서 사용됨.
	  		  
	  @Configuration : 자바 환경 설정 파일 지정시 사용되는 어노테이션
	  
	  @ComponentScan : scan 되는 패키지를 설정. @Configuration 과 함께 사용됨.
 */