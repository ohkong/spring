package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import logic.Board;
import logic.Company;
import logic.CustomRecipe;
import logic.DateData;
import logic.Eatfood;
import logic.FoodList;
import logic.KmsService;
import logic.LsjService;
import logic.OjhService;
import logic.Recipe;
import logic.User;

@RestController 
@RequestMapping("ajax")
public class AjaxController {
	@Autowired
	private OjhService shopService;
	
	@Autowired
	private KmsService kmsService;
	
	public static Map<String,String> mapper= new HashMap<>();
	static {
	mapper.put("Industrials", "원료가공");
	mapper.put("Healthcare", "헬스케어");
	mapper.put("Technology", "기술");
	mapper.put("Communication Services", "의사소통 서비스");
	mapper.put("Consumer Cyclical", "경기소비재");
	mapper.put("Financial", "금융");
	mapper.put("Basic Materials", "공업원료");
	mapper.put("Real Estate", "부동산");
	mapper.put("Utilities", "지원 프로그램");
	mapper.put("Consumer Defensive", "경기 방어주");
	mapper.put("Energy", "에너지");
	}
	@Autowired
	private LsjService service;
	
	@RequestMapping(value="piegraph",produces="text/plain;charset=UTF-8")
	public StringBuilder piegraph(String day) {
		System.out.println(day);
		 List<FoodList> foodlist = service.eatFoodBydate(day,"1");
		 Eatfood e = new Eatfood();
		 e.setFoodlist(foodlist);
		 float car = e.getTotal().getCarbohyd();
		 float pro = e.getTotal().getProtein();
		 float fat = e.getTotal().getFat();
		StringBuilder json = new StringBuilder("[");
		 json.append("{\"data\":\"탄수화물\","+"\"cnt\":"+car+"},");
		 json.append("{\"data\":\"단백질\","+"\"cnt\":"+pro+"},");
		 json.append("{\"data\":\"지방\","+"\"cnt\":"+fat+"}");
		 json.append("]");
		return json;
	}
	
	@RequestMapping(value="recipeupdate", method=RequestMethod.POST)
	public int update(CustomRecipe rec,HttpServletRequest request) {
		service.customUpdate(rec,request);
		System.out.println(rec);
		return RESULT_SUCCESS;
	}
	
	@RequestMapping(value="thistot",produces="text/plain;charset=UTF-8")
	public StringBuilder cal(HttpSession session,DateData dateData) {
		User loginUser = (User)session.getAttribute("loginUser");
		String id = loginUser.getId();
		Calendar cal2 = Calendar.getInstance();
		if(dateData.getDate().equals("")&&dateData.getMonth().equals("")){
			dateData = new DateData(String.valueOf(cal2.get(Calendar.YEAR)),
					String.valueOf(cal2.get(Calendar.MONTH)),String.valueOf(cal2.get(Calendar.DATE)),null);
		}
		Map<String, Integer> today_info =  dateData.today_info(dateData);
		int year = today_info.get("search_year");
		int month = today_info.get("search_month");
		String thmon = year + "-" + String.format("%02d",month) + "-%";
		System.out.println(thmon);
		int tmontot1 = shopService.tmontot(id,thmon); //수입합
		int tmontot2 = shopService.tmontot2(id,thmon);	//지출합
		StringBuilder json = new StringBuilder("[");
		json.append("{\"data\":\"수입 합\","+"\"cnt\":"+tmontot1+"},");
		json.append("{\"data\":\"지출 합\","+"\"cnt\":"+tmontot2+"}");
		json.append("]");
		System.out.println(json);
		return json;
	}
	
