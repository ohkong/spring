package logic;


public class Increase {
	private int seq;
	private String regdate;
	private String name;
	private double dailyval;
	public static IncreaseBuilder build() {
		return new IncreaseBuilder();
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate2) {
		this.regdate = regdate2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getDailyval() {
		return dailyval;
	}
	public void setDailyval(double dailyval) {
		this.dailyval = dailyval;
	}
	
}
