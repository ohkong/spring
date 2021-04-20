package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import dao.mapper.SaleMapper;
import logic.Sale;

@Repository
public class SaleDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	
	//sale 테이블의 최대 saleid값 리턴
	public int getMaxSaleid() {
		return template.getMapper(SaleMapper.class).maxid();
	}

	public void insert(Sale sale) {
		template.getMapper(SaleMapper.class).insert(sale);
	}

	public List<Sale> selectid(String id) {
		return template.getMapper(SaleMapper.class).list(id);
	}

}
