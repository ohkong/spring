package logic;

public class Nutrition {
	private int no;
	private float carbohyd;
	private float protein;
	private float fat;
	private float calorie;
	private String name;
	private String animal_plant;
	public static NutritionBuilder2 build() {
		return new NutritionBuilder2();
	}
	


	@Override
	public String toString() {
		return "Nutrition [no=" + no + ", carbohyd=" + carbohyd + ", protein=" + protein + ", fat=" + fat + ", calorie="
				+ calorie + ", name=" + name + ", animal_plant=" + animal_plant + "]";
	}

	public String getAnimal_plant() {
		return animal_plant;
	}

	public void setAnimal_plant(String animal_plant) {
		this.animal_plant = animal_plant;
	}

	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
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
	public float getCalorie() {
		return calorie;
	}
	public void setCalorie(float calorie) {
		this.calorie = calorie;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
