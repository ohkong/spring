package dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import logic.User;

public interface UserMapper {

	@Insert("insert into useraccount "
			+"(userid,username,password,birthday,phoneno,postcode,address,email)"
			+" values (#{userid},#{username},#{password},#{birthday},"
			+ "#{phoneno},#{postcode},#{address},#{email})")
	void insert(User user);

	@Select({"<script>",
			"select * from useraccount",
			"<if test='userid != null'> where userid = #{userid}</if>",
			"<if test='userids != null'> where userid in ",
			"<foreach collection='userids' item='id' separator=',' open='('",
			" close=')'>#{id}</foreach></if>",
			"</script>"})
	List<User> select(Map<String, Object> param);

	@Update("update useraccount set username=#{username}, phoneno=#{phoneno},"
				+ " postcode=#{postcode}, address=#{address}, email=#{email},"
				+ " birthday=#{birthday} where userid=#{userid}")
	void update(User user);

	@Delete("delete from useraccount where userid=#{userid}")
	void delete(String userid);

	@Select("select concat(substr(userid,1,char_length(userid)-2), '**')"
				+ " from useraccount"
				+ " where email=#{email} and phoneno=#{phoneno}")
	String useridsearch(User user);

	@Select("select concat('**',substr(password,3,char_length(password)-2))"
			+ " from useraccount"
			+ " where userid=#{userid} and email=#{email} and phoneno=#{phoneno}")
	String passwordsearch(User user);
	
	@Select({"<script>",
			"select ${col} from useraccount",
			"<trim prefix='where' prefixOverrides='AND || OR'>"
			+"<if test='userid != null'> and userid=#{userid}</if>"
			+" and email=#{email} and phoneno=#{phoneno}</trim>",
			"</script>"})
	String search(Map<String, Object> param);

	@Update("update useraccount set password=#{password} where userid=#{userid}")
	void passwordupdate(Map<String, Object> param);
}
