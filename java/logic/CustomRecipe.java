package logic;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CustomRecipe {
	private int no;
	private String name;
	private String raw;
	private String recipe;
	private String recipe_pic;
	private String food_Pic;
	private String recway;
	private String recpat;
	private String id;
	private int readcnt;
	
	private MultipartFile foodPicFile;
	private List<String> recipeList;
	private List<MultipartFile> recipePicList;
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
	public String getFood_Pic() {
		return food_Pic;
	}
	public void setFood_Pic(String food_Pic) {
		this.food_Pic = food_Pic;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getReadcnt() {
		return readcnt;
	}
	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}
	public MultipartFile getFoodPicFile() {
		return foodPicFile;
	}
	public void setFoodPicFile(MultipartFile foodPicFile) {
		this.foodPicFile = foodPicFile;
	}
	public List<String> getRecipeList() {
		return recipeList;
	}
	public void setRecipeList(List<String> recipeList) {
		this.recipeList = recipeList;
	}
	public List<MultipartFile> getRecipePicList() {
		return recipePicList;
	}
	public void setRecipePicList(List<MultipartFile> recipePicList) {
		this.recipePicList = recipePicList;
	}
	@Override
	public String toString() {
		return "CustomRecipe [no=" + no + ", name=" + name + ", raw=" + raw + ", recipe=" + recipe + ", recipe_pic="
				+ recipe_pic + ", food_Pic=" + food_Pic + ", recway=" + recway + ", recpat=" + recpat + ", id=" + id
				+ ", readcnt=" + readcnt + ", foodPicFile=" + foodPicFile + ", recipeList=" + recipeList
				+ ", recipePicList=" + recipePicList + "]";
	}
	
	
}
