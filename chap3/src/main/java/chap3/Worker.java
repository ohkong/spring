package chap3;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component	// worker 이름으로 객체화 되어 컨테이너에 저장됨
@Scope(value="prototype",proxyMode=ScopedProxyMode.TARGET_CLASS)	//일회용객체
public class Worker {
	public void work(WorkUnit unit) {
		System.out.println(this + ":work:" + unit);
	}
}
