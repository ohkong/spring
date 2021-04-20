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

import logic.Board;

@Repository
public class BoardDao {
	private NamedParameterJdbcTemplate template;
	private RowMapper<Board> mapper = 
			new BeanPropertyRowMapper<Board>(Board.class);
	private Map<String, Object> param = new HashMap<>();
	private String select = "select num,name,pass,subject,content,file1 fileurl,regdate,"
			+"readcnt,grp,grplevel,grpstep from board";
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		template = new NamedParameterJdbcTemplate(dataSource);
	}

	public int count(String searchtype, String searchcontent) {
		String sql="select count(*) from board";
		if(searchtype != null && searchcontent != null) {
			sql += " where " + searchtype + " like :searchcontent";
			param.clear();
			param.put("searchcontent","%" + searchcontent + "%");
		}
		return template.queryForObject(sql, param, Integer.class);
	}

	public List<Board> list(Integer pageNum, int limit, 
						String searchtype, String searchcontent) {
		param.clear();
		String sql = select;
		if(searchtype != null && searchcontent != null) {
			sql += " where " + searchtype + " like :searchcontent";
			param.put("searchcontent","%" + searchcontent + "%");
		}
		sql += " order by grp desc, grpstep asc limit :startrow, :limit";
		param.put("startrow", (pageNum -1) * limit);
		param.put("limit",limit);
		return template.query(sql,param, mapper);
	}

	public Board boardview(int num) {
		param.clear();
		param.put("num",num);
		String sql = select;
		sql += " where num = :num";
		return template.queryForObject(sql, param,mapper);
	}

	public void readcntadd(Integer num) {
		param.clear();
		param.put("num",num);
		String sql = "update board set readcnt = readcnt + 1 where num = :num";
		template.update(sql,param);
	}

	public void boardwrite(Board board) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(board);
		String sql = "insert into board"
				+ " (num,name,pass,subject,content,file1,regdate,grp,grplevel,grpstep,readcnt)"
				+ " values (:num,:name,:pass,:subject,:content,:fileurl,now(),:grp,"
				+ ":grplevel,:grpstep,0)";
		template.update(sql, param);
	}

	public int maxnum() {
		return template.queryForObject("select ifnull(max(num),0) from board",
				param, Integer.class);
	}
	
	//내가
	public void grpstepadd(int grp, int grpstep) {
		param.clear();
		param.put("grp",grp);
		param.put("grpstep",grpstep);
		String sql = "update board set grpstep = grpstep + 1 "
				+ " where grp = :grp and grpstep > :grpstep";
		template.update(sql,param);
	}
	
	//강사님
	public void updateGrpStep(Board board) {
		String sql = "update board set grpstep = grpstep + 1 "
				+ " where grp = :grp and grpstep > :grpstep";
		param.clear();
		param.put("grp",board.getGrp());
		param.put("grpstep",board.getGrpstep());
		template.update(sql,param);
	}

	public void update(Board board) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(board);
		String sql = "update board set name=:name,pass=:pass,subject=:subject,"
				+ "content=:content,file1=:fileurl where num=:num";
		template.update(sql,param);
	}

	public void delete(int num) {
		param.clear();
		param.put("num",num);
		String sql = "delete from board where num=:num";
		template.update(sql,param);
	}


}
