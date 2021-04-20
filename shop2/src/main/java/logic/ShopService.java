package logic;


import java.util.List;

import dao.UserDao;

public class ShopService {
private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public List<User> getUserList(){
		return userDao.list();
	}
	public void insertUser(User user) {
		userDao.insert(user);
	}
	public User getUser(String userid) {
		return userDao.selectOne(userid);
	}
}
