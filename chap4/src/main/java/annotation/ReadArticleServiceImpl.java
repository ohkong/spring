package annotation;

import org.springframework.stereotype.Component;

import xml.Article;
import xml.ReadArticleService;

//생성되는 객체의 이름을 readArticleService로 설정
@Component("readArticleService")
public class ReadArticleServiceImpl implements ReadArticleService{

	@Override
	public Article getArticleAndReadCnt(int id) throws Exception {
		System.out.println("getArticleAndReadCnt("+id+") 호출");
		if(id==0) {
			throw new Exception("id는 0이 안됨");
		}
		return new Article(id);
	}
	
}
