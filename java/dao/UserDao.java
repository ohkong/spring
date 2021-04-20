package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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

	public User selectUser(String id) {
		param.clear();
		param.put("id",id);
		List<User> list = template.getMapper(UserMapper.class).select(param);
		if(list == null || list.size() == 0) return null;
		else return list.get(0);
	}

	public void update(@Valid User user) {
		template.getMapper(UserMapper.class).update(user);
		
	}

	public void delete(String userid) {
		template.getMapper(UserMapper.class).delete(userid);
	}

	public void changePw(String id, String chgpass) {
		param.clear();
		param.put("id", id);
		param.put("chgpass", chgpass);
		template.getMapper(UserMapper.class).pwupdate(param);
		
	}

	public List<User> getIdsByTel(String tel) {
		return template.getMapper(UserMapper.class).idsearch2(tel);
	}

	public String search(User user) {
		param.clear();
		param.put("email", user.getEmail());
		param.put("id", user.getId());
		param.put("tel", user.getTel());
		if(user.getId()==null) {
			 return template.getMapper(UserMapper.class).idsearch(param);
		}
		else {
			 return template.getMapper(UserMapper.class).pwsearch(param);
			}
			
	}
}
