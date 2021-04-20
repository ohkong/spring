package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.Board;

public interface BoardMapper {
	final String select= "select num,name,pass,subject,content,file1 fileurl,regdate,"
			+"readcnt,grp,grplevel,grpstep from board";

	@Select({"<script>",
			"select count(*) from board",
			"<if test='searchcontent !=null and searchtype !=null'>"
			+ " where ${searchtype} like '%${searchcontent}%'</if>",
			"</script>"})
	int count(Map<String, Object> param);

	@Select({"<script>",
		select,
		"<if test='searchcontent !=null and searchtype !=null'>"
		+ " where ${searchtype} like '%${searchcontent}%'</if>",
		"<if test='num != null'>where num = #{num}</if>",
		"<if test='limit != null'>"
		+ "order by grp desc, grpstep asc limit #{startrow}, #{limit}</if>",
		"</script>"})
	List<Board> select(Map<String, Object> param);

	@Update("update board set readcnt = readcnt + 1 where num = #{num}")
	void readcntadd(Integer num);

	@Select("select ifnull(max(num),0) from board")
	int maxnum();

	@Insert("insert into board"
			+ " (num,name,pass,subject,content,file1,regdate,grp,grplevel,grpstep,readcnt)"
			+ " values (#{num},#{name},#{pass},#{subject},#{content},#{fileurl},now(),"
			+ "#{grp},#{grplevel},#{grpstep},0)")
	void insert(Board board);

	//내가
//	@Update("update board set grpstep = grpstep + 1 "
//			+ " where grp = #{grp} and grpstep > #{grpstep}")
//	void grpstepadd(Map<String, Object> param);

	//강사님
	@Update("update board set grpstep = grpstep + 1 "
			+ " where grp = #{grp} and grpstep > #{grpstep}")
	void updateGrpStep(Board board);
	
	@Update("update board set name=#{name},pass=#{pass},subject=#{subject},"
			+ "content=#{content},file1=#{fileurl} where num=#{num}")
	void update(Board board);

	@Delete("delete from board where num=#{num}")
	void delete(int num);

	@Select("select name, count(*) cnt from board group by name"
			+" having count(*) >1 order by cnt desc")
	List<Map<String, Object>> graph();

	@Select("select date_format(regdate,'%Y-%m-%d') regdate, count(*) cnt"
			+ " from board"
			+ " group by date_format(regdate, '%Y-%m-%d')"
			+ " order by regdate desc LIMIT 0,7")
	 List<Map<String, Object>> graph2();

	
}
