package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import logic.Cal;
import logic.DateData;
import logic.OjhService;
import logic.User;

@Controller
@RequestMapping("cal")
public class CalController {
	@Autowired
	OjhService service;
	
	@GetMapping("*")
	public ModelAndView loginCheckall(String id,Cal cal) {
		ModelAndView mav = new ModelAndView();
		User user = service.selectUser(id);
		mav.addObject("cal",cal);
		mav.addObject("loginUser",user);
		return mav;
	}
	
	@RequestMapping("cal")
	public ModelAndView loginCheckCal(Cal cal,Model model, 
							HttpServletRequest request, DateData dateData,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User loginUser = (User)session.getAttribute("loginUser");
		String id = loginUser.getId();
		User user = service.selectUser(id);
		
		//월별 달력 부분
				Calendar cal2 = Calendar.getInstance();
				DateData calendarData;
				//검색 날짜
				if(dateData.getDate().equals("")&&dateData.getMonth().equals("")){
					dateData = new DateData(String.valueOf(cal2.get(Calendar.YEAR)),
							String.valueOf(cal2.get(Calendar.MONTH)),String.valueOf(cal2.get(Calendar.DATE)),null);
				}
				//검색 날짜 end
				Map<String, Integer> today_info =  dateData.today_info(dateData);
				List<DateData> dateList = new ArrayList<DateData>();
				//실질적인 달력 데이터 리스트에 데이터 삽입 시작.
				//일단 시작 인덱스까지 아무것도 없는 데이터 삽입
				for(int i=1; i<today_info.get("start"); i++){
					calendarData= new DateData(null, null, null, null);
					dateList.add(calendarData);
				}
				//날짜 삽입
				for (int i = today_info.get("startDay"); i <= today_info.get("endDay"); i++) {
					if(i==today_info.get("today")){
						calendarData= new DateData(String.valueOf(dateData.getYear()), 
								String.valueOf(dateData.getMonth()), String.valueOf(i), "today");
					}else{
						calendarData= new DateData(String.valueOf(dateData.getYear()), 
								String.valueOf(dateData.getMonth()), String.valueOf(i), "normal_date");
					}
					dateList.add(calendarData);
				}
			//	System.out.println(today_info);
				//달력 빈곳 빈 데이터로 삽입
				int index = 7-dateList.size()%7;
				if(dateList.size()%7!=0){
					for (int i = 0; i < index; i++) {
						calendarData= new DateData(null, null, null, null);
						dateList.add(calendarData);
					}
				}
				//배열에 담음
				model.addAttribute("dateList", dateList);		//날짜 데이터 배열
				model.addAttribute("today_info", today_info);
			//	System.out.println(today_info);
				System.out.println(dateList.size());
		System.out.println("today_info search_year :  "+today_info.get("search_year"));

		
		user = service.selectUser(id);
		Integer year = today_info.get("search_year");
		Integer month = today_info.get("search_month");
		String thmon = year + "-" + String.format("%02d",month) + "-%";
		String lamon = year + "-" + String.format("%02d",month-1) + "-%";
		System.out.println(lamon);
		//이번달 지난달 수입/지출 총합
		int lmontot1 = service.lmontot(user.getId(),lamon);
		int lmontot2 = service.lmontot2(user.getId(),lamon);
		int tmontot1 = service.tmontot(user.getId(),thmon);
		int tmontot2 = service.tmontot2(user.getId(),thmon);
	//	System.out.println(lmontot1);
		//변동액
		int lmonchg = lmontot1 - lmontot2;
		int tmonchg = tmontot1 - tmontot2;
		//저번달 그래프
		int liotype1 = service.lmontot(user.getId(),lamon);
		int liotype2 = service.lmontot2(user.getId(),lamon);
		StringBuilder json1 = new StringBuilder("[");
		json1.append("{\"data\":\"수입\","+"\"price\":"+liotype1+"},");
		json1.append("{\"data\":\"지출\","+"\"price\":"+liotype2+"}");
		json1.append("]");
		//저번달 그래프
		Integer tiotype1 = service.tmontot(id,thmon);
		Integer tiotype2 = service.tmontot2(id,thmon);
		StringBuilder json2 = new StringBuilder("[");
		json2.append("{\"data\":\"수입\","+"\"price\":"+tiotype1+"},");
		json2.append("{\"data\":\"지출\","+"\"price\":"+tiotype2+"}");
		json2.append("]");
		
		
		//일별 내역 부분
		List<Cal> datel = service.selectdate(id);
		List<Integer> cnt = service.daycnt(id);
		SimpleDateFormat dsdf = new SimpleDateFormat("dd");
		
		for(Cal day : datel) {
			List<Cal> daydetail = service.selectcallist(id, day.getDate());
			day.setDayList(daydetail);
		}
		System.out.println("datel    "+datel);
		
		
		//월별 일자 금액 표시
		List<String> datem = new ArrayList<String>();
		Cal day;
		for(int i=0;i<dateList.size();i++) {
			if(dateList.get(i).getYear()!="") {
				datem.add(dateList.get(i).getYear()+"-"+String.format("%02d",Integer.parseInt(dateList.get(i).getMonth())+1)
							+"-"+String.format("%02d",Integer.parseInt(dateList.get(i).getDate())));
			}else{
				datem.add("");
			}
		}
		System.out.println("datem"+datem);
		mav.addObject("datem",datem);
		
		List<Cal> datem2 = new ArrayList<Cal>();
		for(int i=0;i<dateList.size();i++) {
			day = new Cal();
			day.setDate(datem.get(i));
			datem2.add(day);
		}
		for(Cal datetot : datem2) {
				List<Cal> daydetail2 = service.selectcallist(user.getId(),datetot.getDate());
				datetot.setDayList(daydetail2);
		}
		System.out.println("datem2 "+datem2);
		mav.addObject("datem2",datem2);
				
		
		
		//카테고리별 통계 부분
		List<Cal> cate = service.selectlist(id);
		List<Cal> inkplist = service.selectkp(id,1,thmon);
		List<Cal> outkplist = service.selectkp(id,2,thmon);
		//수입 그래프
		Map<String,Object> map = service.kpgraph1(id,thmon);
		StringBuilder json3 = new StringBuilder("[");
		int i = 0;
		for(Map.Entry<String,Object> me : map.entrySet()) {
				json3.append("{\"kind\":\""+me.getKey()+"\"," 
						+	"\"price\":\"" + me.getValue() + "\"}");
				i++;
				if(i<map.size()) json3.append(",");
		}
		json3.append("]");
		System.out.println(json3);
		//지출 그래프
		Map<String,Object> map2 = service.kpgraph2(id,thmon);
		StringBuilder json4 = new StringBuilder("[");
		int j = 0;
		for(Map.Entry<String,Object> me : map2.entrySet()) {
				json4.append("{\"kind\":\""+me.getKey()+"\"," 
						+	"\"price\":\"" + me.getValue() + "\"}");
				j++;
				if(j<map2.size()) json4.append(",");
		}
		json4.append("]");
		System.out.println(json4);
		
		mav.addObject("inkplist",inkplist);
		mav.addObject("outkplist",outkplist);
		mav.addObject("json1",json1);
		mav.addObject("json2",json2);
		mav.addObject("json3",json3);
		mav.addObject("json4",json4);
		mav.addObject("lmontot1",lmontot1);
		mav.addObject("lmontot2",lmontot2);
		mav.addObject("tmontot1",tmontot1);
		mav.addObject("tmontot2",tmontot2);
		mav.addObject("lmonchg",lmonchg);
		mav.addObject("tmonchg",tmonchg);
		mav.addObject("cnt",cnt);
		mav.addObject("list",datel);
		mav.addObject("loginUser",user);
		return mav;
	}
	
