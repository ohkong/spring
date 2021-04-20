package spring;

public class MariadbArticleDao implements ArticleDao{
	@Override
	public void insert() {
		System.out.println("MariadbArticleDao.insert() 메서드 호출");
	}
	@Override
	public String select() {
		System.out.println("MariadbArticleDao.select() 메서드 호출");
		return "홍길동의 글";
	}
}
 