package logic;

public class Recipe {
	private int no;
	private float carbohyd;
	private float protein;
	private float fat;
	private float calorie;
	private String name;
	private String raw;
	private String recipe;
	private String recipe_pic;
	private String food_pic;
	private String recway;
	private String recpat;
	private int readcnt;
	public static RecipeBuilder build() {
		return new RecipeBuilder();
	}
	
	@Override
	public String toString() {
		return "Recipe [no=" + no + ", carbohyd=" + carbohyd + ", protein=" + protein + ", fat=" + fat + ", calorie="
				+ calorie + ", name=" + name + ", raw=" + raw + ", recipe=" + recipe + ", recipe_pic=" + recipe_pic
				+ ", food_pic=" + food_pic + ", recway=" + recway + ", recpat=" + recpat + ", readcnt=" + readcnt + "]";
	}

	public String getRecway() {
		return recway;
	}

	public void setRecway(String recway) {
		this.recway = recway;
	}

	public String getRecpat() {
		return recpat;
	}

	public void setRecpat(String recpat) {
		this.recpat = recpat;
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
	public String getRaw() {
		return raw;
	}
	public void setRaw(String raw) {
		this.raw = raw;
	}
	public String getRecipe() {
		return recipe;
	}
	public void setRecipe(String recipe) {
		this.recipe = recipe;
	}
	public String getRecipe_pic() {
		return recipe_pic;
	}
	public void setRecipe_pic(String recipe_pic) {
		this.recipe_pic = recipe_pic;
	}
	public String getFood_pic() {
		return food_pic;
	}
	public void setFood_pic(String food_pic) {
		this.food_pic = food_pic;
	}

	public int getReadcnt() {
		return readcnt;
	}

	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}


	
}
