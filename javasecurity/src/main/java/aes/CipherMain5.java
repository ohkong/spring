package aes;

public class CipherMain5 {
	public static void main(String[] args) {
		String key="abc1234567";
		CipherUtil.encryptFile("p1.txt","c.sec",key);
	}
}
