package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import logic.User;

@Repository
public class UserDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<User> mapper = 
			new BeanPropertyRowMapper<User>(User.class);
	private Map<String, Object> param = new HashMap<>();
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
	}

	public List<User> list() {
		return template.query("select * from useraccount",mapper);
	}

	public User selectUser(String userid) {
		param.clear();
		param.put("userid",userid);
		return template.queryForObject
				("select * from useraccount where userid=:userid",param,mapper);
	}

	public void insert(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "insert into useraccount "
			+"(userid,username,password,birthday,phoneno,postcode,address,email)"
			+" values (:userid,:username,:password,:birthday,:phoneno,:postcode,:address,:email)";
		template.update(sql, param);
	}

	public void update(User user) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		String sql = "update useraccount set username=:username, phoneno=:phoneno,"
				+ " postcode=:postcode, address=:address, email=:email,"
				+ " birthday=:birthday where userid=:userid";
		template.update(sql, param);
	}

	public void deleteUser(String userid) {
		param.clear();
		param.put("userid",userid);
		String sql = "delete from useraccount where userid=:userid";
		template.update(sql, param);
	}

	public List<User> list(String[] idchks) {
		String user = "(";
		for(int i=0;i<idchks.length;i++) {
			user += "'" + idchks[i] + "'" + ((i==idchks.length-1)?"":",");
		}
		user += ")";
		String sql="select * from useraccount where userid in "+ user;
		return template.query(sql,mapper);
	}

	public String search(User user) {
		String sql = null;
		if(user.getUserid() == null)	//idsearch인 경우
			sql="select concat(substr(userid,1,char_length(userid)-2), '**')"
				+ " from useraccount"
				+ " where email=:email and phoneno=:phoneno";
		else
			sql="select concat('**',substr(password,3,char_length(password)-2))"
					+ " from useraccount"
					+ " where userid=:userid and email=:email and phoneno=:phoneno";
		System.out.println(sql);
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		return template.queryForObject(sql, param, String.class);
	}

	public void passwordUpdate(String userid,String pass) {
		param.clear();
		param.put("userid",userid);
		param.put("password",pass);
		String sql="update useraccount set password=:password where userid=:userid";
		template.update(sql,param);
	}
}
