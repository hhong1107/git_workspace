package cn.fund123.JMeterTest.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log.Logger;

import cn.fund123.shumi.security.Secure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class HttpSend {
	public static final String CHARSET = "UTF-8";

	public static String doPost(Conf conf, HeadMsg headMsg, String params,
			Logger log) {
		System.out.println("<<<CharSet:" + Charset.defaultCharset().toString());
		String url = conf.getBaseUri() + conf.getApiUri();
		Secure secure = Secure.getInstance();
		String data = secure.enByPublicKey(conf.getCertificatePath(), params);
		String sign = secure.signByMD5(conf.getApiUri() + params
				+ conf.getSecretKey());
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put("merchantId", headMsg.getMerchantId());
		postParams.put("sysDateTime", headMsg.getSysDateTime());
		postParams.put("version", headMsg.getVersion());
		postParams.put("sign", sign);
		postParams.put("data", data);
		HttpURLConnection con = null;
		OutputStream osw = null;
		InputStream ins = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setDoInput(true);
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			if (null != postParams) {
				con.setDoOutput(true);
				String postParam = encodeParameters(postParams);
				byte[] bytes = postParam.getBytes(CHARSET);
				con.setRequestProperty("Content-Length",
						Integer.toString(bytes.length));
				osw = con.getOutputStream();
				osw.write(bytes);
				osw.flush();
			}

			int resCode = con.getResponseCode();
			if (resCode == 200) {
				ins = con.getInputStream();
				String responseStr = IOUtils.toString(ins, CHARSET);
				log.info("<<<" + resCode + "返回数据：" + responseStr);
				Gson gson = new Gson();
				Response response = gson.fromJson(responseStr, Response.class);
				if ("0000".equals(response.getCode())) {
					log.info("<<<解密数据  ↓↓↓↓");
					String decmsg = new String(secure.deByprivateKey(
							conf.getKeyStorePath(), conf.getKeyStorePass(),
							response.getData()));
					try {
						Map<String, String> dataMap = gson.fromJson(decmsg,
								new TypeToken<Map<String, String>>() {
								}.getType());
						for (String key : dataMap.keySet()) {
							log.info(key + "=" + dataMap.get(key));
						}
					} catch (Exception e) {
						log.info("<<<无法二次解析数据：" + decmsg);
					}
					log.info("<<<验证签名  ↓↓↓↓");
					boolean signSessuce = secure
							.verifyByMD5((conf.getApiUri() + decmsg + conf
									.getSecretKey()), response.getSign());
					log.info("<<<验签结果" + signSessuce);
				}
			} else {
				ins = con.getErrorStream();
				String ss = IOUtils.toString(ins, CHARSET);
				log.info("<<<" + resCode + "返回数据 :" + ss);
			}
			return readContent(ins);
		} catch (IOException e) {

		} finally {

			try {
				if (osw != null) {
					osw.close();
				}
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	public static String doGet(Conf conf, HeadMsg headMsg, String params,
			Logger log) {
		String url = null;
		url = conf.getBaseUri() + conf.getApiUri() + "?" + params;
		HttpURLConnection con = null;
		OutputStream osw = null;
		InputStream ins = null;
		try {
			con = (HttpURLConnection) new URL(url).openConnection();
			con.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=UTF-8");
			con.setRequestMethod("GET");
			int resCode = con.getResponseCode();
			if (resCode < 400) {
				ins = con.getInputStream();
			} else {
				ins = con.getErrorStream();
			}
			return readContent(ins);
		} catch (IOException e) {

		} finally {

			try {
				if (osw != null) {
					osw.close();
				}
				if (ins != null) {
					ins.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return null;
	}

	private static final String readContent(InputStream ins) throws IOException {

		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(ins,
				CHARSET));
		if (ins != null) {
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		}
		return sb.toString();
	}

	public static String encodeParameters(Map<String, String> postParams) {
		StringBuilder buf = new StringBuilder();
		if (postParams != null && postParams.size() > 0) {

			for (Map.Entry<String, String> tmp : postParams.entrySet()) {
				try {
					buf.append(URLEncoder.encode(tmp.getKey(), CHARSET))
							.append("=")
							.append(URLEncoder.encode(tmp.getValue(), CHARSET))
							.append("&");
				} catch (java.io.UnsupportedEncodingException neverHappen) {
				}
			}
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		Gson gson = new GsonBuilder().create();

		Map<String, String> maps = new HashMap<String, String>();
		maps.put("name1", "value1");
		maps.put("name2", "value2");
		maps.put("name3", "value3");
		String mapStr = gson.toJson(maps);
		System.out.println(mapStr);

		Map<String, String> maps2 = gson.fromJson(mapStr,
				new TypeToken<Map<String, String>>() {
				}.getType());
		for (String s : maps.keySet()) {
			System.out.print(s);
			System.out.println("---" + maps.get(s));
		}

	}
}