	@RequestMapping(value="todaySector2",produces="text/plain;charset=UTF-8")
	public StringBuilder todaySector2(String day) {
		Map<String,Object> map=service.getTodaySector2(day);
		int j= 0;
		StringBuilder json = new StringBuilder("[");
		for(String key : map.keySet()) {
			json.append("{\"data\":\""+key+"\","+"\"cnt\":"+map.get(key)+"}");
			j++;
			if(j<map.size()) json.append(",");
		}
		json.append("]");
		System.out.println(json);
		return json;
	}
	@RequestMapping(value="todaySector",produces="text/plain;charset=UTF-8")
	public StringBuilder todaySector(String day) {
		Map<String,Object> map=service.getTodaySector(day);
		int j= 0;
		StringBuilder json = new StringBuilder("[");
		for(String key : map.keySet()) {
			json.append("{\"data\":\""+key+"\","+"\"cnt\":"+map.get(key)+"}");
			j++;
			if(j<map.size()) json.append(",");
		}
		json.append("]");
		System.out.println(json);
		return json;
	}
    private static final int RESULT_EXCEED_SIZE = -2;
    private static final int RESULT_UNACCEPTED_EXTENSION = -1;
    private static final int RESULT_SUCCESS = 1;
    private static final long LIMIT_SIZE = 10 * 1024 * 1024;
	@RequestMapping(value="recipeupload", method=RequestMethod.POST)
	public int upload(CustomRecipe rec,HttpServletRequest request) {
	       long sizeSum = 0;
	        for(MultipartFile image : rec.getRecipePicList()) {
	            String originalName = image.getOriginalFilename();
	            //확장자 검사
	            if(!isValidExtension(originalName)){
	                return RESULT_UNACCEPTED_EXTENSION;
	            }
	            
	            //용량 검사
	            sizeSum += image.getSize();
	            if(sizeSum >= LIMIT_SIZE) {
	                return RESULT_EXCEED_SIZE;
	            }
	        }
	        rec.setId("123");
	        service.customWrite(rec,request);
	        System.out.println(rec);
	        return RESULT_SUCCESS;
	}
	