	@PostMapping("input")
	public ModelAndView input(Cal cal,User user,HttpSession session
			//,BindingResult bindResult
			) {
		ModelAndView mav = new ModelAndView();
//		if(bindResult.hasErrors()) {
//			mav.getModel().putAll(bindResult.getModel());
//			bindResult.reject("error.input.user");
//			return mav;
//		}
		try {
			User loginUser = (User)session.getAttribute("loginUser");
			user = service.selectUser(user.getId());
			int seq = service.maxseq();
			cal.setSeq(++seq);
			service.calwrite(cal);
			service.chgsum(cal,user);
			user = service.selectUser(user.getId());
			if(user.getId().equals(loginUser.getId())) {
				session.setAttribute("loginUser", user);
				mav.addObject("url","cal.shop");
				mav.addObject("msg","가계부 작성 완료");
				mav.setViewName("alert");
			}
		}catch(Exception e) {
//			mav.getModel().putAll(bindResult.getModel());
			e.printStackTrace();
		}
		return mav;
	}
	
	
	/*
	//caldelete페이지 삭제
	@GetMapping("caldelete")
	public ModelAndView caldelete(String id,int seq) {
		ModelAndView mav = new ModelAndView();
		User user = service.selectUser(id);
		Cal cal = service.selectcal(seq);
		mav.addObject("cal",cal);
		mav.addObject("loginUser",user);
		return mav;
	}
	*/
	
	@RequestMapping("caldelete")
	public ModelAndView caldelete(int seq,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("loginUser");
		Cal cal = service.selectcal(seq);
	//	int price = service.selectprice(cal.getSeq());
		long price = cal.getPrice();
		cal.setPrice(price);
		service.rechgsum(cal,user);
		service.caldelete(seq);
		user = service.selectUser(user.getId());
		mav.addObject("url","cal.shop");
		mav.addObject("msg","삭제되었습니다.");
		mav.setViewName("alert");
		return mav;
	}
	
	@GetMapping("calupdate")
	public ModelAndView calupdate(int seq,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		User user = (User) session.getAttribute("loginUser");
		Cal cal = service.selectcal(seq);
		mav.addObject("cal",cal);
		mav.addObject("loginUser",user);
		return mav;
	}
	
	@PostMapping("calupdate")
	public ModelAndView calupdate(Cal cal,HttpSession session) {
		ModelAndView mav = new ModelAndView();
		try {
			User user = (User) session.getAttribute("loginUser");
			Cal dbcal = service.selectcal(cal.getSeq());
			service.rechgsum(dbcal,user); //수정전 가격 변경
			service.calupdate(cal);
			cal.setPrice(cal.getPrice());
			service.chgsum(cal,user);	//수정후 가격 변경
			user = service.selectUser(user.getId());
			mav.addObject("cal",cal);
			session.setAttribute("loginUser", user);
			mav.addObject("url","cal.shop");
			mav.addObject("msg","수정 완료 되었습니다.");
			mav.setViewName("alert");
		}catch(Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	
}
