package logic;

public class RecipeBuilder {
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
	
	public Recipe build() {
		Recipe rec = new Recipe(); 
		rec.setNo(this.no);
		rec.setCarbohyd(this.carbohyd);
		rec.setProtein(this.protein);
		rec.setFat(this.fat);
		rec.setCalorie(this.calorie);
		rec.setName(this.name);
		rec.setRaw(this.raw);
		rec.setRecipe(this.recipe);
		rec.setRecipe_pic(this.recipe_pic);
		rec.setFood_pic(this.food_pic);
		rec.setRecway(this.recway);
		rec.setRecpat(this.recpat);
		return rec;
	}
	public RecipeBuilder name(String name) {
		this.name=name;
		return this;
	}
	public RecipeBuilder recway(String recway) {
		this.recway=recway;
		return this;
	}
	public RecipeBuilder recpat(String recpat) {
		this.recpat=recpat;
		return this;
	}
	public RecipeBuilder raw(String raw) {
		this.raw=raw;
		return this;
	}
	public RecipeBuilder recipe(String recipe) {
		this.recipe=recipe;
		return this;
	}
	public RecipeBuilder recipe_pic(String recipe_pic) {
		this.recipe_pic=recipe_pic;
		return this;
	}
	public RecipeBuilder food_pic(String food_pic) {
		this.food_pic=food_pic;
		return this;
	}
	public RecipeBuilder no(int no) {
		this.no=no;
		return this;
	}
	public RecipeBuilder carbohyd(float carbohyd) {
		this.carbohyd=carbohyd;
		return this;
	}
	public RecipeBuilder protein(float protein) {
		this.protein=protein;
		return this;
	}
	public RecipeBuilder fat(float fat) {
		this.fat=fat;
		return this;
	}
	public RecipeBuilder calorie(float calorie) {
		this.calorie=calorie;
		return this;
	}
	
}
