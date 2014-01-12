package com.hundsun.fund.shumi_security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.fund123.shumi.security.Secure;

public class SecureTest {
	//D:\\SVN\\SFTP2\\SFTPv2\\tiger\\branches\\v1.0.0\\src\\main\\resources\\keystore.pfx
//	public String keyStorePath = "D:\\SVN\\SFTP2\\SFTPv2\\tiger\\branches\\v1.0.0\\src\\main\\resources\\keystore.pfx";
//	public String password = "123456789";
//	public String keyStorePath = "D:\\ssl\\certs\\sina.pfx";
//	public String password = "fund123";
	public String certificatePath = "D:/ssl/certs/22/shumi_sina.cer";

	public String keyStorePath = "D:\\SVN\\SFTP2\\SFTPv2\\tiger\\branches\\v1.0.0\\src\\main\\resources\\keystore.pfx";
	public String password = "123456789";
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	
	@Test
	/*公钥加密  私钥解密 */
	public void testGetInstance() {
		
		
		
		String data = "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来"
				+ "如题，暂且抛开手机发热，耗电快等问题，就想了解MX3，还有S4的5410处理器真的能通过升级来实现八核全开吗？真能全开的话，就算真有发热严重耗电飞快，也要试一下八核全开是怎么个爽法！大不了到时真的吃不消了再刷回来";
		
		data = "bankCard=8888888888888888888888888&bankSerial=0101&certificationNumber=430921199001170856&certificationType=0&contactAddress=峨山路66号&customerRealName=刘舟&educational=null&email=sina-liuzhou@test.com&marriage=null&merchantUserId=sina-liuzhou2&mobile=13816296272&paymentCard=8888888888888888888888888&paymentCode=88888&tradePassword=222222&vocationCode=03";
		
		
//		try {
//			StringBuilder sb = new StringBuilder();
//			char[] bf = new char[1024];
//			FileReader fr = new FileReader("C:/Users/剑虹/Desktop/22.txt");
//			while (fr.read(bf)>0) {
//				sb.append(bf);
//			}
//			data = sb.toString();
//			System.out.println(data);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		data = "LAM=ZybxaZsGZL3BqHwhKhVR0bh33CbnbqhKRuvrQGUc2+MMey7dipM+qhE2v7WBTONgNWjAFZyo9u6aWD+VF+Gfn9BA/IwsAD9d0ivg+RM/yqQ+OA5U9lkjeVIOOeF/9g8/t60wuQ0PrmYTqN6btTvnsm09nWagZI2q/bnre14MCkAPyAQS5hZ/z8fxkWykeEgty9kY9Mn0TMV1ge7R9KNNxZf5Vuh2OxuuoQNsf/VgfE8+IrmoMFgnmX9dRNoTSdeaN2WBJp5xO7FONTFjsHSTMEUyOwqWMGKkYE7DJEBeiTxh6Yj96u9/bG35oGApluF4kc7H4LV+i/QUOpjHXkynHg==olg+V9/JusDVQ8166n55UA==";
		System.out.println(data.length());
		Secure secure = Secure.getInstance();
		String enMsg = secure.enByPublicKey(certificatePath,data);
		System.out.println("密文："+enMsg);
//		enMsg = "eixisqMwhCPfArXoJhkY8t5AcMxDnaYenrxOvmfkAg+jTMe8TN+DAc8ogStC8Dw9IgWwBoLwvszWZF+JZWwoqOoHftHhzTY4h3ynfN05yus3nM/IEAnSPeKni2kyEH5XyfTeLlW5ufWr+Qio0LcDRj6a4rUpo89Cg0GjHs39zLdhuHY/ZhP4lp6CfR1lT89AJcmzNMOTa0AtF/8wg5ioaJXFJkDTtCaUf7zFwlwuOxjMIUsgWZcQ9A94poCAR0lfRHsrtAcp9sQkgxq8vIXL/RQooTCE/eU1/p1nGnd7vhXbcJjXIhLNdAVdU2WrAl/9d+zPYMz3cESWVSD0PRVXzg==wZi2bIhLiYT/pN1yV10369MH+yZyEUlpSJFDkqPkg2uBgGPKGq8Zt0ub+PyXENNxa8OfdBNErzMZ0CwnUpzD1BVhmZ4KpnzepD1EcwsyPYc43ykXegQTKyYsxuPcKcvZyoUfTYja315U7pEMUMYr+3TUb9JevQczYw6DfE4MKILR1BEwVMS7jvSVp2KN4ckFvMVlHX5iEg4S7rtUIhUysMOAX0XRaWAKE+MN+aRRBng0aF/JuSTs0D/PiDM933DOctgXlJKJBpwH6Yim7R4JR0ZRbf8voJ8qR69710K9F3BTbGO7An5xSFDj1/3QzelVjTZAdwsKYrSu2VHXp7TeqIgJTcbYbTI4Q90XJtxISv+Y6rxDj3rsE8Fdc6vbn5d2vezuYKc4jLoirL2LAF0KXw==";
		String deMsg = secure.deByprivateKey(keyStorePath,password,enMsg);
		System.out.println("明文："+deMsg);
//		assertEquals(deMsg, data);
		
	}
	
//	@Test
	/**
	 * 证书签名(暂时不用)
	 */
	public void testSign()throws Exception{
		String data = "account/open/merchant/v1customerRealName=鐜嬮夯瀛�certificationType=0&certificationNumber=142732198505244831&bankSerial=003&bankAccount=3443214567432145&merchantUserNo=2013112201&tradePassword=8888&email=345534243@qq.com&mobile=13456789921&riskLevel=0&vocationCode=4&contactAddress=娴欐睙鍢夊叴&marriage=1&educational=3pppppppppppppppppppppppppppp";

		Secure secure = Secure.getInstance();
		String signMsg = secure.sign(keyStorePath,password, certificatePath,data);
		System.out.println(signMsg);
		boolean s = secure.verify(certificatePath,data, signMsg);
		System.out.println(s);
		assertTrue(s);
	}
	
//	@Test
	/**
	 * MD5 生成签名(有顺序混淆机制)
	 */
	public void createKey(){
		String data = "sina305";
		Secure secure = Secure.getInstance();
		String sign  = secure.signByMD5(data);
		System.out.println(sign);
		assertTrue(secure.verifyByMD5(data, sign));
	}
	
