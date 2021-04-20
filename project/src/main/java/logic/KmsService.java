package logic;

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import dao.BoardDao;

@Service
public class KmsService {
	@Autowired
	private BoardDao boardDao;
	
	private void uploadFileCreate
    (MultipartFile picture, HttpServletRequest request, String path) {
	String orgFile = picture.getOriginalFilename(); //업로드된 파일의 이름
	String uploadPath = request.getServletContext().getRealPath("/") +path;
	File fpath = new File(uploadPath);
	if(!fpath.exists()) fpath.mkdirs(); //업로드 폴더가 없는 경우 폴더 생성
	try {
		//picture : 업로드된 파일의 내용 저장
		// transferTo : 업로드된 파일의 내용을 File로 저장
		picture.transferTo(new File(uploadPath+orgFile));
	} catch(Exception e) {
		e.printStackTrace();
	}				
}
	public int boardcount(String searchtype, String searchcontent) {
		return boardDao.count(searchtype,searchcontent);
	}
	public List<Board> boardlist(Integer pageNum, int limit, 
			             String searchtype, String searchcontent) {
		return boardDao.list(pageNum,limit,searchtype,searchcontent);
	}
	public Board getBoard(Integer num,boolean able) {
	   if(able) boardDao.readcntadd(num);
	   return boardDao.selectOne(num);
	}
	public int maxnum() {
		return boardDao.maxNum();
	}
	public void boardwrite(@Valid Board board, HttpServletRequest request) {
		if(board.getFile1() != null && !board.getFile1().isEmpty()) {
			uploadFileCreate(board.getFile1(), request, "board/file/");
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		boardDao.write(board);
		
	}
	
	public void boardReply(Board board) {
		//기존의 답글들의 grpstep항목 +1 수정
		boardDao.updateGrpStep(board);
		//답글 등록
		int max = boardDao.maxNum();
		board.setNum(++max);
		board.setGrplevel(board.getGrplevel() + 1);
		board.setGrpstep(board.getGrpstep() + 1);
		boardDao.write(board);
	}
	
	public void boardUpdate (Board board, HttpServletRequest request) {
		if(board.getFile1()!=null && !board.getFile1().isEmpty()){
			uploadFileCreate(board.getFile1(),request, "board/file/");
			board.setFileurl(board.getFile1().getOriginalFilename());
		}
		boardDao.update(board);
	}
	public void boardDelete(int num) {
		boardDao.delete(num);
	}
	public Map<String, Object> graph1() {
		Map<String,Object> map = new HashMap<String,Object>();
		 /*
		  [
		  {name:홍길동,cnt:7} : m
		  {name:김삿갓,cnt:5}
		  {name:이몽룡,cnt:2}] */
		for(Map<String,Object> m : boardDao.graph1()) {
			map.put((String)m.get("name"), m.get("cnt"));
			//{"홍길동",7}...
		}
		return map;
	}	
	
	public Map<String, Object> graph2() {
		Map<String,Object> map = new TreeMap<String,Object>
											(Comparator.reverseOrder());
		for(Map<String,Object> m : boardDao.graph2()) {
			map.put((String)m.get("regdate"), m.get("cnt"));
		}
		return map;
	}	
}
