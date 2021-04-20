package logic;


public class IncreaseBuilder {
	private int seq;
	private String regdate;
	private String name;
	private double dailyval;
	public Increase build() {
		Increase in = new Increase();
		in.setDailyval(this.dailyval);
		in.setName(this.name);
		in.setRegdate(this.regdate);
		in.setSeq(this.seq);
		return in;
	}
	public IncreaseBuilder dailyval(double dailyval) {
		this.dailyval=dailyval;
		return this;
	}
	public IncreaseBuilder name(String name) {
		this.name=name;
		return this;
	}
	public IncreaseBuilder regdate(String regdate) {
		this.regdate=regdate;
		return this;
	}
	public IncreaseBuilder seq(int seq) {
		this.seq=seq;
		return this;
	}
	
}
