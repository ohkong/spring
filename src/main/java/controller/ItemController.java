package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import logic.Item;
import logic.ShopService;

@Controller	//@Component + Controller 기능
@RequestMapping("item")	// item/* 요청시 호출되는 컨트롤러
public class ItemController {
	@Autowired	//자료형 기준 객체 주입
	private ShopService service;
	@RequestMapping("list")// /item/list.shop 요청시
	public ModelAndView list() {
		ModelAndView mav = new ModelAndView();// /WEB-INF/view/item/list.jsp 뷰
		//itemList : item 테이블의 모든 레코드 정보 저장
		List<Item> itemList = service.getItemList();
		mav.addObject("itemList",itemList);
		return mav;
	}
	@RequestMapping({"detail","edit","confirm"})	//detail.shop, edit.shop 요청처리
	//@RequestMapping("*")	//지정해주지 않은 요청 처리
	public ModelAndView detail(Integer id) {
		ModelAndView mav = new ModelAndView();
		Item item = service.getItem(id);
		mav.addObject("item",item);
		return mav;
	}
	@RequestMapping("create")	// item 
	public String addform(Model model) {
		model.addAttribute(new Item());
		return "item/add";
	}
	@RequestMapping("register")
	public ModelAndView add(@Valid Item item, BindingResult bresult,
			HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("item/add");
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		//item 테이블에 insert, picture 파일을 파일로 생성
		service.itemCreate(item,request);
		mav.setViewName("redirect:/item/list.shop");
		return mav;
	}
	@PostMapping("update")
	public ModelAndView update(@Valid Item item,
			BindingResult bresult,HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("item/edit");
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		//상품 수정
		// item 테이블에 내용 수정
		// upload 이미지변경시 업로드 실행
		service.itemUpdate(item,request);
		mav.setViewName("redirect:detail.shop?id="+item.getId());
		return mav;
	}
	@RequestMapping("delete")
/*
	public ModelAndView delete(Integer id) {
		ModelAndView mav = new ModelAndView();
		service.delete(id);
		mav.setViewName("redirect:list.shop");
		return mav;
	}
*/
	public String delete(Integer id) {
		service.delete(id);
		return "redirect:list.shop";
	}
}
