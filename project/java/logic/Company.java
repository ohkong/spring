package logic;

public class Company {

	private String name;
	private String initial;
	private String classify1;
	private String classify2;
	private String country;
	private String firm;
	private double val;
	private double volume;
	private double price;
	
	@Override
	public String toString() {
		return "Company [name=" + name + ", initial=" + initial + ", classify1=" + classify1 + ", classify2="
				+ classify2 + ", country=" + country + ", firm=" + firm + ", val=" + val + ", volume=" + volume
				+ ", price=" + price + "]";
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getVal() {
		return val;
	}
	public void setVal(double val) {
		this.val = val;
	}
	public double getVolume() {
		return volume;
	}
	public void setVolume(double volume) {
		this.volume = volume;
	}
	public String getFirm() {
		return firm;
	}
	public void setFirm(String firm) {
		this.firm = firm;
	}
	public static CompanyBuilder builder() {
		return new CompanyBuilder();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInitial() {
		return initial;
	}
	public void setInitial(String initial) {
		this.initial = initial;
	}
	public String getClassify1() {
		return classify1;
	}
	public void setClassify1(String classify1) {
		this.classify1 = classify1;
	}
	public String getClassify2() {
		return classify2;
	}
	public void setClassify2(String classify2) {
		this.classify2 = classify2;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
}
