package hash;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/*
 * db useraccount => usersecurity 테이블로 모든 내용 저장
 * create table usersecurity as select * from useraccount
 * CREATE TABLE usersecurity AS SELECT * FROM useraccount
 * SELECT * FROM usersecurity
 * ALTER TABLE usersecurity MODIFY PASSWORD VARCHAR(250) NOT NULL
 * DESC usersecurity => password 컬럼이 varchar(250) 수정.
 * 
 * 2. 프로그램 작성
 * 	useraccount 테이블을 읽어서 usersecurity 테이블의 password 컬럼을 SHA256 알고리즘을 이용하여 해쉬값으로 수정하기
 */
public class DigestMain2 {

	public static void main(String[] args) throws ClassNotFoundException,
						SQLException, NoSuchAlgorithmException{
		Class.forName("org.mariadb.jdbc.Driver"); 
		Connection conn = DriverManager.getConnection
		("jdbc:mariadb://localhost:3307/classdb","scott","1234"); 
		PreparedStatement pstmt = conn.prepareStatement("select * from useraccount");
		ResultSet rs = pstmt.executeQuery();
		
		while(rs.next()){ //읽어온 레코드 한개씩
			String userid = rs.getString("userid");
			String pass = rs.getString("password");
			byte[] plain = null;
			byte[] hash = null;
			String sql = "";
			plain = pass.getBytes();
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			hash = md.digest(plain);
			String hashPass = "";
			for(byte b : hash) {
				System.out.printf("%02X",b);
				hashPass += String.format("%02X", b);
			}
			pstmt.close();
			sql="update usersecurity set password='" + hashPass + "' where userid ='"+userid+"'";
			pstmt = conn.prepareStatement(sql);
			pstmt.executeUpdate();
			System.out.print(" " +rs.getString(1));
			System.out.println(" " +rs.getString(2));
		}
	}
}
