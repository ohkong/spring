package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.RecipeMapper;
import logic.CustomRecipe;
import logic.Nutrition;
import logic.Recipe;
@Repository
public class RecipeDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String,Object> param  =new HashMap<>();
	public void insertRec(Recipe r) {
		try {
				template.getMapper(RecipeMapper.class).insert(r);
		}catch(Exception e) {
			System.out.println(e);
		}
		
	}
	public int maxno() {
		return template.getMapper(RecipeMapper.class).max();
		
	}
	public List<Recipe> getAllmenu() {
		return template.getMapper(RecipeMapper.class).list();
	}
	public Recipe getRecipe(int no) {
		return template.getMapper(RecipeMapper.class).getOne(no);
	}
	public List<Recipe> getMenu(String raw, String[] check1, String[] check2,int pageNum,int limit,String order,String asc) {
		param.clear();
		if (raw!=null) {
			param.put("raw", raw.split(","));
			}
		else
			param.put("raw", raw);
		if(check1!=null) {
			for(int i =0;i<check1.length;i++) {
				param.put("way"+(i+1), check1[i]);
			}
		}
		if(check2!=null) {
			for(int i =0;i<check2.length;i++) {
				param.put("pat"+(i+1), check2[i]);
			}
		}
		int start = pageNum*10 -10;
		param.put("start", start);
		param.put("limit", limit);
		param.put("order",order);
		param.put("asc",asc);
		return template.getMapper(RecipeMapper.class).select(param);
	}
	public int nutmaxno() {
		return template.getMapper(RecipeMapper.class).maxnu();
	}
	public void insertNut(Nutrition r) {
		try {
			template.getMapper(RecipeMapper.class).insertnu(r);
	}catch(Exception e) {
		System.out.println(e);
	}
	}
	public int menuCount(String raw, String[] check1, String[] check2) {
		param.clear();
		if (raw!=null) {
			param.put("raw", raw.split(","));
			}
		else
			param.put("raw", raw);
		if(check1!=null) {
			for(int i =0;i<check1.length;i++) {
				param.put("way"+(i+1), check1[i]);
			}
		}
		if(check2!=null) {
			for(int i =0;i<check2.length;i++) {
				param.put("pat"+(i+1), check2[i]);
			}
		}
		return template.getMapper(RecipeMapper.class).menuCnt(param);
	}
	public void updateReadCnt(int no) {
		 template.getMapper(RecipeMapper.class).updateReadCnt(no);
	}
	public List<Recipe> recomandRcp() {
		return template.getMapper(RecipeMapper.class).recomandRcp();
	}
	public void writeCustom(CustomRecipe rec) {
		 template.getMapper(RecipeMapper.class).writeCustom(rec);
	}
	public int getCustomNo() {
		return template.getMapper(RecipeMapper.class).getCustomNo();
	}
	public CustomRecipe getCusRecipe(int no) {
		return template.getMapper(RecipeMapper.class).getcus(no);
	}
	public List<CustomRecipe> getCusMenu(String raw, String[] check1, String[] check2, Integer pageNum, int limit) {
		param.clear();
		if (raw!=null) {
			param.put("raw", raw.split(","));
			}
		else
			param.put("raw", raw);
		if(check1!=null) {
			for(int i =0;i<check1.length;i++) {
				param.put("way"+(i+1), check1[i]);
			}
		}
		if(check2!=null) {
			for(int i =0;i<check2.length;i++) {
				param.put("pat"+(i+1), check2[i]);
			}
		}
		int start = pageNum*10 -10;
		param.put("start", start);
		param.put("limit", limit);
		return template.getMapper(RecipeMapper.class).selectCus(param);
	}
	public void updateCustom(CustomRecipe rec) {
		template.getMapper(RecipeMapper.class).updateCustom(rec);
	}
	public void delrcp(int no) {
		template.getMapper(RecipeMapper.class).delrcp(no);
	}
}
