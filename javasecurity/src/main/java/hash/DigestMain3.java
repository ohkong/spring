package hash;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/*
 * 화면에서 아이디와 비밀번호를 입력받아서
 * 해당 아이디가 usersecurity 테이블에 없으면 "아이디 없음" 출력
 * 해당 아이디의 비밀번호를 비교해서 맞으면 반갑습니다 . xxx님
 * 해당 아이디의 비밀번호가 다르면 비밀번호가 틀렸습니다. 다시 입력해주세요
 * 
 * 1. 아이디에 맞는 레코드 조회
 * 		해당 레코드가 없으면 : 아이디 확인
 * 		레코드 있으면
 * 			- 비밀번호 검증
 * 				입력된 비밀번호를 해쉬값 구하기
 * 				입력된 비밀번호의 해쉬값 == db에 등록된 비밀번호 검증
 * 				맞으면 : "반갑습니다. ..."
 * 				틀리면 : "비밀번호 확인"
 */
public class DigestMain3 {
	public static void main(String[] args) throws Exception{
		Class.forName("org.mariadb.jdbc.Driver"); 
		Connection conn = DriverManager.getConnection
		("jdbc:mariadb://localhost:3307/classdb","scott","1234"); 
		PreparedStatement pstmt = conn.prepareStatement
					("select password,username from usersecurity");
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("아이디를 입력해주세요 =>");
		String inId = br.readLine();
		System.out.println("비밀번호를 입력해주세요 =>");
		String inPass = br.readLine();
		String sql = "select password,username from usersecurity where userid='"+inId+"'";
		ResultSet rs = pstmt.executeQuery(sql);
		if(rs.next()) {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] plain = inPass.getBytes();
			byte[] hash = md.digest(plain);
			String hashPass = "";
			for(byte b : hash) {
				hashPass += String.format("%02X", b);
			}
			if(rs.getString(1).equals(hashPass)) {
				System.out.println("반갑습니다. "+rs.getString(2)+"님");
			}else {
				System.out.println("비밀번호가 틀렸습니다. 다시 입력해주세요");
			}
		}else {
			System.out.println("아이디 없음.");
		}
	}
}