	/**
	 * 验签 
	 */
	@Test
	public void verifyByMD5(){
		Secure secure = Secure.getInstance();
		String data = "account/open/merchant/v1bankCard=8888888888888888888888888&bankSerial=0101&certificationNumber=430921199001170856&certificationType=0&contactAddress=峨山路66号&customerRealName=刘舟&educational=null&email=sina-liuzhou@test.com&marriage=null&merchantUserId=sina-liuzhou2&mobile=13816296272&paymentCard=8888888888888888888888888&paymentCode=88888&tradePassword=222222&vocationCode=0316ACE2B797EE376D1F4EC72A4EFB0C28";//原数据
		String signData = "3D19F984AA734B1A3C8C1F379C86749F"; //签名数据
		System.out.println(secure.signByMD5(data));
		boolean signSuccess = secure.verifyByMD5(data, signData);
		System.out.println(signSuccess);
	}
//		public static void main(String[] args) {
//			byte[] tt = {99, 117, 115, 116, 111, 109, 101, 114, 82, 101, 97, 108, 78, 97, 109, 101, 61, -26, -99, -114, -27, -101, -101, 38, 99, 101, 114, 116, 105, 102, 105, 99, 97, 116, 105, 111, 110, 84, 121, 112, 101, 61, 48, 38, 99, 101, 114, 116, 105, 102, 105, 99, 97, 116, 105, 111, 110, 78, 117, 109, 98, 101, 114, 61, 49, 52, 50, 55, 51, 50, 49, 57, 56, 53, 48, 53, 50, 52, 52, 56, 49, 51, 38, 98, 97, 110, 107, 83, 101, 114, 105, 97, 108, 61, 48, 48, 51, 38, 98, 97, 110, 107, 65, 99, 99, 111, 117, 110, 116, 61, 51, 52, 52, 51, 50, 49, 52, 53, 54, 55, 52, 51, 50, 49, 52, 53, 38, 109, 101, 114, 99, 104, 97, 110, 116, 85, 115, 101, 114, 78, 111, 61, 50, 48, 49, 51, 49, 49, 50, 50, 48, 49, 38, 116, 114, 97, 100, 101, 80, 97, 115, 115, 119, 111, 114, 100, 61, 56, 56, 56, 56, 38, 101, 109, 97, 105, 108, 61, 51, 52, 53, 53, 51, 52, 50, 52, 51, 64, 113, 113, 46, 99, 111, 109, 38, 109, 111, 98, 105, 108, 101, 61, 49, 51, 52, 53, 54, 55, 56, 57, 57, 50, 49, 38, 114, 105, 115, 107, 76, 101, 118, 101, 108, 61, 48, 38, 118, 111, 99, 97, 116, 105, 111, 110, 67, 111, 100, 101, 61, 52, 38, 99, 111, 110, 116, 97, 99, 116, 65, 100, 100, 114, 101, 115, 115, 61, -26, -75, -103, -26, -79, -97, -27, -104, -119, -27, -123, -76, 38, 109, 97, 114, 114, 105, 97, 103, 101, 61, 49, 38, 101, 100, 117, 99, 97, 116, 105, 111, 110, 97, 108, 61, 51};
//			System.out.println(new String(tt,Charset.forName("UTF-8")));
//			System.out.println(new String(tt,Charset.forName("GBK")));
//			System.out.println(new String(tt,Charset.forName("ISO8859-1")));
//			
//		}
	public static void main(String[] args) throws UnsupportedEncodingException {
//		byte[] sb = {03,44};
//		String s3 = new String(sb);
//		System.out.println(s3);
//		sb = s3.getBytes();
		
//		System.out.println(new String(sb));
		byte[] bb = {50, 50, 48, 55, 55, 91, 93, -23, -108, -97, -26, -106, -92, -26, -117, -73, -26, -108, -81, -23, -108, -97, -26, -106, -92, -26, -117, -73, -23, -108, -97, -26, -106, -92, -26, -117, -73, -23, -108, -97, -25, -89, -72, -24, -66, -66, -26, -117, -73, -23, -124, -81, -23, -108, -97, -25, -69, -98, -17, -65, -67, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32, 32};
		String tt = "";
		Charset c = Charset.forName("UTF-8");
		ByteBuffer b = c.encode(tt);
		
		
		String ss = new String(bb,"UTF-8");
		System.out.println(ss);
		String s2 = new String(ss.getBytes("UTF-8"),"UTF-8");
		System.out.println(s2);
		byte[] b2 = s2.getBytes("UTF-8");
		System.out.println(new String(bb,"ISO-8859-1"));
	}
}
