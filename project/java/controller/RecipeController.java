package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import logic.CustomRecipe;
import logic.LsjService;
import logic.Nutrition;
import logic.Recipe;


@Controller
@RequestMapping("rcp")
public class RecipeController {
	@Autowired
	private LsjService service;
	@GetMapping("*")
	public String all() {
		return null;
	}
	
	@GetMapping("custom")
	public String loginCheckCustom(HttpSession session) {
		return null;
	}
	@GetMapping("update")
	public ModelAndView idCheckupdate(int no,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		CustomRecipe rcp = service.getCusRecipe(no);
		List<String> rec = new ArrayList<String>();
		List<String> recp = new ArrayList<String>();
		for (String s : rcp.getRecipe().split(";"))
			rec.add(s);
		for (String s : rcp.getRecipe_pic().split(";"))
			recp.add(s);
		mav.addObject("length", rec.size()-1);
		mav.addObject("foodpic",rcp.getFood_Pic());
		mav.addObject("rec", rec);
		mav.addObject("recp", recp);
		mav.addObject("menu", rcp.getName());
		mav.addObject("rcp", rcp);
		System.out.println(rec);
		System.out.println(recp);
		return mav;
	}
	@PostMapping("delrcp")
	public ModelAndView idCheckdelEatfood(int no,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {
		service.delrcp(no);
		mav.addObject("msg","성공");
		mav.addObject("url","custommenu.shop");
		}catch(Exception e)  { e.printStackTrace();mav.addObject("msg","실패");
		mav.addObject("url","customRcp.shop?no="+no);
		}
		mav.setViewName("alert");
		return mav;
	}

	
	@RequestMapping("InsertRecipe")
	public ModelAndView insert(String menu) throws IOException {
		ModelAndView mav = new ModelAndView();
		 URL url = new URL("http://openapi.foodsafetykorea.go.kr/api/b6651b0104e9452b917f/COOKRCP01/xml/1/1000");
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("GET");
	        conn.setRequestProperty("Content-type", "application/xml");
	        System.out.println("Response code: " + conn.getResponseCode());
	        BufferedReader rd;
	        List<Recipe> list= new ArrayList<Recipe>();
	        List<Nutrition> nutlist= new ArrayList<Nutrition>();
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
	    			String rceipe = "";
	    			String recipe_pic ="";
	    			String temp;
	    			for(int i=1;i<19;i++) {
	    				 temp =ele.select("MANUAL0" + i).text();
					if (temp.equals("")) {
						break;
					} else {
						rceipe += temp + ";";
						recipe_pic += ele.select("MANUAL_IMG0" + i).text() + ';';
					}
				}
				for (int i = 10; i < 19; i++) {
					temp = ele.select("MANUAL" + i).text();
					if (temp.equals("")) {
						break;
					} else {
						rceipe += temp + ";";
						recipe_pic += ele.select("MANUAL_IMG" + i).text() + ';';
					}
				}
				Recipe rec = Recipe.build().name(ele.select("RCP_NM").text())
						.protein(Float.parseFloat(ele.select("INFO_PRO").text()))
						.carbohyd(Float.parseFloat(ele.select("INFO_CAR").text()))
						.fat(Float.parseFloat(ele.select("INFO_FAT").text()))
						.calorie(Float.parseFloat(ele.select("INFO_ENG").text())).recipe(rceipe)
						.raw(ele.select("RCP_PARTS_DTLS").text()).recipe_pic(recipe_pic)
						.food_pic(ele.select("ATT_FILE_NO_MK").text()).recpat(ele.select("RCP_PAT2").text())
						.recway(ele.select("RCP_WAY2").text()).build();
				list.add(rec);
				Nutrition nut = Nutrition.build().name(ele.select("RCP_NM").text())
						.protein(Float.parseFloat(ele.select("INFO_PRO").text()))
						.carbohyd(Float.parseFloat(ele.select("INFO_CAR").text()))
						.fat(Float.parseFloat(ele.select("INFO_FAT").text()))
						.calorie(Float.parseFloat(ele.select("INFO_ENG").text())).build();
				nutlist.add(nut);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		url = new URL("http://openapi.foodsafetykorea.go.kr/api/b6651b0104e9452b917f/COOKRCP01/xml/1001/1300");
		conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/xml");
		if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		} else {
			rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
		}
		StringBuilder sb2 = new StringBuilder();
		while ((line = rd.readLine()) != null) {
			sb2.append(line);
		}
		rd.close();
		conn.disconnect();
		try {
			doc = Jsoup.parse(sb2.toString());

			for (Element ele : doc.select("row")) {
				String rceipe = "";
				String recipe_pic = "";
				String temp;
				for (int i = 1; i < 19; i++) {
					temp = ele.select("MANUAL0" + i).text();
					if (temp.equals("")) {
						break;
					} else {
						rceipe += temp + ";";
						recipe_pic += ele.select("MANUAL_IMG0" + i).text() + ';';
					}
				}
				for (int i = 10; i < 19; i++) {
					temp = ele.select("MANUAL" + i).text();
					if (temp.equals("")) {
						break;
					} else {
						rceipe += temp + ";";
						recipe_pic += ele.select("MANUAL_IMG" + i).text() + ';';
					}
				}
				Recipe rec = Recipe.build().name(ele.select("RCP_NM").text())
						.protein(Float.parseFloat(ele.select("INFO_PRO").text()))
						.carbohyd(Float.parseFloat(ele.select("INFO_CAR").text()))
						.fat(Float.parseFloat(ele.select("INFO_FAT").text()))
						.calorie(Float.parseFloat(ele.select("INFO_ENG").text())).recipe(rceipe)
						.raw(ele.select("RCP_PARTS_DTLS").text()).recipe_pic(recipe_pic)
						.food_pic(ele.select("ATT_FILE_NO_MK").text()).recpat(ele.select("RCP_PAT2").text())
						.recway(ele.select("RCP_WAY2").text()).build();
				list.add(rec);
				Nutrition nut = Nutrition.build().name(ele.select("RCP_NM").text())
						.protein(Float.parseFloat(ele.select("INFO_PRO").text()))
						.carbohyd(Float.parseFloat(ele.select("INFO_CAR").text()))
						.fat(Float.parseFloat(ele.select("INFO_FAT").text()))
						.calorie(Float.parseFloat(ele.select("INFO_ENG").text())).build();
				nutlist.add(nut);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		service.insertRecipe(list);
		service.insertNutrition(nutlist);
		return mav;

	}

	@GetMapping("menu")
	public ModelAndView menu(Integer pageNum, String rawlist[], String check1[], String check2[], String order,
			String asc) throws IOException {
		ModelAndView mav = new ModelAndView();
		if (pageNum == null || pageNum.toString().equals("")) {
			pageNum = 1;
		}
		String raw = "";
		List<String> r = new ArrayList<String>();
		if (rawlist != null) {
			for (String s : rawlist) {
				if (!s.equals(""))
					r.add(s);
			}
		}
		int i = 0;
		for (String s : r) {
			i++;
			raw += (i < r.size()) ? s + "," : s;
		}
		if (raw.equals("")) {
			raw = null;
		}
		List<Recipe> list = service.getMenu(raw, check1, check2, pageNum, 10, order, asc);
		int listcount = service.menucount(raw, check1, check2);
		List<String> way = new ArrayList<String>();
		List<String> pat = new ArrayList<String>();
		if (check1 != null) {
			for (String s : check1)
				way.add(s);
		}
		if (check2 != null) {
			for (String s : check2)
				pat.add(s);
		}
		int maxpage = (int) ((double) listcount / 10 + 0.95);
		int startpage = (int) ((pageNum / 10.0 + 0.9) - 1) * 10 + 1;
		int endpage = startpage + 9;
		if (endpage > maxpage)
			endpage = maxpage;
		mav.addObject("pageNum", pageNum);
		mav.addObject("startpage", startpage);
		mav.addObject("endpage", endpage);
		mav.addObject("maxpage", maxpage);
		mav.addObject("way", way);
		mav.addObject("pat", pat);
		mav.addObject("list", list);
		mav.addObject("raw", r);
		return mav;
	}

	@RequestMapping("rcp")
	public ModelAndView rcp(int no) throws IOException {
		ModelAndView mav = new ModelAndView();
		service.updateReadCnt(no);
		Recipe rcp = service.getRecipe(no);
		List<String> rec = new ArrayList<String>();
		List<String> recp = new ArrayList<String>();
		for (String s : rcp.getRecipe().split(";"))
			rec.add(s);
		for (String s : rcp.getRecipe_pic().split(";"))
			recp.add(s);
		mav.addObject("length", rec.size()-1);
		mav.addObject("rec", rec);
		mav.addObject("recp", recp);
		mav.addObject("menu", rcp.getName());
		mav.addObject("rcp", rcp);
		return mav;
	}
	@RequestMapping("customRcp")
	public ModelAndView customRcp(int no) throws IOException {
		ModelAndView mav = new ModelAndView();
		CustomRecipe rcp = service.getCusRecipe(no);
		List<String> rec = new ArrayList<String>();
		List<String> recp = new ArrayList<String>();
		for (String s : rcp.getRecipe().split(";"))
			rec.add(s);
		for (String s : rcp.getRecipe_pic().split(";"))
			recp.add(s);
		mav.addObject("length", rec.size()-1);
		mav.addObject("foodpic",rcp.getFood_Pic());
		mav.addObject("rec", rec);
		mav.addObject("recp", recp);
		mav.addObject("menu", rcp.getName());
		mav.addObject("rcp", rcp);
		System.out.println(rec);
		System.out.println(recp);
		return mav;
	}
	
	@GetMapping("custommenu")
	public ModelAndView custommenu(Integer pageNum, String rawlist[], String check1[], String check2[]) throws IOException {
		ModelAndView mav = new ModelAndView();
		if (pageNum == null || pageNum.toString().equals("")) {
			pageNum = 1;
		}
		String raw = "";
		List<String> r = new ArrayList<String>();
		if (rawlist != null) {
			for (String s : rawlist) {
				if (!s.equals(""))
					r.add(s);
			}
		}
		int i = 0;
		for (String s : r) {
			i++;
			raw += (i < r.size()) ? s + "," : s;
		}
		if (raw.equals("")) {
			raw = null;
		}
		List<CustomRecipe> list = service.getCusMenu(raw, check1, check2, pageNum, 10);
		int listcount = service.menucount(raw, check1, check2);
		List<String> way = new ArrayList<String>();
		List<String> pat = new ArrayList<String>();
		if (check1 != null) {
			for (String s : check1)
				way.add(s);
		}
		if (check2 != null) {
			for (String s : check2)
				pat.add(s);
		}
		int maxpage = (int) ((double) listcount / 10 + 0.95);
		int startpage = (int) ((pageNum / 10.0 + 0.9) - 1) * 10 + 1;
		int endpage = startpage + 9;
		if (endpage > maxpage)
			endpage = maxpage;
		mav.addObject("pageNum", pageNum);
		mav.addObject("startpage", startpage);
		mav.addObject("endpage", endpage);
		mav.addObject("maxpage", maxpage);
		mav.addObject("way", way);
		mav.addObject("pat", pat);
		mav.addObject("list", list);
		mav.addObject("raw", r);
		return mav;
	}
	

}
