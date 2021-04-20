package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.Company;
import logic.Increase;

public interface ComMapper {
	@Insert("insert into company (name,initial,classify1,classify2,country,firm) "
			+  "values(#{name},#{initial},#{classify1},#{classify2},#{country},#{firm})")
	void insert(Company com);
	@Select("<script>"
			+ "select * from company "
			+ "<if test='value!=null'> where classify1= #{value}</if>"
			+ "order by classify1"
			+ "</script>")
	List<Company> list(String cls);
	
	@Select("select distinct classify1 from company limit 0,7")
	List<String> classify();
	@Insert("INSERT INTO SP500(seq,NAME,regdate,dailyval) VALUES(#{seq},#{name},DATE_sub(#{regdate},  INTERVAL 9 hour),#{dailyval}) ")
	void insertsp500(Increase inc);
	@Insert("INSERT INTO Dow_Jones(seq,NAME,regdate,dailyval) VALUES(#{seq},#{name},DATE_sub(#{regdate},  INTERVAL 9 hour),#{dailyval}) ")
	void insertdowJones(Increase inc);
	@Update("UPDATE dow_jones SET dailyval=#{dailyval} WHERE regdate =date_format(DATE_sub(#{regdate},  INTERVAL 9 hour),'%Y-%m-%d') and name=#{name}")
	void updatedowJones(Increase inc);
	@Update("UPDATE SP500 SET dailyval=#{dailyval} WHERE regdate =date_format(DATE_sub(#{regdate},  INTERVAL 9 hour),'%Y-%m-%d') and name=#{name}")
	void updatesp500(Increase inc);
	@Select("select ifnull(max(seq),0) from SP500")
	int sp500max();
	@Select("select ifnull(max(seq),0) from dow_jones")
	int dowmax();
	@Select("SELECT date_format(regdate,'%Y-%m-%d') regdate,dailyval cnt FROM  sp500 "
			+ "WHERE NAME= #{value}"
			+ " and DAYOFWEEK( regdate) !=1"
			+ " and DAYOFWEEK( regdate) !=7"
			+ " and regdate not in "
			+ "('2021-01-01','2021-01-18','2021-02-15','2021-04-02','2021-05-31','2021-07-05','2021-09-06','2021-11-25','2021-12-24')"
			+ " order by regdate desc limit 0,7")
	List<Map<String, Object>>  sp500(String cls);
	@Select("SELECT date_format(regdate,'%Y-%m-%d') regdate,dailyval cnt FROM  Dow_Jones"
			+ " WHERE NAME= #{value}"
			+ " and DAYOFWEEK( regdate) !=1"
			+ " and DAYOFWEEK( regdate) !=7"
			+ " and regdate not in "
			+ "('2021-01-01','2021-01-18','2021-02-15','2021-04-02','2021-05-31','2021-07-05','2021-09-06','2021-11-25','2021-12-24')"
			+ " order by regdate desc limit 0,7")
	List<Map<String, Object>>  dow(String cls);
	@Select("select name,dailyval cnt from sp500 where regdate = date_format(DATE_sub(#{value},  INTERVAL 9 hour),'%Y-%m-%d')")
	List<Map<String, Object>> todaySp(String date);
	@Select("select name,dailyval cnt from Dow_Jones where regdate = date_format(DATE_sub(#{value},  INTERVAL 9 hour),'%Y-%m-%d')")
	List<Map<String, Object>> todaydow(String day);

}
