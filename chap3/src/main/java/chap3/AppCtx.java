package chap3;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"chap3"})
public class AppCtx {
	@Bean
	public Camera camera1() {
		Camera c = new Camera();
		c.setNumber(1);
		return c;
	}
	@Bean
	public Camera camera2() {
		Camera c = new Camera();
		c.setNumber(2);
		return c;
	}
	@Bean
	public Camera camera3() {
		Camera c = new Camera();
		c.setNumber(3);
		return c;
	}
	@Bean
	public Camera camera4() {
		Camera c = new Camera();
		c.setNumber(4);
		return c;
	}
	@Bean
	@Qualifier("intrusionDetection")
	public InfraredRaySensor windowSensor() {
		return new InfraredRaySensor("창센서");
	}
	@Bean
	@Qualifier("intrusionDetection")
	public InfraredRaySensor doorSensor() {
		return new InfraredRaySensor("현관센서");
	}
	@Bean
	@Qualifier("intrusionDetection")
	public InfraredRaySensor lampSensor() {
		return new InfraredRaySensor("전등센서");
	}
	@Bean
	public DisplayMode displayMode() {
		DisplayMode d = new DisplayMode();
		d.setType("GRID");
		return d;
	}
}
