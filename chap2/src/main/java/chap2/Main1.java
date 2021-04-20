package chap2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class Main1 {
	public static void main(String[] args) {
		ApplicationContext ctx = new GenericXmlApplicationContext("classpath:annotation.xml");
		Executor exec = ctx.getBean("executor", Executor.class);
		exec.addUnit(new WorkUnit());
		exec.addUnit(new WorkUnit());

		HomeController home = ctx.getBean("homeController", HomeController.class);
		home.checkSensorAndAlarm(); // 출력되는 내용 없음

		// 창문에 침입자 발생
		System.out.println("창문에 침입자 발견");
		InfraredRaySensor sensor = ctx.getBean("windowSensor", InfraredRaySensor.class);
		sensor.foundObject();
		home.checkSensorAndAlarm();

		System.out.println("전등센서에 침입자 발견");
		sensor = new InfraredRaySensor("전등센서");
		sensor.foundObject();
		home.checkSensorAndAlarm();
	}
}
