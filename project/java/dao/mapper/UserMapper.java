package dao.mapper;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.User;

public interface UserMapper {

	@Insert("insert into member "
			+"(id,name,pass,birthday,gender,sum,address,email,tel)"
			+" values (#{id},#{name},#{pass},#{birthday},"
			+ "#{gender},#{sum},#{address},#{email},#{tel})")
	void insert(User user);

	@Select({"<script>",
		"select * from member",
		"<if test='id != null'> where id = #{id}</if>",
		"</script>"})
	List<User> select(Map<String, Object> param);
	@Update("update member set name=#{name},birthday=#{birthday},sum=#{sum},tel=#{tel},"
			+ "address=#{address},email=#{email},height=#{height},weight=#{weight} where id=#{id}")
	void update(@Valid User user);
	@Delete("delete from member where id=#{value}")
	void delete(String userid);
	@Update("update member set pass=#{chgpass} where id=#{id}")
	void pwupdate(Map<String, Object> param);
	@Select("select id,email from member "
			+ "where tel=#{value}")
	List<User> idsearch2(String tel);
	@Select("select concat(substr(id,1,char_length(id)-2),'**') id from member "
			+ "where email=#{email} and tel=#{tel}")
	String idsearch(Map<String, Object> param);
	@Select("select pass from member "
			+ "where id=#{id} and email=#{email} and tel=#{tel}")
	String pwsearch(Map<String, Object> param);

}
