package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.Board;
import logic.Cal;

public interface CalMapper {

	@Select("select ifnull(max(seq),0) from cal")
	int maxseq();
	
	@Insert("insert into cal"
			+ " (seq,iotype,kind,detail,date,price,id)"
			+ " values (#{seq},#{iotype},#{kind},#{detail},#{date},#{price},#{id})")
	void insert(Cal cal);

	@Update("update member set sum=sum+#{price} where id = #{id}")
	void plussum(Map<String, Object> param);
	
	@Update("update member set sum=sum-#{price} where id = #{id}")
	void minussum(Map<String, Object> param);

	@Select("SELECT seq,date,iotype,kind,detail,price FROM cal " 
			+ " WHERE id=#{id}"
			+ " ORDER BY date desc")
	List<Cal> daylist(Map<String, Object> param);

	@Delete("delete from cal where seq=#{seq}")
	void caldelete(int seq);

	@Select("select price from cal where seq=#{seq}")
	int selectprice(int seq);

	@Select("select * from cal where seq=#{seq}")
	Cal selectcal(int seq);

	@Update("update cal set iotype=#{iotype},kind=#{kind},detail=#{detail}"
			+ ",date=#{date},price=#{price},id=#{id} where seq=#{seq}")
	void calupdate(Cal cal);

	@Select("select * from cal WHERE id=#{id} and date=#{date}")
	List<Cal> callist(Map<String, Object> param);


	@Select("SELECT count(*) " + 
			"FROM cal " + 
			"WHERE id=#{id} " + 
			"GROUP BY date ORDER BY date DESC;")
	List<Integer> daycnt(Map<String, Object> param);

	@Select("SELECT distinct DATE FROM cal WHERE id=#{id} ORDER BY date DESC;")
	List<Cal> selectdate(String id);

	
	@Select("SELECT ifnull(SUM(price),0) AS price FROM cal WHERE id=#{id} AND DATE LIKE #{date} AND iotype=1")
	int lmontot(Map<String, Object> param);

	@Select("SELECT ifnull(SUM(price),0) AS price FROM cal WHERE id=#{id} AND DATE LIKE #{date} AND iotype=2")
	int lmontot2(Map<String, Object> param);

	@Select("SELECT ifnull(SUM(price),0) AS price FROM cal WHERE id=#{id} AND DATE LIKE #{date} AND iotype=1")
	int tmontot(Map<String, Object> param);

	@Select("SELECT ifnull(SUM(price),0) AS price FROM cal WHERE id=#{id} AND DATE LIKE #{date} AND iotype=2")
	int tmontot2(Map<String, Object> param);

	@Select("select * from cal where id=#{id}")
	List<Cal> selectlist(String id);

	@Select("SELECT kind,ifnull(SUM(price),0) AS price FROM cal WHERE id=#{id} AND iotype=#{i} "
			+ "and date LIKE #{date} GROUP BY kind order by price desc")
	List<Cal> selectkp(Map<String, Object> param);

	@Select("SELECT kind,ifnull(SUM(price),0) AS price FROM cal WHERE id=#{id} AND iotype=1 "
			+ "and date LIKE #{date} GROUP BY kind order by price desc")
	List<Map<String, Object>> kpgraph1(Map<String, Object> param);
	
	@Select("SELECT kind,ifnull(SUM(price),0) AS price FROM cal WHERE id=#{id} AND iotype=2 "
			+ "and date LIKE #{date} GROUP BY kind order by price desc")
	List<Map<String, Object>> kpgraph2(Map<String, Object> param);

	@Insert("insert into cal"
			+ " (seq,iotype,date,price,id)"
			+ " values (#{seq},#{iotype},#{date},#{price},#{id})")
	void insertdate(Cal one);

	

	@Select("SELECT * FROM board WHERE readcnt>0 ORDER BY readcnt desc LIMIT 0,5")
	List<Board> recomandBoard();

}
