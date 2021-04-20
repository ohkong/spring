package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Eatfood;
import logic.FoodList;
import logic.LsjService;
import logic.Nutrition;
import logic.User;


@Controller
@RequestMapping("nutrition")
public class NutritionController {

	@Autowired
	private LsjService service;
	@GetMapping("*")
	public String form() {
		return null;
	}
	@PostMapping("addForm")
	public ModelAndView addForm(String input) {
		ModelAndView mav = new ModelAndView();
		List<Map<String,Object>> nut = new ArrayList<Map<String,Object>>();
		nut = service.getNut(input);
		mav.addObject("list",nut);
		return mav;
	}
	@PostMapping("delEatfood")
	public ModelAndView delEatfood(int no) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("url","myNutrition.shop");
		try {
		service.delEatfood(no);
		mav.addObject("msg","성공");
		}catch(Exception e)  { e.printStackTrace();mav.addObject("msg","실패");}
		mav.addObject("url","myNutrition.shop");
		mav.setViewName("alert");
		return mav;
	}
	@PostMapping("addEatfood")
	public ModelAndView addEatfood(String date,String fodidx,String fod,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		FoodList food = new FoodList();
		User loginUser = (User)session.getAttribute("loginUser");
		food.setUserid(loginUser.getId());
//		food.setUserid("1");
		food.setDate(date);
		int idx=0;
		if(fodidx ==null || fodidx.equals("")) {
			 idx = service.getfodidx(fod);
		}else {
			idx = Integer.parseInt(fodidx);
		}
		food.setFoodIdx(idx);
		service.addEatfood(food);
			mav.addObject("month",date.split("-")[1]);
			mav.addObject("day",date.split("-")[2]);
			mav.setViewName("redirect:myNutrition.shop");
		return mav;
	}
	@GetMapping("myNutrition")
	   public ModelAndView loginCheckmyNutrition(Integer month,Integer day,HttpSession session) {
	      ModelAndView mav = new ModelAndView();
	      Calendar cal = Calendar.getInstance();
	      int startNum=0;
	      int lastNum=0;
	      String m=null;
	      // iYear : current Year, iMonth : current Month (january is 0), 1 : one day
	      if(month==null) {
	          month=(cal.get(Calendar.MONTH)+1);
	          cal.set(2021, month-1, 1);
	          m=(month<10?"0"+month:month+"");
	      }else {
	         cal.set(2021, month-1, 1); // iYear : current Year, iMonth : current Month (1월은 0), 1 : one day
	         m=month<10?"0"+month:month+"";
	         
	      }
	      startNum = cal.get(Calendar.DAY_OF_WEEK)-1;
	      lastNum= cal.getActualMaximum(Calendar.DATE);
	       cal = Calendar.getInstance();
	       
	      if(day==null)
	       day =cal.get(Calendar.DAY_OF_MONTH);
	      cal.set(2021, month,day); 
	      int week =(startNum+day-1)/7;
	      mav.addObject("month",m);
	      mav.addObject("day",day);
	      mav.addObject("week",week);
	      mav.addObject("start",startNum);
	      mav.addObject("end",lastNum);
	       List<Eatfood> nutlist = new ArrayList<Eatfood>();
	       User loginUser = (User)session.getAttribute("loginUser");
	       
	          for(int i=1;i<lastNum;i++) {
	              String date ="";
	              date="2021-"+m+"-"+(i<10?"0"+i:i);
//	              List<FoodList> foodlist = service.eatFoodBydate(date,"1");
	             List<FoodList> foodlist = service.eatFoodBydate(date,loginUser.getId());
	             Eatfood e = new Eatfood();
	              e.setDate(date);
	             e.setFoodlist(foodlist);
	             nutlist.add(e);
	          }

	       nutlist.sort((x,y) -> x.getDate().compareTo(y.getDate()) );
//	       double height =1.58;
//	       double weight =52;
//	       double pa =1.12;
//	       int age = 52;
//	       double userCal = 354-(6.91*age) +pa*(9.36*weight+ 726*height); //성인여성
	       //pa : 1.0 (비활동적) , 1.12(저활동적) , 1.26(활동적) , 1.46(매우 활동적)
//	       userCal = 662-(9.53*age) +pa*(15.91*weight+ 539.6*height); //성인남성
	       mav.addObject("list",nutlist);
	      return mav;
	   }
	@RequestMapping("Insertnutrition")
	public ModelAndView insertnu(String menu) throws IOException {
		ModelAndView mav = new ModelAndView();
		for(int i = 1 ; i<227;i++) {
		URL url = new URL("http://apis.data.go.kr/1470000/FoodNtrIrdntInfoService/getFoodNtrItdntList?servicekey=sMwwxot%2BF5FgPv7B89tUkYWKjTvpbvJt%2FU0lUr9BCPdS7U3eEV3%2FgFNTVwHEkxcKlRzv7OP%2FOFfNUuD6h8%2F0IQ%3D%3D&numOfRows=100&pageNo="+i);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/xml");
		System.out.println("Response code: " + conn.getResponseCode());
		BufferedReader rd;
		List<Nutrition> list= new ArrayList<Nutrition>();
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		Document doc = null;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		rd.close();
		conn.disconnect();
		try{
			doc = Jsoup.parse(sb.toString());
			for(Element ele : doc.select("item")) {
				String com = ele.select("ANIMAL_PLANT").text();
				StringBuilder n= new StringBuilder();
				n.append(ele.select("desc_kor").text());
				if(!com.equals(""))
					n.append("(").append(com).append(")");
					Nutrition rec =  Nutrition.build()
						.name(n.toString())
						.animal_plant(ele.select("animal_plant").text())
						.protein(Float.parseFloat(ele.select("NUTR_CONT3").text()))
						.carbohyd(Float.parseFloat(ele.select("NUTR_CONT2").text()))
						.fat(Float.parseFloat(ele.select("NUTR_CONT4").text()))
						.calorie(Float.parseFloat(ele.select("NUTR_CONT1").text()))
						.build();
				list.add(rec);
			}
		}catch(Exception e){e.printStackTrace();}
		service.insertNutrition(list);
	}
		return mav;
		
	}
	@RequestMapping("Insertnutrition2")
	public ModelAndView insertnu2(String menu) throws IOException {
		ModelAndView mav = new ModelAndView();
		for(int i = 1 ; i<53000;i+=1000) {
			URL url = new URL("http://openapi.foodsafetykorea.go.kr/api/b6651b0104e9452b917f/I2790/xml/"+i+"/"+(i+999));
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/xml");
			System.out.println("Response code: " + conn.getResponseCode());
			BufferedReader rd;
			List<Nutrition> list= new ArrayList<Nutrition>();
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(),"UTF-8"));
			}
			StringBuilder sb = new StringBuilder();
			String line;
			Document doc = null;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			try{
				doc = Jsoup.parse(sb.toString());
				for(Element ele : doc.select("row")) {
					String cal = (ele.select("NUTR_CONT1").text() ==null)?"0.0":ele.select("NUTR_CONT1").text();
					String car = (ele.select("NUTR_CONT2").text() ==null)?"0.0":ele.select("NUTR_CONT2").text();
					String pro = (ele.select("NUTR_CONT3").text() ==null)?"0.0":ele.select("NUTR_CONT3").text();
					String fat = (ele.select("NUTR_CONT4").text() ==null)?"0.0":ele.select("NUTR_CONT4").text();
					 cal = (cal.split(".").length==1)?cal+".0":cal;
					 car = (car.split(".").length==1)?car+".0":car;
					 pro = (pro.split(".").length==1)?pro+".0":pro;
					 fat = (fat.split(".").length==1)?fat+".0":fat;
					try {
					Nutrition rec =  Nutrition.build()
							.name(ele.select("DESC_KOR").text())
							.protein(Float.parseFloat(pro))
							.carbohyd(Float.parseFloat(car))
							.fat(Float.parseFloat(fat))
							.calorie(Float.parseFloat(cal))
							.build();
					list.add(rec);
					}catch(NumberFormatException e1) {e1.printStackTrace();}
				}
			}catch(Exception e2){e2.printStackTrace();}
			service.insertNutrition(list);
		}
		return mav;
		
	}
}
