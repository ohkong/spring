package logic;

import java.util.ArrayList;
import java.util.List;

public class Eatfood {
	private String date;
	private String id;
	private List<FoodList> foodlist = new ArrayList<FoodList>();
	private int no;
	private String str;
	public Nutrition getTotal() {
		float car =0;
		float cal =0;
		float pro =0;
		float fat =0;
		for(FoodList f :foodlist ) {
			car+=f.getCarbohyd();
			cal+=f.getCalorie();
			pro+=f.getProtein();
			fat+=f.getFat();
		}
		Nutrition n = Nutrition.build().carbohyd(car).calorie(cal).protein(pro).fat(fat).build();
		return n;
	}
	public float getAllNutrition() {
		return getTotal().getCarbohyd()+getTotal().getProtein()+getTotal().getFat();
	}
	public float  getCarbohydRate() {
		return getTotal().getCarbohyd()/getAllNutrition();
	}
	public float  getProteinRate() {
		return getTotal().getProtein()/getAllNutrition();
	}
	public float  getFatRate() {
		return getTotal().getFat()/getAllNutrition();
	}
	public String getStr() {
		return str;
	}
	public void setStr(String str) {
		 this.str=str;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getId() {
		return id;
	}
	public void setId(String userid) {
		this.id = userid;
	}
	public List<FoodList> getFoodlist() {
		return foodlist;
	}
	public void setFoodlist(List<FoodList> foodlist) {
		this.foodlist = foodlist;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	@Override
	public String toString() {
		return "Eatfood [date=" + date + ", userid=" + id + ", foodlist=" + foodlist + ", no=" + no + "]";
	}
	
	
	
}
