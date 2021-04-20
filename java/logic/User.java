package logic;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class User {
	@Size(min=3,max=10,message="아이디는 3자이상 10자 이하로 입력하세요.")
	private String id;
	@Size(min=3,max=10,message="비밀번호는 3자이상 10자 이하로 입력하세요.")
	private String pass;
	@NotEmpty(message="사용자이름은 필수입니다.")
	private String name;
	@NotNull(message="뒷자리 첫번째 값을 입력해주세요.")
	private Integer gender;
	private String address;
	@NotEmpty(message="email 인증해주세요.")
	private String email;
	@Size(min=6,max=6,message="주민번호 앞 6자리를 입력해주세요.")
	private String birthday;
	private String tel;
	@NotNull(message="통장잔액을 입력해주세요.")
	private Integer sum;
	private float weight;
	private float height;
	
	public float getWeight() {
		return weight;
	}
	public void setWeight(float weight) {
		this.weight = weight;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", pass=" + pass + ", name=" + name + ", gender=" + gender + ", address=" + address
				+ ", email=" + email + ", birthday=" + birthday + ", tel=" + tel + ", sum=" + sum + ", weight=" + weight
				+ ", height=" + height + "]";
	}
	
}
