package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.NutMapper;
import logic.Eatfood;
import logic.FoodList;
import logic.Nutrition;
@Repository
public class NutDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String,Object> param  =new HashMap<>();
	public  List<Map<String,Object>> list(String input) {
		return template.getMapper(NutMapper.class).list(input);
	}
	public  List<String> getEatDate(String userid) {
		return template.getMapper(NutMapper.class).getEatDate(userid);
	}
	public List<FoodList> eatFoodBydate(String date,String userid) {
		param.clear();
		param.put("userid", userid);
		param.put("date", date);
		return template.getMapper(NutMapper.class).eatFoodBydate(param);
	}
	public void addEatfood(FoodList food) {
		template.getMapper(NutMapper.class).addEatfood(food);
	}
	public int max() {
		return template.getMapper(NutMapper.class).max();
	}
	public int getfodidx(String fod) {
		return template.getMapper(NutMapper.class).getfod(fod);
	}
	public void delEatfood(int no) {
		template.getMapper(NutMapper.class).delete(no);
	}


}
