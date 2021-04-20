package dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.ComMapper;
import logic.Company;
import logic.Increase;
@Repository
public class CompanyDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String,Object> param  =new HashMap<String,Object>();
	public void insertCom(Company com) {
		System.out.println(com);
		try {
			template.getMapper(ComMapper.class).insert(com);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public List<Company> list(String cla) {
		return template.getMapper(ComMapper.class).list(cla);
	}
	public List<String> Classify() {
		return template.getMapper(ComMapper.class).classify();
	}
	public void insertsp500(Increase inc) {
		 template.getMapper(ComMapper.class).insertsp500(inc);
	}
	public void insertdowJones(Increase inc) {
		 template.getMapper(ComMapper.class).insertdowJones(inc);
	}
	public void updatedowJones(Increase inc) {
		 template.getMapper(ComMapper.class).updatedowJones(inc);
		
	}
	public void updatesp500(Increase inc) {
		 template.getMapper(ComMapper.class).updatesp500(inc);
		
	}
	public int sp500max() {
		return template.getMapper(ComMapper.class).sp500max();
	}
	public int dowmax() {
		return template.getMapper(ComMapper.class).dowmax();
	}
	public List<Map<String, Object>> sp500(String cls) {
		return template.getMapper(ComMapper.class).sp500(cls);
	}
	public List<Map<String, Object>> dow(String cls) {
		return template.getMapper(ComMapper.class).dow(cls);
	}
	public List<Map<String, Object>> sp500ToDay(String date) {
		return template.getMapper(ComMapper.class).todaySp(date);
	}
	public List<Map<String, Object>> dowToDay(String day) {
		return template.getMapper(ComMapper.class).todaydow(day);
	}

}
