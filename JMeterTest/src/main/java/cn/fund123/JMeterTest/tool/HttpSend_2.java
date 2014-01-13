package cn.fund123.JMeterTest.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.log.Logger;

import cn.fund123.shumi.security.Secure;



public class HttpSend_2 {
    public static final String CHARSET = "UTF-8";  
    
    public static String doPost(Conf conf,HeadMsg headMsg,String params, Logger log) { 
    	String url = conf.getBaseUri()+conf.getApiUri();
    	log.info("<<<请求地址:" + url);
    	log.info(conf.getCertificatePath());
    	Secure secure = Secure.getInstance();
    	String data = secure.enByPublicKey(conf.getCertificatePath(), params);
    	String sign = secure.signByMD5(conf.getApiUri() + params + conf.getSecretKey());  
    	Map<String, String> postParams = new HashMap<String, String>();
    	postParams.put("merchantId", headMsg.getMerchantId());
    	postParams.put("sysDateTime", headMsg.getSysDateTime());
    	postParams.put("version", headMsg.getVersion());
    	postParams.put("sign",sign);
    	postParams.put("data",data);
        HttpURLConnection con = null;  
        OutputStream osw = null;  
        InputStream ins = null;  
        try {  
            con = (HttpURLConnection) new URL(url).openConnection();  
            con.setDoInput(true);  
            con.setRequestMethod("POST");  
            con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");  
            if (null != postParams) {  
                con.setDoOutput(true);  
                String postParam = encodeParameters(postParams);  
                log.info("<<<发送数据:" + postParam);
                byte[] bytes = postParam.getBytes(CHARSET);  
                con.setRequestProperty("Content-Length", Integer.toString(bytes.length));  
                osw = con.getOutputStream();  
                osw.write(bytes);  
                osw.flush();  
            }  
  
            int resCode = con.getResponseCode();  
            if (resCode < 400) {  
                ins = con.getInputStream(); 
                String ss = IOUtils.toString(ins);
                log.info("<<<返回数据" + ss);
            } else {  
                ins = con.getErrorStream();  
                String ss = IOUtils.toString(ins);
                log.info("<<<返回数据" + ss);
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
  
    public static String get(String url) {  
        HttpURLConnection con = null;  
        OutputStream osw = null;  
        InputStream ins = null;  
        try {  
            con = (HttpURLConnection) new URL(url).openConnection();  
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


}
