package logic;


import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.CompanyDao;
import dao.NutDao;
import dao.RecipeDao;
import dao.UserDao;

@Service
public class LsjService {
	@Autowired
	private CompanyDao comDao;
	@Autowired
	private RecipeDao recDao;
	@Autowired
	private NutDao nutDao;
	@Autowired
	private UserDao userDao;
	public void insertCompany(Company com) {
		comDao.insertCom(com);
	}
	public void insertRecipe(List<Recipe> list) {
		for(Recipe r : list) {
			int no = recDao.maxno();
			r.setNo(no+1);
			recDao.insertRec(r);
		}
		
	}
	public List<Recipe> getAllMenu() {
		return recDao.getAllmenu();
	}
	public Recipe getRecipe(int no) {
		return recDao.getRecipe(no);
	}
	public List<Company> Comlist(String classi) {
		return comDao.list(classi);
	}
	public List<String> getClassify() {
		return comDao.Classify();
	}
	public List<Recipe> getMenu(String raw, String[] check1, String[] check2,int pageNum,int limit,String order,String asc) {
		return recDao.getMenu(raw,check1,check2, pageNum, limit,order,asc);
	}
	public void insertNutrition(List<Nutrition> list) {
		for(Nutrition r : list) {
			int no = recDao.nutmaxno();
			r.setNo(no+1);
			try {
			recDao.insertNut(r);
			}catch(Exception e) {e.printStackTrace();
		}
			}
		
	}
	public List<Map<String,Object>> getNut(String input) {
		return nutDao.list(input);
	}
	public void insertsp500(String name , double avg) {
		if(avg ==0)return; 
		 TimeZone tz;
		 Date date = new Date();
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 tz = TimeZone.getTimeZone("America/New_York");
		 df.setTimeZone(tz);
		 
		int seq=comDao.sp500max()+1;
		Increase inc = Increase.build().name(name).dailyval(avg).seq(seq).regdate(df.format(date)).build();
			try {
				comDao.insertsp500(inc);
		}catch(Exception  e) {
			comDao.updatesp500(inc);
			e.printStackTrace();
		}
			
	}
	public void insertdowJones(String name , double avg) {
		if(avg ==0)return; 
		TimeZone tz;
		 Date date = new Date();
		 DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 tz = TimeZone.getTimeZone("America/New_York");
		 df.setTimeZone(tz);
		 System.out.println(df.format(date));
		int seq=comDao.dowmax()+1;
		Increase inc = Increase.build().name(name).dailyval(avg).seq(seq).regdate(df.format(date)).build();
		try {
			comDao.insertdowJones(inc);
		}catch(Exception  e) {
			comDao.updatedowJones(inc);
		}
		}
	public Map<String, Object>  bargraph2(String v,String cls) {
		Map<String,Object> map = new TreeMap<String, Object>();
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		if (v.equals("dow")) {
		 data = comDao.dow(cls);
		}
		else {
			data =comDao.sp500(cls);
		}
		for(Map<String,Object> m :data) {
			map.put((String)m.get("regdate"), m.get("cnt"));
		}
		return map;
	}
	public List<String> getEatDate(String userid) {
		return nutDao.getEatDate(userid);
	}
	public List<FoodList> eatFoodBydate(String date,String userid) {
		return nutDao.eatFoodBydate(date,userid);
	}
	public void addEatfood(FoodList food) {
		food.setNo(nutDao.max()+1);
		nutDao.addEatfood(food);
	}
	public int getfodidx(String fod) {
		return nutDao.getfodidx(fod);
	}
	public void delEatfood(int no) {
		nutDao.delEatfood(no);
	}
	public int menucount(String raw, String[] check1, String[] check2) {
		return recDao.menuCount(raw,check1,check2);
	}
	public void userUpdate(@Valid User user) {
		userDao.update(user);
	}
	public void userChangePass(String id, String chgpass) {
		userDao.changePw(id,chgpass);		
	}
	public List<User> getidByTel(String tel) {
		return userDao.getIdsByTel(tel);
	}
	public String getSearch(User user) {
		return userDao.search(user);
	}
	public Map<String, Object> getTodaySector(String date) {
		List<Map<String,Object>> list= comDao.sp500ToDay(date);
		Map<String,Object> map =new HashMap<String, Object>();
		for(Map<String,Object> m : list) {
			map.put((String)m.get("name"), m.get("cnt"));
		}
		
		return map;
	}
	public Map<String, Object> getTodaySector2(String day) {
		List<Map<String,Object>> list= comDao.dowToDay(day);
		Map<String,Object> map =new HashMap<String, Object>();
		for(Map<String,Object> m : list) {
			map.put((String)m.get("name"), m.get("cnt"));
		}
		
		return map;
	}
	public void updateReadCnt(int no) {
		recDao.updateReadCnt(no);
	}
	public List<Recipe> recomandRcp() {
		return recDao.recomandRcp();
	}
	public void customWrite(CustomRecipe rec, HttpServletRequest request) {
		String rec_pic="";
		String recipe="";
		
		uploadFileCreate(rec.getFoodPicFile(),request,"rcp/file/");
		rec.setFood_Pic(rec.getFoodPicFile().getOriginalFilename());
		
		for (MultipartFile f : rec.getRecipePicList()) {
			uploadFileCreate(f,request,"rcp/file/");
			rec_pic+=f.getOriginalFilename()+";";
		}
		rec.setRecipe_pic(rec_pic);
		for(String s : rec.getRecipeList()) {
			recipe+=s+";";
		}
		rec.setRecipe(recipe);
		rec.setNo(recDao.getCustomNo()+1);
		System.out.println(rec);
		recDao.writeCustom(rec);
		
	}
	public void customUpdate(CustomRecipe rec, HttpServletRequest request) {
		String recipe="";
		
//		uploadFileCreate(rec.getFoodPicFile(),request,"rcp/file/");
//		rec.setFood_Pic(rec.getFoodPicFile().getOriginalFilename());
		
		for(String s : rec.getRecipeList()) {
			recipe+=s+";";
		}
		rec.setRecipe(recipe);
		recDao.updateCustom(rec);
		
	}
	private void uploadFileCreate
	(MultipartFile picture, HttpServletRequest request, String path) {
		String orgFile =picture.getOriginalFilename();
		String uploadPath = request.getServletContext().getRealPath("/")+path;
		File fpath=new File(uploadPath); 
		if(!fpath.exists()) fpath.mkdirs();
		try {
			//picture : 업로드된 파일의 내용 저장
			//transferTo : 업로드된 파일의 내용을 File로 저장
			picture.transferTo(new File(uploadPath+orgFile));
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	public CustomRecipe getCusRecipe(int no) {
		return recDao.getCusRecipe(no);
	}
	public List<CustomRecipe> getCusMenu(String raw, String[] check1, String[] check2, Integer pageNum, int limit) {
		return recDao.getCusMenu(raw,check1,check2, pageNum, limit);
	}
	public void delrcp(int no) {
			recDao.delrcp(no);
	}
}