    private boolean isValidExtension(String originalName) {
        String originalNameExtension = originalName.substring(originalName.lastIndexOf(".") + 1);
        switch(originalNameExtension) {
        case "jpg":
        case "png":
        case "gif":
            return true;
        }
        return false;
    }
	
	
	@RequestMapping(value="nuttable")
		public  List<Object> loginUserNuttable(String day,HttpSession session) {
		System.out.println(day);
		List<Object> list = new ArrayList<Object>(); 
		User loginuser = (User) session.getAttribute("loginUser");
		List<FoodList> foodlist = service.eatFoodBydate(day,loginuser.getId());
		 Eatfood e = new Eatfood();
		 String str ="";
		 e.setFoodlist(foodlist);
		 StringBuilder json2 = new StringBuilder("[");
		 float car = e.getTotal().getCarbohyd();
		 float pro = e.getTotal().getProtein();
		 float fat = e.getTotal().getFat();
		 if(e.getCarbohydRate()<0.55) {
			 str += "탄수화물 섭취량이 부족 합니다. <br>";
		 }
		 else if(e.getCarbohydRate()>0.65) {
			 str += "탄수화물 섭취를 줄일 필요가 있습니다. <br>";
		 }
		 if(e.getProteinRate()<0.07) {
			 str += "단백질 섭취량이 부족 합니다.  <br>";
		 }
		 else if(e.getProteinRate()>0.2) {
			 str +="단백질 섭취를 줄일 필요가 있습니다. <br>";
		 }
		 
		 if(e.getFatRate()<0.2) {
			 str +="지방 섭취량이 부족 합니다.  <br>";
		 }else if(e.getFatRate()>0.35) {
			 str += "지방 섭취를 줄일 필요가 있습니다. <br>";
		 }
		 json2.append("{\"data\":\"탄수화물\","+"\"cnt\":"+car+"},");
		 json2.append("{\"data\":\"단백질\","+"\"cnt\":"+pro+"},");
		 json2.append("{\"data\":\"지방\","+"\"cnt\":"+fat+"}");
		 json2.append("]");
		StringBuilder json = new StringBuilder("");
		 json.append("<table <thead>");
		 json.append("<tr><th width='30%'>식품명</th>");
		 json.append("<th width='15%'>칼로리</th>");
		 json.append("<th width='15%'> 탄수화물</th>");
		 json.append("<th width='15%'> 단백질</th>");
		 json.append("<th width='15%'> 지방</th>");
		 json.append("<th width='10%'>삭제</th></tr></thead><tbody>");
		 for(FoodList f : foodlist) {
			 json.append("<tr><td class='title'>");
			 json.append(f.getName());
			 json.append("</td><td>");
			 json.append(f.getCalorie());
			 json.append("</td><td>");
			 json.append(f.getCarbohyd());
			 json.append("</td><td>");
			 json.append(f.getProtein());
			 json.append("</td><td>");
			 json.append(f.getFat());
			 json.append("</td><td><input type=\"button\" value=\"삭제\" class=\"small\"  onclick='del(");
			 json.append(f.getNo());
			 json.append(")'></td></tr>");
		 }
		 json.append("</tbody>");
		 json.append("</table");
		list.add(json.toString());
		list.add(json2.toString());
		list.add(str);
		System.out.println(json);
		System.out.println(json2);
		 return list;
	}
	
	
	@RequestMapping(value="recommendRcp",produces="text/plain;charset=UTF-8")
	public Object recommendRcp() {
		StringBuilder sb = new StringBuilder();
			List<Recipe> list =service.recomandRcp();
			System.out.println(list);
			int i =1;
			
			
			sb.append("<table> <thead>");
			sb.append("<tr><th width='20%' style='text-align:center'>순위</th>");
			sb.append("<th width='60%' style='text-align:center'>식품명</th>");
			sb.append("<th width='20%' style='text-align:center'>조회수</th></tr></thead><tbody>");
			for(Recipe r : list) {
				sb.append("<tr><td class='title' style='text-align:center'>");
				sb.append(i);
				sb.append("</td><td style='text-align:center'><a href='../rcp/rcp.shop?no=");
				sb.append(r.getNo());
				sb.append("'>");
				sb.append(r.getName());
				sb.append("</a></td><td style='text-align:center'>");
				sb.append(r.getReadcnt());
				sb.append("</td></tr>");
				i++;
			}
			sb.append("</tbody></table>");
			System.out.println(sb);
		return sb.toString();
	}
	
	
	@RequestMapping(value="recommendBoard",produces="text/plain;charset=UTF-8")
	public Object recommendBoard() {
		StringBuilder sb = new StringBuilder();
			List<Board> list = shopService.recomandBoard();
			System.out.println(list);
			int i =1;
			
			sb.append("<table> <thead>");
			sb.append("<tr><th width='20%' style='text-align:center'>순위</th>");
			sb.append("<th width='60%' style='text-align:center'>게시글 제목</th>");
			sb.append("<th width='20%' style='text-align:center'>조회수</th></tr></thead><tbody>");
			for(Board b : list) {
				sb.append("<tr><td class='title' style='text-align:center'>");
				sb.append(i);
				sb.append("</td><td style='text-align:center'><a href='../board/detail.shop?num=");
				sb.append(b.getNum());
				sb.append("'>");
				sb.append(b.getSubject());
				sb.append("</a></td><td style='text-align:center'>");
				sb.append(b.getReadcnt());
				sb.append("</td></tr>");
				i++;
			}
			sb.append("</tbody></table>");
			System.out.println(sb);
		return sb.toString();
	}
	
	
	@RequestMapping(value="bargraph",produces="text/html;charset=UTF-8")
	public StringBuilder bargraph(String classi) {
		  String url = "https://finviz.com/map.ashx";
		  Map<String,Object> data = new  HashMap<String, Object>();
		  double avg=0;
	        try{
	        	Document doc = Jsoup.connect(url).get();
	        	String text  = doc.toString();
	    		int beginIndex = text.indexOf("var FinvizMapPerf");
	    		text = text.substring(beginIndex);
	    		int start =text.indexOf("{")+1;
	    		int end =text.indexOf("}");
	    		String str = text.substring(start,end);
	    		String[] list=str.split(",");
	    		Map<String,Double> map = new TreeMap<String,Double>();
	    		for(String t : list) {
	    			map.put(t.split(":")[0].replace("\"", ""),Double.parseDouble(t.split(":")[1] ));
	    		}
	    		List<Company> com = service.Comlist(classi);
	    		double sum =0;
	    		for(int i =0 ; i<com.size();i++) {
	    			com.get(i).setClassify1(mapper.get(com.get(i).getClassify1()));
	    		}
	    		try {
		    		for(Company s:com) {
		    			data.put(s.getName(),map.get(s.getInitial()) );
		    			sum+= map.get(s.getInitial());
		    		}
		    		 avg = sum/com.size();
	    		}catch(Exception e) {
	    			e.printStackTrace();
	    		}
	        }catch(Exception e){e.printStackTrace();}
		StringBuilder json = new StringBuilder("[");
		int i = 0;
		for(Entry<String, Object> m : data.entrySet()) {
			json.append("{\"data\":\""+m.getKey()+"\","+"\"cnt\":"+m.getValue()+"}");
			i++;
			if(i<data.size()) json.append(",");
		}
		json.append("]");
		return json;
	}

	
	@GetMapping(value = "menuajax", produces = "text/html;charset=UTF-8")
	public ModelAndView menu(Integer pageNum,String raw,String check1[], String check2[],String order,String asc) throws IOException {
		ModelAndView mav = new ModelAndView();
		if(pageNum==null || pageNum.toString().equals("")) {
			pageNum=1;
		}
		List<Recipe> list=service.getMenu(raw,check1,check2,pageNum,10,order,asc);
		int listcount =service.menucount(raw,check1,check2);
		List<String> way=new ArrayList<String>();
		List<String> pat=new ArrayList<String>();
		if(check1!=null) {
			for(String s : check1)
				way.add(s);
		}
		if(check2!=null) {
			for(String s : check2)
				pat.add(s);
		}
		int maxpage = (int) ((double)listcount/10 + 0.95);
		int startpage=(int)((pageNum/10.0 + 0.9) -1) * 10 +1; 
		int endpage = startpage +9;
		if(endpage > maxpage) endpage = maxpage;
		mav.addObject("pageNum",pageNum);
		mav.addObject("startpage",startpage);
		mav.addObject("endpage",endpage);
		mav.addObject("maxpage",maxpage);
		mav.addObject("way",way);
		mav.addObject("pat",pat);
		mav.addObject("list",list);
		return mav;
	}
	
	
	@RequestMapping(value = "mone1", produces = "text/html;charset=UTF-8")
	public String mone1() {
		Document doc = null;
		List<String> list = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		String url = "http://finance.moneta.co.kr/saving/bestIntCat02List.jsp?accu_meth=1&kid_edu_fg=&join_term=1Y&rgn_sido=&rgn_gugun=&org_grp_cd=BK&saving_amt=100000";
		try {
				doc = Jsoup.connect(url).get();
			Elements e1 = doc.select(".tb_table_water");
			Elements e2 = e1.select("tr");
			Elements e3 = e2.select("td");
			for(Element ele : e3) {
				list.add(ele.html());
			}
			Elements e4 = e2.select(".ablue");
			for(Element ele2 : e4) {
				list2.add(ele2.html());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("list : "+list);
		System.out.println("list2 : "+list2);
		StringBuilder html = new StringBuilder();
		html.append("<table>");
		html.append("<tr><th style='text-align:center'>번호</th>"
				+ "<th style='text-align:center'>금융기관 명</th>");
		html.append("<th style='text-align:center'>상품 명</th><th style='text-align:center'>세전금리</th>"
				+ "<th style='text-align:center'>세후수령액</th></tr>");
		int j=0;
		for(int i=6;i<list.size();i+=6) {
			html.append("<tr>");
			html.append("<td style='text-align:center'>" + list.get(i+1)+"</td>");
			html.append("<td style='text-align:center'>" + list2.get(j)+"</td>");
			html.append("<td style='text-align:center'>" + list2.get(j+1)+"</td>");
			html.append("<td style='text-align:center'>" + list.get(i+4)+"</td>");
			html.append("<td style='text-align:center'>" + list.get(i+5)+"</td>");
			html.append("</tr>");
			j+=2;
		}
		html.append("</table>");
		return html.toString();
	}
	
	
	@RequestMapping(value = "mone2", produces = "text/html;charset=UTF-8")
	public String mone2() {
		Document doc = null;
		List<String> list = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		String url = "http://finance.moneta.co.kr/saving/bestIntCat02List.jsp?accu_meth=1&kid_edu_fg=&join_term=1Y&rgn_sido=&rgn_gugun=&org_grp_cd=SF&saving_amt=100000";
		try {
			doc = Jsoup.connect(url).get();
			Elements e1 = doc.select(".tb_table_water");
			Elements e2 = e1.select("tr");
			Elements e3 = e2.select("td");
			for(Element ele : e3) {
				list.add(ele.html());
			}
			Elements e4 = e2.select(".ablue");
			for(Element ele2 : e4) {
				list2.add(ele2.html());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("list : "+list);
		System.out.println("list2 : "+list2);
		StringBuilder html = new StringBuilder();
		html.append("<table>");
		html.append("<tr><th style='text-align:center'>번호</th>"
				+ "<th style='text-align:center'>금융기관 명</th>");
		html.append("<th style='text-align:center'>상품 명</th><th style='text-align:center'>세전금리</th>"
				+ "<th style='text-align:center'>세후수령액</th></tr>");
		int j=0;
		for(int i=6;i<list.size();i+=6) {
			html.append("<tr>");
			html.append("<td style='text-align:center'>" + list.get(i+1)+"</td>");
			html.append("<td style='text-align:center'>" + list2.get(j)+"</td>");
			html.append("<td style='text-align:center'>" + list2.get(j+1)+"</td>");
			html.append("<td style='text-align:center'>" + list.get(i+4)+"</td>");
			html.append("<td style='text-align:center'>" + list.get(i+5)+"</td>");
			html.append("</tr>");
			j+=2;
		}
		html.append("</table>");
		return html.toString();
	}
	
	@RequestMapping(value = "mone3", produces = "text/html;charset=UTF-8")
	public String mone3() {
		Document doc = null;
		List<String> list = new ArrayList<>();
		List<String> list2 = new ArrayList<>();
		String url = "http://finance.moneta.co.kr/saving/bestIntCat02List.jsp?accu_meth=1&kid_edu_fg=&join_term=1Y&rgn_sido=&rgn_gugun=&org_grp_cd=CU&saving_amt=100000";
		try {
			doc = Jsoup.connect(url).get();
			Elements e1 = doc.select(".tb_table_water");
			Elements e2 = e1.select("tr");
			Elements e3 = e2.select("td");
			for(Element ele : e3) {
				list.add(ele.html());
			}
			Elements e4 = e2.select(".ablue");
			for(Element ele2 : e4) {
				list2.add(ele2.html());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("list : "+list);
		System.out.println("list2 : "+list2);
		StringBuilder html = new StringBuilder();
		html.append("<table>");
		html.append("<tr><th style='text-align:center'>번호</th>"
				+ "<th style='text-align:center'>금융기관 명</th>");
		html.append("<th style='text-align:center'>상품 명</th><th style='text-align:center'>세전금리</th>"
				+ "<th style='text-align:center'>세후수령액</th></tr>");
		int j=0;
		for(int i=6;i<list.size();i+=6) {
			html.append("<tr>");
			html.append("<td style='text-align:center'>" + list.get(i+1)+"</td>");
			html.append("<td style='text-align:center'>" + list.get(i+2)+"</td>");
			html.append("<td style='text-align:center'>" + list2.get(j)+"</td>");
			html.append("<td style='text-align:center'>" + list.get(i+4)+"</td>");
			html.append("<td style='text-align:center'>" + list.get(i+5)+"</td>");
			html.append("</tr>");
			j++;
		}
		html.append("</table>");
		return html.toString();
	}
	
	
	
}



