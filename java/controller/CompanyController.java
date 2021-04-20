package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.LsjService;
import logic.Company;


@Controller
@RequestMapping("company")
public class CompanyController {
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
	@RequestMapping("main")
	public String layout() {
		return "company/img";
	}
	@RequestMapping("e")
	public ModelAndView insert(String menu) throws IOException {
		ModelAndView mav = new ModelAndView();
		  String url = "https://finviz.com/map.ashx";
	        try{
	        	Document doc = Jsoup.connect(url).get();
	        	String text  = doc.toString();
	    		int beginIndex = text.indexOf("var FinvizMapPerf");
	    		text = text.substring(beginIndex);
	    		int start =text.indexOf("{")+1;
	    		int end =text.indexOf("}");
	    		String str = text.substring(start,end);
	    		String[] list=str.split(",");
	    		Map<String,Float> map = new TreeMap<String,Float>();
	    		for(String t : list) {
	    			map.put(t.split(":")[0].replace("\"", ""),Float.parseFloat(t.split(":")[1] ));
	    		}
	    		for(String st: map.keySet()) {
	    			url ="https://finviz.com/quote.ashx?t="+st;
	    			doc = Jsoup.connect(url).get();
	    			Elements ele=doc.select(".fullview-title");
	    			ele = ele.select("td");
	    			String[] init = ele.get(0).text().split(" ");
	    			Company com =  Company.builder().name(ele.get(1).text()).
	    					initial(init[0])
	    					.firm(init[1])
	    					.classify1(ele.get(2).select("a").get(0).text())
	    					.classify2(ele.get(2).select("a").get(1).text())
	    					.country(ele.get(2).select("a").get(2).text())
	    					.build();
	    			service.insertCompany(com);

	    		}
	        }catch(Exception e){e.printStackTrace();}
		return mav;
	}
	@GetMapping("today")
	public void tody() {
		double spavg =0;
		double sptotal =0;
		double dowavg =0;
		double dowtotal =0;
		Map<String,Double> map = getsp500();
		
		for(String classi : mapper.keySet()) {
			List<Company> spcom = service.Comlist(classi);
			List<Company> dowcom = new ArrayList<Company>();
			String b= "";
			if(classi !=null) {
				String[] param = classi.split(" ");
				for(String s : param)
					b+= s;
			}
			String url = "https://finviz.com/screener.ashx?v=111&o=-volume&f=idx_dji,sec_"+b;
			dowcom = getDow(url);
			double spsum =0;
			double dowsum =0;
			
			for(Company c : spcom) {
				spsum+= map.get(c.getInitial());
			}
			for(Company c : dowcom) {
				dowsum+=c.getVal();
			}
			dowavg= dowsum/dowcom.size();
			if(dowcom.size()==0){
				dowavg=0;
			}
		
			spavg = spsum/spcom.size();
			sptotal+=spavg;
			dowtotal+=spavg;
			service.insertsp500(mapper.get(classi),spavg);
			service.insertdowJones(mapper.get(classi),dowavg);
		
	}
		service.insertsp500("전체",sptotal/mapper.size());
		service.insertdowJones("전체",dowtotal/mapper.size());
		
		
		
	}

	@RequestMapping("onepage")
	public ModelAndView onepage(String v,String classi) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("cls",mapper);

