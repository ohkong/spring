package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.CustomRecipe;
import logic.Nutrition;
import logic.Recipe;

public interface RecipeMapper {
	@Insert("insert into recipe (no,name,raw,recipe,recipe_pic,food_pic,carbohyd,recway,recpat,protein,fat,calorie) "
			+  "values(#{no},#{name},#{raw},#{recipe},#{recipe_pic},#{food_pic},#{carbohyd},#{recway},#{recpat},#{protein},#{fat},#{calorie})")
	void insert(Recipe rec);
	@Select("select ifnull(max(NO),0) from recipe")
	int max();
	@Select("select * from recipe")
	List<Recipe> list();
	@Select("select * from recipe where no=#{value} ")
	Recipe getOne(int no);
	@Select("<script>"
			+ "select * from recipe "
			+ " where 1=1 "
			+ "<if test='raw!=null'> "
			+ "<foreach item='item' index='index' collection='raw'>" + 
			"        and raw like '%${item}%' "+
			"  </foreach> </if>"
			+ "<if test='way1!=null'> and ( recway like '%${way1}%'</if>"
			+ "<if test='way2!=null'> or recway like '%${way2}%'</if>"
			+ "<if test='way3!=null'> or recway like '%${way3}%'</if>"
			+ "<if test='way4!=null'> or recway like '%${way4}%'</if>"
			+ "<if test='way5!=null'> or recway like '%${way5}%'</if>"
			+ "<if test='way6!=null'> or recway like '%${way6}%'</if>"
			+ "<if test='way1!=null'> ) </if>"
			
			+ "<if test='pat1!=null'> and ( recpat like '%${pat1}%'</if>"
			+ "<if test='pat2!=null'> or recpat like '%${pat2}%'</if>"
			+ "<if test='pat3!=null'> or recpat like '%${pat3}%'</if>"
			+ "<if test='pat4!=null'> or recpat like '%${pat4}%'</if>"
			+ "<if test='pat5!=null'> or recpat like '%${pat5}%'</if>"
			+ "<if test='pat6!=null'> or recpat like '%${pat6}%'</if>"
			+ "<if test='pat1!=null'> ) </if>"
			+ "<if test='order!=null'> order by ${order} </if>"
			+ "<if test='asc!=null'>  ${asc}</if>"
			+ "<if test='start!=null'>  limit #{start},#{limit} </if>"
			+ "</script>")
	List<Recipe> select(Map<String, Object> param);
	
	@Insert("insert into nutrition (no,name,carbohyd ,protein,fat,calorie) "
			+  "values(#{no},#{name},#{carbohyd},#{protein},#{fat},#{calorie})")
	void insertnu(Nutrition r);
	@Select("select ifnull(max(NO),0) from nutrition")
	int maxnu();
	@Select("<script>"
			+ "select count(no) from recipe "
			+ " where 1=1 "
			+ "<if test='raw!=null'> "
			+ "<foreach item='item' index='index' collection='raw'>" + 
			"        and raw like '%${item}%' "+
			"  </foreach> </if>"
			+ "<if test='way1!=null'> and ( recway like '%${way1}%'</if>"
			+ "<if test='way2!=null'> or recway like '%${way2}%'</if>"
			+ "<if test='way3!=null'> or recway like '%${way3}%'</if>"
			+ "<if test='way4!=null'> or recway like '%${way4}%'</if>"
			+ "<if test='way5!=null'> or recway like '%${way5}%'</if>"
			+ "<if test='way6!=null'> or recway like '%${way6}%'</if>"
			+ "<if test='way1!=null'> ) </if>"
			
			+ "<if test='pat1!=null'> and ( recpat like '%${pat1}%'</if>"
			+ "<if test='pat2!=null'> or recpat like '%${pat2}%'</if>"
			+ "<if test='pat3!=null'> or recpat like '%${pat3}%'</if>"
			+ "<if test='pat4!=null'> or recpat like '%${pat4}%'</if>"
			+ "<if test='pat5!=null'> or recpat like '%${pat5}%'</if>"
			+ "<if test='pat6!=null'> or recpat like '%${pat6}%'</if>"
			+ "<if test='pat1!=null'> ) </if>"
			+ "limit 0,30"
			+ "</script>")
	int menuCnt(Map<String, Object> param);
	@Update("update recipe set readcnt=readcnt+1 where no =${value}")
	void updateReadCnt(int no);
	@Select("SELECT * FROM recipe WHERE readcnt>0 ORDER BY readcnt desc LIMIT 0,5")
	List<Recipe> recomandRcp();
	@Insert("insert into customrecipe(no,id,name,raw,recway,recpat,recipe,recipe_pic,food_pic,readcnt)"
			+ "values(#{no},#{id},#{name},#{raw},#{recway},#{recpat},#{recipe},#{recipe_pic},#{food_Pic},0)")
	void writeCustom(CustomRecipe rec);
	@Select("select ifnull(max(no),0) from customrecipe")
	int getCustomNo();
	@Select("select * from customrecipe where no = #{no}")
	CustomRecipe getcus(int no);
	@Select("<script>"
			+ "select * from customrecipe "
			+ " where 1=1 "
			+ "<if test='raw!=null'> "
			+ "<foreach item='item' index='index' collection='raw'>" + 
			"        and raw like '%${item}%' "+
			"  </foreach> </if>"
			+ "<if test='way1!=null'> and ( recway like '%${way1}%'</if>"
			+ "<if test='way2!=null'> or recway like '%${way2}%'</if>"
			+ "<if test='way3!=null'> or recway like '%${way3}%'</if>"
			+ "<if test='way4!=null'> or recway like '%${way4}%'</if>"
			+ "<if test='way5!=null'> or recway like '%${way5}%'</if>"
			+ "<if test='way6!=null'> or recway like '%${way6}%'</if>"
			+ "<if test='way1!=null'> ) </if>"
			
			+ "<if test='pat1!=null'> and ( recpat like '%${pat1}%'</if>"
			+ "<if test='pat2!=null'> or recpat like '%${pat2}%'</if>"
			+ "<if test='pat3!=null'> or recpat like '%${pat3}%'</if>"
			+ "<if test='pat4!=null'> or recpat like '%${pat4}%'</if>"
			+ "<if test='pat5!=null'> or recpat like '%${pat5}%'</if>"
			+ "<if test='pat6!=null'> or recpat like '%${pat6}%'</if>"
			+ "<if test='pat1!=null'> ) </if>"
			+ "<if test='start!=null'>  limit #{start},#{limit} </if>"
			+ "</script>")
	List<CustomRecipe> selectCus(Map<String, Object> param);
	
	@Update("update customrecipe set name =#{name}, raw=#{raw}, recway=#{recway}, recpat=#{recpat}, recipe=#{recipe} "
			+ " where no=#{no}")
	void updateCustom(CustomRecipe rec);
	@Delete("delete from customrecipe where no=${value}")
	void delrcp(int no);

}
