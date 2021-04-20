package logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.CalDao;
import dao.UserDao;

@Service
public class OjhService {
	@Autowired
	private CalDao calDao;
	@Autowired
	private UserDao userDao;

	public int maxseq() {
		return calDao.maxseq();
	}

	public void calwrite(Cal cal) {
		calDao.calwrite(cal);
	}

	public void insertUser(@Valid User user) {
		userDao.insert(user);
	}

	public User selectUser(String id) {
		return userDao.selectUser(id);
	}

	public void chgsum(Cal cal, User loginUser) {
		calDao.chgsum(cal,loginUser);
	}

	public List<Cal> daylist(String id) {
		return calDao.daylist(id);
	}

	public void caldelete(int seq) {
		calDao.caldelete(seq);
	}
	
	public void rechgsum(Cal cal, User user) {
		calDao.rechgsum(cal,user);
	}

	public int selectprice(int seq) {
		return calDao.selectprice(seq);
	}

	public List<Cal> selectcallist(String id,String date) {
		return calDao.selectcallist(id,date);
	}
	
	public Cal selectcal(int seq) {
		return calDao.selectcal(seq);
	}

	public void calupdate(Cal cal) {
		calDao.calupdate(cal);
	}

	public List<Integer> daycnt(String id) {
		return calDao.daycnt(id);
	}

	public List<Cal> selectdate(String id) {
		return calDao.selectdate(id);
	}

	public int lmontot(String id, String lamon) {
		return calDao.lmontot(id,lamon);
	}

	public int lmontot2(String id, String lamon) {
		return calDao.lmontot2(id,lamon);
	}
	
	public int tmontot(String id, String thmon) {
		return calDao.tmontot(id,thmon);
	}

	public int tmontot2(String id, String thmon) {
		return calDao.tmontot2(id,thmon);
	}

	public List<Cal> selectlist(String id) {
		return calDao.selectlist(id);
	}

	public List<Cal> selectkp(String id, int i,String thmon) {
		return calDao.selectkp(id,i,thmon);
	}

	public Map<String, Object> kpgraph1(String id, String thmon) {
		Map<String,Object> map = new HashMap<String, Object>();
		for(Map<String,Object> m : calDao.kpgraph1(id,thmon)) {
			map.put((String)m.get("kind"),m.get("price"));
		}
		return map;
	}
	
	public Map<String, Object> kpgraph2(String id,String thmon) {
		Map<String,Object> map = new HashMap<String, Object>();
		for(Map<String,Object> m : calDao.kpgraph2(id,thmon)) {
			map.put((String)m.get("kind"),m.get("price"));
		}
		return map;
	}
	public void userDelete(String userid) {
		userDao.delete(userid);		
	}

	
	public List<Board> recomandBoard() {
		return calDao.recomandBoard();
	}	
}
