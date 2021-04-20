package util;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

@Component
public class CiperUtil {
	private  byte[] randomkey;
	private final static byte[] iv = new byte[] {
			(byte) 0x8E,0x12,0x39,(byte)0x9C,
					0x07,0x72,0x6F,(byte)0x5A,
			(byte) 0x8E,0x12,0x39,(byte)0x9C,
					0x07,0x72,0x6F,(byte)0x5A};
	static Cipher cipher;	//암호객체
	static {
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); //암호화 알고리즘/블럭암호화모드/패딩방법 
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String makehash(String plain) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] pbyte = plain.getBytes();
		byte[] hash = md.digest(pbyte);
		return byteToHex(hash);
	}
	public  byte[] getRandomkey(String algo) throws NoSuchAlgorithmException{
		//AES용 키를 생성 128~196 비트 키의 크기가 가능
		KeyGenerator keyGen = KeyGenerator.getInstance(algo);
		keyGen.init(128);
		SecretKey key =keyGen.generateKey();//랜덤키 생성
		return key.getEncoded();
	}

	private  String byteToHex(byte[] hash) {
		if(hash==null) return null;
		String str ="";
		for(byte b: hash)str+= String.format("%02X", b);
		return str;
	}
	private  byte[] hexToByte(String str) {
		if(str==null||str.length()<2)	return null;
		int len = str.length()/2;
		byte[] buf = new byte[len];
		for(int i=0;i<len;i++) {
			buf[i]= (byte) Integer.parseInt(str.substring(i*2,i*2+2),16);
		}
		return buf;
	}
	public  String decrypt(String cipher1, String key) {
		byte[] plainMsg = new byte[1024];
		try {
			//randomKey : ㅇ마호화시에 사용되었던 키
			Key keya = new SecretKeySpec(makeKey(key), "AES");	//키 설정
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, keya,paramSpec);
			plainMsg = cipher.doFinal(hexToByte(cipher1.trim())); //복호화 실행
		}catch(Exception e ) {e.printStackTrace();}
	return  new String(plainMsg).trim();
	}
	public  String encrypt(String plain, String key) {
		byte[] cipherMsg = new byte[1024];
		try {
			randomkey = getRandomkey("AES");
			Key keya = new SecretKeySpec(makeKey(key), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv); 
			cipher.init(Cipher.ENCRYPT_MODE, keya,paramSpec);
			cipherMsg=cipher.doFinal(plain.getBytes());//암호화 실행
		}catch(Exception e) { e.printStackTrace();}
		
		return byteToHex(cipherMsg).trim();	//16진수로 표시된 문자열로 변환 
	}
	private  byte[] makeKey(String key) {
		//key : abc1234567 
		int len= key.length();
		char ch='A';
		//16자리에서 모자란부분을 A로 채움
		for(int i=len;i<16;i++) {
			key+=ch++;
			}
			return key.substring(0,16).getBytes();
	}
}
