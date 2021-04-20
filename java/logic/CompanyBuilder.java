package logic;

public class CompanyBuilder {

	private String name;
	private String initial;
	private String classify1;
	private String classify2;
	private String country;
	private String firm;
	private double val;
	private double volume;
	private double price;
	public Company build() {
		Company com = new Company();
		com.setName(this.name);
		com.setInitial(this.initial);
		com.setClassify1(this.classify1);
		com.setClassify2(this.classify2);
		com.setCountry(this.country);
		com.setFirm(this.firm);
		com.setVal(this.val);
		com.setVolume(this.volume);
		com.setPrice(this.price);return com;
	}
	public CompanyBuilder price(double price) {
		this.price=price;
		return this;
	}
	public CompanyBuilder val(double val) {
		this.val=val;
		return this;
	}
	public CompanyBuilder volume(double volume) {
		this.volume=volume;
		return this;
	}
	public CompanyBuilder name(String name) {
		this.name=name;
		return this;
	}
	public CompanyBuilder initial(String initial) {
		this.initial=initial;
		return this;
	}
	public CompanyBuilder classify1(String classify1) {
		this.classify1=classify1;
		return this;
	}
	public CompanyBuilder classify2(String classify2) {
		this.classify2=classify2;
		return this;
	}
	public CompanyBuilder country(String country) {
		this.country=country;
		return this;
	}
	public CompanyBuilder firm(String firm) {
		this.firm=firm;
		return this;
	}
	
}
