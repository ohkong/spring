package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import logic.FoodList;
import logic.Nutrition;

public interface NutMapper {
	@Select("SELECT name,max(no) no FROM nutrition WHERE NAME LIKE '%${value}%' group by name")
	List<Map<String,Object>> list(String input);
	@Select("select distinct date from eatfood where id = #{value}")
	List<String> getEatDate(String id);
	@Select("SELECT eatfood.date DATE ,eatfood.no no,nutrition.name NAME , nutrition.calorie calorie,"
			+ "nutrition.carbohyd carbohyd,nutrition.protein protein,nutrition.fat fat" + 
			" FROM eatfood JOIN nutrition ON (eatfood.foodidx = nutrition.no) WHERE date =#{date} and id=#{userid}"
			+ " order by eatfood.date")
	List<FoodList> eatFoodBydate(Map<String,Object> map);
	@Insert("insert into eatfood (no,foodidx,DATE,id) values (#{no},#{foodIdx},#{date},#{userid})")
	void addEatfood(FoodList food);
	@Select("select ifnull(max(no),0) from eatfood")
	int max();
	@Select("select max(no) from nutrition where name=#{value} group by name")
	int getfod(String fod);
	@Delete("delete from eatfood where no =#{value}")
	void delete(int no);

}
