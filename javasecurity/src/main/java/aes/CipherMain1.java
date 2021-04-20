package aes;
/*
 * 키를 설정해서 AES 암호화 하기
 */
public class CipherMain1 {
	public static void main(String[] args) {
		String plain1 = "안녕하세요. 홍길동 입니다.";	//평문
		String cipher1 = CipherUtil.encrypt(plain1);	//암호화
		System.out.println("암호문: "+cipher1);
		String plain2 = CipherUtil.decrypt(cipher1);	//복호화
		System.out.println("복호문: "+plain2);//평문. plain1 문장과 같은 문장. 복호화.
	}

}
