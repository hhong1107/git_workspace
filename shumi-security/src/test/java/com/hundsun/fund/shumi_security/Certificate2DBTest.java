package com.hundsun.fund.shumi_security;

import java.io.FileReader;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import cn.fund123.shumi.security.Secure;

public class Certificate2DBTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {
		String data = "kdwadÎÒ´òµÄ";
		try {
			StringBuilder sb = new StringBuilder();
			char[] buf = new char[1024];
			FileReader fr = new FileReader("D:/ssl/certs/sina_shumi2.cer");
			while (fr.read(buf)>0) {
				sb.append(buf);
			}
			String ss = sb.toString();
			System.out.println(ss);
			System.out.println("--------------------------------");
			Secure secure = Secure.getInstance();
			String enMsg = secure.enByDBPublicKey("30500012", ss, data);
			System.out.println(enMsg);
			
			System.out.println(secure.deByprivateKey("D:/ssl/certs/sina.pfx", "fund123", enMsg));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