		if(classi !=null) {
			if(classi.equals(""))
				classi =null;
				}
		if(v==null) {
			v="S&P";
		}
		List<Company> com = new ArrayList<Company>();
		StringBuilder json = new StringBuilder("[");
		StringBuilder json2 = new StringBuilder("[");
		double avg=0;
		double sum=0;
		int j=0;
		if(v.equals("dow")) {
			
			String b ="";
			if(classi !=null) {
				String[] param = classi.split(" ");
				for(String s : param)
					b+= s;
			}
			String url = "https://finviz.com/screener.ashx?v=111&o=-volume&f=idx_dji,sec_"+b;
			if(b.equals("")){
				 url = "https://finviz.com/screener.ashx?v=111&o=-volume&f=idx_dji";
			}
				
			com = getDow(url);
			if(b.equals("")) {
				List<Company> com2 = getDow("https://finviz.com/screener.ashx?v=111&f=idx_dji&o=-volume&r=21");
				for(Company c: com2) {
					com.add(c);
				}
			}
			
			if(com.size()==0) {
				System.out.println("데이터가 없습니다.");
				mav.addObject("no",1);
				return mav;
				}
			
			for(Company c : com) {
				sum+=c.getVal();
				json.append("{\"data\":\""+c.getInitial()+"\","+"\"cnt\":"
						+c.getVal()+"}");
				j++;
				if(j<com.size()) json.append(",");
				}
			avg= sum/com.size();
			if(com.size()==0){
				avg=0;
			}
			service.insertdowJones(classi==null?"전체":mapper.get(classi), avg);
		}
		else{

			Map<String,Double> map = getsp500();
			com = service.Comlist(classi);
    		for(int i =0 ; i<com.size();i++) {
			com.get(i).setClassify1(mapper.get(com.get(i).getClassify1()));
			com.get(i).setVal(map.get(com.get(i).getInitial()));
			sum+= map.get(com.get(i).getInitial());
			
			json.append("{\"data\":\""+com.get(i).getInitial()+"\","+"\"cnt\":"+map.get(com.get(i).getInitial())+"}");
			j++;
			if(j<com.size()) json.append(",");
		}
    		avg = sum/com.size();
    		service.insertsp500(classi==null?"전체":mapper.get(classi), avg);
    		
		}
		json.append("]");
		mav.addObject("no", 2);
		mav.addObject("json",json);
		if( classi==null){
			classi="전체";
		}else {
			classi=mapper.get(classi);
		}
		
		mav.addObject("type",classi);
		
		Map<String, Object>  map2= service.bargraph2(v,classi);
		j=0;
		for(String m :map2.keySet()){
			json2.append("{\"data\":\""+m+"\","+"\"cnt\":"+map2.get(m)+"}");
			j++;
			if(j<map2.size()) json2.append(",");
		}
		json2.append("]");
		mav.addObject("json2",json2);
		mav.addObject("list",com);
		mav.addObject("avg",avg);
		return mav;
		
		
		
	}
	
	public List<Company> getDow(String url){
		List<Company> list = new ArrayList<Company>();
		try{
			Document doc = Jsoup.connect(url).get();
				Elements text  = doc.select("#screener-content table table tbody tr");
				for(Element t : text) {
					if(t.childNodeSize()==12) {
						Company c = Company.builder().initial(t.select("td").get(1).text())
								.name(t.select("td").get(2).text())
								.classify1(mapper.get(t.select("td").get(3).text()))
								.classify2(t.select("td").get(4).text())
								.price(Double.parseDouble(t.select("td").get(8).text()))
								.val(Double.parseDouble(t.select("td").get(9).text().replace("%", "")))
								.volume(Double.parseDouble(t.select("td").get(10).text().replace(",", "")))
								.build();
						list.add(c);
					}
				}
		}catch(Exception e) {e.printStackTrace();}
				
		return list;
	}
	public Map<String,Double> getsp500(){
		  String url = "https://finviz.com/map.ashx";
		  Map<String,Double> map = new TreeMap<String,Double>();
	        try{
	        	Document doc = Jsoup.connect(url).get();
	        	String text  = doc.toString();
	    		int beginIndex = text.indexOf("var FinvizMapPerf");
	    		text = text.substring(beginIndex);
	    		int start =text.indexOf("{")+1;
	    		int end =text.indexOf("}");
	    		String str = text.substring(start,end);
	    		String[] list=str.split(",");
	    		for(String t : list) {
	    			map.put(t.split(":")[0].replace("\"", ""),Double.parseDouble(t.split(":")[1] ));
	    		}
	    		}catch(Exception e) {e.printStackTrace();}
	        return map;
	}
}
