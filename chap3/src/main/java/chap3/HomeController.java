package chap3;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component	//객체화. DI이용하여 객체들의 주입이 완료.
public class HomeController {
	private AlarmDevice alarmDevice;
	private Viewer viewer;
	//@Resource : 객체의 이름으로 객체를 주입함
	@Resource(name="camera1")
	private Camera camera1;	//number=1인 Camera 객체 주입
	@Resource(name="camera2")
	private Camera camera2;	//number=2인 Camera 객체 주입
	@Resource(name="camera3")
	private Camera camera3;	//number=3인 Camera 객체 주입
	@Resource(name="camera4")
	private Camera camera4;	//number=4인 Camera 객체 주입
	
	private List<InfraredRaySensor> sensors;
	//Autowired : 객체의 자료형을 이용하여 주입함.
	//(required=false) : 없으면 주입안함. 기본값은 true.
	@Autowired(required=false)
	private Recorder recorder;
	
	@Autowired	//반드시 주입되는 객체가 존재해야함.
				// AlarmDevice 객체와 Viewer 객체를 찾아서 주입함.
				// alarmDevice : SmsAlarmDevice 객체 주입됨.
				// viewer : SmartPhoneViewer 객체 주입됨 
	public void prepare(AlarmDevice alarmDevice, Viewer viewer) {
		this.alarmDevice = alarmDevice;
		this.viewer = viewer;
	}
	@PostConstruct 	//객체 생성이 완료(DI완료)된 이후에 자동으로 호출되는 메서드
	public void init() {
		System.out.println("init() 메서드 호출");
		viewer.add(camera1);
		viewer.add(camera2);
		viewer.add(camera3);
		viewer.add(camera4);
		viewer.draw();
	}
	@Autowired
	//별명 설정. qualifier 가 intrusionDetection 인 객체들을 주입
	@Qualifier("intrusionDetection")
	public void setSensors(List<InfraredRaySensor> sensors) {
		System.out.println("setSensors() 메서드 호출");
		this.sensors = sensors;
		for(InfraredRaySensor s : sensors) {
			System.out.println(s.getName());
		}
	}
	public void checkSensorAndAlarm() {
		for(InfraredRaySensor s : sensors) {
			if(s.isObjectFounded()) {
				alarmDevice.alarm(s.getName());
			}
		}
	}
}
