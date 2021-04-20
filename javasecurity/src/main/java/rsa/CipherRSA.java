package rsa;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;

/*
 * RSA : 공개키 암호 알고리즘. 비대칭키
 * 		  공개키로 암호화 -> 개인키로 복호화 가능 : 기밀성
 * 		  개인키로 암호화 -> 공개키로 복호화 가능 : 부인방지
 */
public class CipherRSA {
	static Cipher cipher;
	static PrivateKey priKey; //개인키
	static PublicKey pubKey; //공개키
	static {
		try {
			cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
			key.initialize(2048);
			KeyPair keyPair = key.genKeyPair();
			priKey = keyPair.getPrivate();	//개인키 정보
			pubKey = keyPair.getPublic();	//공개키 정보
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String encrypt(String plain) { //암호화 기능
		byte[] cipherMsg = new byte[1024];
		try {
			//공개키를 이용하여 암호화
			cipher.init(Cipher.ENCRYPT_MODE,pubKey); //암호화 모드 설정
			cipherMsg = cipher.doFinal(plain.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg); //16진수 의 문자열
	}

	private static String byteToHex(byte[] cipherMsg) {
		if(cipherMsg == null) return null;
		int len = cipherMsg.length;
		String str = "";
		for(byte b : cipherMsg) {
			str += String.format("%02X", b);
		}
		return str;
	}

	public static String decrypt(String cipherMsg) { //복호화
		byte[] plainMsg = new byte[1024];
		try {
			//개인키로 복호화
			cipher.init(Cipher.DECRYPT_MODE,priKey); //암호화 모드 설정
			plainMsg = cipher.doFinal(hexToByte(cipherMsg.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim();
	}

	private static byte[] hexToByte(String str) {
		if(str == null || str.length() < 2) return null;
		byte[] buf = new byte[str.length()/2];
		for(int i=0;i<buf.length;i++) {
			buf[i] = (byte)Integer.parseInt(str.substring(i*2,i*2+2), 16);
		}
		return buf;
	}
	
	//각자의 키를 파일로 생성하기
	public static void getKey() {
		try {
			KeyPairGenerator key = KeyPairGenerator.getInstance("RSA");
			key.initialize(2048);//키의 크기 2048비트
			KeyPair keyPair = key.generateKeyPair();//키의 쌍으로 설정
			PrivateKey priKey = keyPair.getPrivate();
			PublicKey pubKey = keyPair.getPublic();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("privatekey.ser"));
			out.writeObject(priKey); //개인키를 privatekey.ser파일로 저장
			out.flush(); out.close();
			out = new ObjectOutputStream(new FileOutputStream("publickey.ser"));
			out.writeObject(pubKey); //개인키를 publickey.ser파일로 저장
			out.flush(); out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static PrivateKey getPrivateKey() {
		ObjectInputStream ois = null;
		PrivateKey prikey = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("privatekey.ser"));
			prikey = (PrivateKey)ois.readObject();
			ois.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return prikey;
	}
	
	private static PublicKey getPublicKey() {
		ObjectInputStream ois = null;
		PublicKey pubkey = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("publickey.ser"));
			pubkey = (PublicKey)ois.readObject();
			ois.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return pubkey;
	}

	public static String encrypt(String org, int menu1) {
		byte[] cipherMsg = new byte[1024];
		try {
			if(menu1==1)
				cipher.init(Cipher.ENCRYPT_MODE,getPublicKey());
			else
				cipher.init(Cipher.ENCRYPT_MODE,getPrivateKey());
			cipherMsg = cipher.doFinal(org.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return byteToHex(cipherMsg); //16진수 의 문자열
	}


	public static String decrypt(String cipherMsg, int menu1) {
		byte[] plainMsg = new byte[1024];
		try {
			if(menu1==1)
				cipher.init(Cipher.DECRYPT_MODE,getPrivateKey());
			else
				cipher.init(Cipher.DECRYPT_MODE,getPublicKey());
			plainMsg = cipher.doFinal(hexToByte(cipherMsg.trim()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(plainMsg).trim();
	}
}
