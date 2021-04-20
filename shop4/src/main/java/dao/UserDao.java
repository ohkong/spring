package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.UserMapper;
import logic.User;

@Repository
public class UserDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();

	public void insert(User user) {
		template.getMapper(UserMapper.class).insert(user);
	}

	public User selectUser(String userid) {
		param.clear();
		param.put("userid",userid);
		List<User> list = template.getMapper(UserMapper.class).select(param);
		if(list == null || list.size() == 0) return null;
		else return list.get(0);
	}

	public void update(User user) {
		template.getMapper(UserMapper.class).update(user);
	}

	public void deleteUser(String userid) {
		template.getMapper(UserMapper.class).delete(userid);
	}

	public List<User> list(){
		return template.getMapper(UserMapper.class).select(null);
	}
	
	public List<User> list(String[] idchks) {
		param.clear();
		param.put("userids",idchks);
		return template.getMapper(UserMapper.class).select(param);
	}

	public String search(User user) {
		//내가
		if(user.getUserid() == null) //idsearch
			return template.getMapper(UserMapper.class).useridsearch(user);
		else	//pwsearch
			return template.getMapper(UserMapper.class).passwordsearch(user);
		
		//강사님
		
	}

	public void passwordUpdate(String userid,String pass) {
		param.clear();
		param.put("userid",userid);
		param.put("password",pass);
		template.getMapper(UserMapper.class).passwordupdate(param);
	}
}
