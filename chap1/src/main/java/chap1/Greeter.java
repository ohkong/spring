package chap1;

public class Greeter {
	private String format;	//%을 배웁니다
	// guest : 스프링
	public String greet(String guest) {
		return String.format(format,guest);
	}
	//format 매개변수 : %s을 배웁니다
	public void setFormat(String format) {
		this.format = format;
	}
}
