package controller;

import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.ShopService;

//index.shop 요청시 호출되는 객체
public class IndexController {
	private ShopService shopService;
	public void setShopService(ShopService shopService) {
		this.shopService = shopService;
	}
	@RequestMapping	//index.shop 요청시 메서드 호출됨
	public ModelAndView itemList() {
		//ModelAndView : 데이터부분과 View부분을 저장할 수 있는 객체. 
		//				DispatcherServlet객체로 전달함.
		//itemList : db의 item 테이블의 모든 내용을 저장.
		List<Item> itemList = shopService.getItemList();
		//view이름 : index
		//			spring-mvc.xml의 viewResolver의 설정에 의해서
		//			/WEB-INF/view/index.jsp 설정됨.
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("itemList",itemList);	//데이터를 view로 전송
		return mav;  
	}
}
