package controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import exception.BoardException;
import logic.Board;
import logic.ShopService;

@Controller
@RequestMapping("board")
public class BoardController {
	@Autowired
	ShopService service;
	
	/*
	 * pageNum : 보여줄 페이지 번호. 없으면 1페이지로 설정
	 * searchtype : 검색시 컬럼명
	 * searchcontent : 검색 내dyd
	 */
	@RequestMapping("list")
	public ModelAndView list(Integer pageNum, String searchtype,
								String searchcontent) {
		ModelAndView mav = new ModelAndView();
		if(pageNum == null || pageNum.toString().equals("")) {
			pageNum = 1;
		}
		if(searchtype == null || searchcontent == null ||
			searchtype.trim().equals("") || searchcontent.trim().equals("")) {
			searchtype = null;
			searchcontent = null;
		}
		int limit = 10;
		int listcount = service.boardcount(searchtype,searchcontent);
		List<Board> boardlist = service.boardlist(pageNum,limit,searchtype,searchcontent);
		int maxpage = (int)((double)listcount/limit + 0.95);
		int startpage = (int)((pageNum/10.0 + 0.9) - 1) * 10 + 1;
		int endpage = startpage + 9;
		if(endpage>maxpage) endpage = maxpage;
		int boardno = listcount - (pageNum - 1) * limit;
		mav.addObject("pageNum",pageNum);
		mav.addObject("maxpage",maxpage);
		mav.addObject("startpage",startpage);
		mav.addObject("endpage",endpage);
		mav.addObject("listcount",listcount);
		mav.addObject("boardlist",boardlist);
		mav.addObject("boardno",boardno);
		mav.addObject("today",
				new SimpleDateFormat("yyyyMMdd").format(new Date()));
		return mav;
	}
	
	@RequestMapping("detail")
	public ModelAndView detail(Board board) {
		ModelAndView mav = new ModelAndView();
		board = service.getBoard(board.getNum(),true);
		mav.addObject("board",board);
		return mav;
	}
	
	@GetMapping("*")
	public ModelAndView getBoard(Integer num) {
		ModelAndView mav = new ModelAndView();
		Board board = null;
		if(num == null) {
			board = new Board();
		}else {
			board = service.getBoard(num,false);
		}
		mav.addObject("board",board);
		return mav;
	}
	
	@PostMapping("write")
	public ModelAndView write(@Valid Board board,BindingResult bresult,
												HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		//db에 board 객체 저장
		//최대 num값 + 1 을 등록되는 게시뮬의 num으로 설정
		//등록 성공 : list.shop
		//등록 실패 : BoardException 예외 발생. write.shop 등록화면
		try {
			int num = service.maxnum(board.getNum());
			board.setNum(++num);
			board.setGrp(num);
			service.boardwrite(board,request);
			mav.addObject("board",board);
		}catch(Exception e) {
			e.printStackTrace();
			throw new BoardException("다시 등록해 주세요", "write.shop");
		}
		mav.setViewName("redirect:list.shop");
		return mav;
	}
	
	@PostMapping("reply")
	//내가 한것
//	public ModelAndView reply(@Valid Board board,BindingResult bresult,
//												HttpServletRequest request) {
//		ModelAndView mav = new ModelAndView();
//		if(bresult.hasErrors()) {
//			mav.getModel().putAll(bresult.getModel());
//			return mav;
//		}
//		try {
//			service.grpstepadd(board.getGrp(),board.getGrpstep());
//			int grplevel = board.getGrplevel();
//			int grpstep = board.getGrpstep();
//			int num = service.maxnum(board.getNum());
//			board.setNum(++num);
//			board.setGrplevel(grplevel+1);
//			board.setGrpstep(grpstep+1);
//			service.boardwrite(board,request);
//			mav.addObject("board",board);
//		}catch(Exception e) {
//			e.printStackTrace();
//			throw new BoardException("다시 등록해 주세요", "write.shop");
//		}
//		mav.setViewName("redirect:list.shop");
//		return mav;
	
		//강사님
	public ModelAndView reply(@Valid Board board,BindingResult bresult) {
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			Board dbBoard = service.getBoard(board.getNum(), false);
			Map<String, Object> map = bresult.getModel();
			Board b = (Board)map.get("board");
			b.setSubject(dbBoard.getSubject());
			return mav;
		}
		try {
			service.boardReply(board);
			mav.setViewName("redirect:list.shop");
		}catch(Exception e) {
			e.printStackTrace();
			throw new BoardException
					("답변 등록에 실패했습니다.", "reply.shop?num="+board.getNum());
		}
		return mav;
	}
	
	/*
	 * 1. 파라미터 값 board 객체 저장. 유효성 검증.
	 * 2. 입력된 비밀번호와 db의 비밀번호를 비교 
	 * 	   비밀번호가 맞는경우 수정정보를 db에 변경
	 *    첨부파일 변경 : 첨부파일 업로드, fileurl 정보 수정
	 *    detail.shop 페이지 호출
	 * 3. 틀린경우 '비밀번호가 틀립니다.',update.shop Get방식으로 호출
	 */
	@PostMapping("update")
	public ModelAndView update(@Valid Board board,BindingResult bresult,
										HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		if(bresult.hasErrors()) {
			mav.getModel().putAll(bresult.getModel());
			return mav;
		}
		Board dbBoard = service.getBoard(board.getNum(), false);
		if(!board.getPass().equals(dbBoard.getPass())) {
			throw new BoardException("비밀번호가 틀립니다", "update.shop?num="+board.getNum());
		} 
		try {
		service.boardupdate(board,request);
		mav.setViewName("redirect:detail.shop?num="+board.getNum());
		}catch(Exception e) {
			e.printStackTrace();
			throw new BoardException("게시물 수정에 실패하였습니다", "update.shop?num="+board.getNum());
		}
		return mav;
	}
	
	/*
	 * 1. num,pass 파라미터 저장.
	 * 2. db의 비밀번호
	 */
	@PostMapping("delete")
	public ModelAndView delete(Board board,BindingResult bresult) {
		ModelAndView mav = new ModelAndView();
		int num = board.getNum();
		String pass = board.getPass();
		try {
			Board dbBoard = service.getBoard(num, false);
			if(!pass.equals(dbBoard.getPass())) {
				bresult.reject("error.login.password");
				return mav;
			}
			service.boardDelete(num);
			mav.setViewName("redirect:list.shop");
		}catch(Exception e) {
			e.printStackTrace();
			throw new BoardException("삭제에 실패하였습니다.", "delete.shop?num="+board.getNum());
		}
		
		return mav;
	}
	
	//upload : ckeditor에서 전달해주는 파일의 이름.
	//			<input type="file" name="upload" ..>
	//CKEditorFuncNum : ckeditor에서 전달한 파라미터 이름 
	@RequestMapping("imgupload")
	public String imgupload(MultipartFile upload,
				String CKEditorFuncNum,HttpServletRequest request,Model model) {
		String path = request.getServletContext().getRealPath("/") + "board/imgfile/";
		File f = new File(path);
		if(!f.exists()) f.mkdirs();
		if(!upload.isEmpty()) {
			File file = new File(path, upload.getOriginalFilename());
			try {
				upload.transferTo(file);	//업로드된 내용을 파일에 저장
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		String fileName = request.getContextPath() + 
					"/board/imgfile/" + upload.getOriginalFilename();
		model.addAttribute("fileName",fileName);
		model.addAttribute("CKEditorFuncNum",CKEditorFuncNum);
		return "ckedit";	//view 이름
	}
}
