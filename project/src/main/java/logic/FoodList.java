package logic;


public class FoodList {
	private String date;
	private int no ;
	private String name;
	private int foodIdx;
	private String userid;
	private float calorie;
	private float carbohyd;
	private float protein;
	private float fat;
	

	public int getFoodIdx() {
		return foodIdx;
	}
	public void setFoodIdx(int foodIdx) {
		this.foodIdx = foodIdx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getCalorie() {
		return calorie;
	}
	public void setCalorie(float calorie) {
		this.calorie = calorie;
	}
	public float getCarbohyd() {
		return carbohyd;
	}
	public void setCarbohyd(float carbohyd) {
		this.carbohyd = carbohyd;
	}
	public float getProtein() {
		return protein;
	}
	public void setProtein(float protein) {
		this.protein = protein;
	}
	public float getFat() {
		return fat;
	}
	public void setFat(float fat) {
		this.fat = fat;
	}
	@Override
	public String toString() {
		return "FoodList [date=" + date + ", no=" + no + ", name=" + name + ", food_idx=" + foodIdx + ", userid="
				+ userid + ", calorie=" + calorie + ", carbohyd=" + carbohyd + ", protein=" + protein + ", fat=" + fat
				+ "]";
	}

	
}
