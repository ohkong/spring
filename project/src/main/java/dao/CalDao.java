package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dao.mapper.BoardMapper;
import dao.mapper.CalMapper;
import logic.Board;
import logic.Cal;
import logic.User;

@Repository
public class CalDao {
	@Autowired
	private SqlSessionTemplate template;
	private Map<String, Object> param = new HashMap<>();
	
	public int maxseq() {
		return template.getMapper(CalMapper.class).maxseq();
	}

	public void calwrite(Cal cal) {
		template.getMapper(CalMapper.class).insert(cal);
	}

	public void chgsum(Cal cal, User user) {
		param.clear();
		param.put("id",user.getId());
		param.put("price",cal.getPrice());
		if(cal.getIotype()==1) {
			template.getMapper(CalMapper.class).plussum(param);
		}else {
			template.getMapper(CalMapper.class).minussum(param);
		}
	}

	public List<Cal> daylist(String id) {
		param.clear();
		param.put("id",id);
		return template.getMapper(CalMapper.class).daylist(param);
	}

	public void caldelete(int seq) {
		template.getMapper(CalMapper.class).caldelete(seq);
	}
	
	public void rechgsum(Cal cal, User user) {
		param.clear();
		param.put("id",user.getId());
		param.put("price",cal.getPrice());
		if(cal.getIotype()==1) {
			template.getMapper(CalMapper.class).minussum(param);
		}else {
			template.getMapper(CalMapper.class).plussum(param);
		}
	}

	public int selectprice(int seq) {
		return template.getMapper(CalMapper.class).selectprice(seq);
	}

	public Cal selectcal(int seq) {
		return template.getMapper(CalMapper.class).selectcal(seq);
	}

	public void calupdate(Cal cal) {
		template.getMapper(CalMapper.class).calupdate(cal);
	}

	public List<Cal> selectcallist(String id, String date) {
		param.clear();
		param.put("id",id);
		param.put("date",date);
		return template.getMapper(CalMapper.class).callist(param);
	}

	public List<Integer> daycnt(String id) {
		param.clear();
		param.put("id",id);
		return template.getMapper(CalMapper.class).daycnt(param);
	}

	public List<Cal> selectdate(String id) {
		return template.getMapper(CalMapper.class).selectdate(id);
	}

	public int lmontot(String id, String lamon) {
		param.clear();
		param.put("id",id);
		param.put("date",lamon);
		return template.getMapper(CalMapper.class).lmontot(param);
	}

	public int lmontot2(String id, String lamon) {
		param.clear();
		param.put("id",id);
		param.put("date",lamon);
		return template.getMapper(CalMapper.class).lmontot2(param);
	}
	
	public int tmontot(String id, String thmon) {
		param.clear();
		param.put("id",id);
		param.put("date",thmon);
		return template.getMapper(CalMapper.class).tmontot(param);
	}

	public int tmontot2(String id, String thmon) {
		param.clear();
		param.put("id",id);
		param.put("date",thmon);
		return template.getMapper(CalMapper.class).tmontot2(param);
	}

	public List<Cal> selectlist(String id) {
		return template.getMapper(CalMapper.class).selectlist(id);
	}

	public List<Cal> selectkp(String id, int i,String thmon) {
		param.clear();
		param.put("id",id);
		param.put("i",i);
		param.put("date",thmon);
		return template.getMapper(CalMapper.class).selectkp(param);
	}

	public List<Map<String, Object>> kpgraph1(String id,String thmon) {
		param.clear();
		param.put("id",id);
		param.put("date",thmon);
		return template.getMapper(CalMapper.class).kpgraph1(param);
	}

	public List<Map<String, Object>> kpgraph2(String id,String thmon) {
		param.clear();
		param.put("id",id);
		param.put("date",thmon);
		return template.getMapper(CalMapper.class).kpgraph2(param);
	}

	public void insertdate(Cal one) {
		template.getMapper(CalMapper.class).insertdate(one);
	}

	
	
	public List<Board> recomandBoard() {
		return template.getMapper(CalMapper.class).recomandBoard();
	}
}
