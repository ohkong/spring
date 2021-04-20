package logic;

public class NutritionBuilder2 {
	private int no;
	private float carbohyd;
	private float protein;
	private float fat;
	private float calorie;
	private String name;
	private String animal_plant;
	
	public Nutrition build() {
		Nutrition rec = new Nutrition(); 
		rec.setNo(this.no);
		rec.setCarbohyd(this.carbohyd);
		rec.setProtein(this.protein);
		rec.setFat(this.fat);
		rec.setCalorie(this.calorie);
		rec.setName(this.name);
		rec.setAnimal_plant(this.animal_plant);
		return rec;
	}
	public NutritionBuilder2 animal_plant(String animal_plant) {
		this.animal_plant=animal_plant;
		return this;
	}
	public NutritionBuilder2 name(String name) {
		this.name=name;
		return this;
	}
	public NutritionBuilder2 no(int no) {
		this.no=no;
		return this;
	}
	public NutritionBuilder2 carbohyd(float carbohyd) {
		this.carbohyd=carbohyd;
		return this;
	}
	public NutritionBuilder2 protein(float protein) {
		this.protein=protein;
		return this;
	}
	public NutritionBuilder2 fat(float fat) {
		this.fat=fat;
		return this;
	}
	public NutritionBuilder2 calorie(float calorie) {
		this.calorie=calorie;
		return this;
	}
	
}
