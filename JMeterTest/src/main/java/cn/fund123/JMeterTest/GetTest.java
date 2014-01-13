package cn.fund123.JMeterTest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.log.Logger;
import org.omg.PortableInterceptor.SUCCESSFUL;

import cn.fund123.JMeterTest.tool.Conf;
import cn.fund123.JMeterTest.tool.HeadMsg;
import cn.fund123.JMeterTest.tool.HttpSend;
import cn.fund123.JMeterTest.tool.NameValuePair;
import cn.fund123.shumi.security.Secure;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName CancelTest
 * @Description 撤单
 * @author lijh@fund123.cn
 * @date 2013年12月8日
 */
public class GetTest extends AbstractJavaSamplerClient {
	private final String CHARSET = "UTF-8";
	private Logger log = getLogger();
	private SampleResult results = new SampleResult();
	private HeadMsg headMsg = new HeadMsg();
	private Conf conf = new Conf();
	private Gson gson = new Gson();
	private StringBuffer sb = new StringBuffer();
	private StringBuffer signStr = new StringBuffer();
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setupTest(JavaSamplerContext jsc) {
		log.info("<<<初始化测试数据...");
		conf = gson.fromJson(jsc.getParameter("conf"), Conf.class);
		headMsg = gson.fromJson(jsc.getParameter("handMsg"), HeadMsg.class);
		conf.setApiUri(jsc.getParameter("apiUri"));
		Map<String, String> paramMap = gson.fromJson(
				jsc.getParameter("paramsMap"),
				new TypeToken<Map<String, String>>() {
				}.getType());
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (String key : paramMap.keySet()) {
			try {
				sb.append(key).append("=").append(URLEncoder.encode(paramMap.get(key).trim(),CHARSET)).append("&");
				list.add(new NameValuePair(key, paramMap.get(key)));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		list.add(new NameValuePair("merchantId", headMsg.getMerchantId()));
		list.add(new NameValuePair("sysDateTime", headMsg.getSysDateTime()));
		list.add(new NameValuePair("version", headMsg.getVersion()));
		
		Collections.sort(list, new  Comparator() {
            public int compare(Object o1, Object o2) {
            	NameValuePair n1 = (NameValuePair)o1;
            	NameValuePair n2 = (NameValuePair)o2;
            	return n1.getName().compareTo(n2.getName());
            }});
		signStr.append(conf.getApiUri());
		for (NameValuePair nv : list) {
			signStr.append(nv.getName()).append(nv.getValue());
		}
		signStr.append(conf.getSecretKey());
		sb.append("sign=").append(Secure.getInstance().signByMD5(signStr.toString()));
		sb.append("&merchantId=").append(headMsg.getMerchantId()).append("&sysDateTime=").append(headMsg.getSysDateTime())
			.append("&version=").append(headMsg.getVersion());
	}

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		conf.setApiUri("");
		String confStr = gson.toJson(conf);
		String handMsg = gson.toJson(headMsg);
		params.addArgument("conf", confStr);
		params.addArgument("handMsg", handMsg);
		params.addArgument("apiUri", "");
		params.addArgument("paramsMap", "{这是一个Map按照Map的Json格式填入参数}");
		return params;
	}

	public SampleResult runTest(JavaSamplerContext arg0) {
		log.info("<<<发送地址：" + conf.getBaseUri() + conf.getApiUri());
		log.info("<<<发送数据：" + sb.toString());
		try {
			String responeStr = HttpSend.doGet(conf,headMsg,sb.toString(), log);
			log.info("<<<返回数据：" + responeStr);
		} catch (Exception e) {
			log.info("ERROR...", e);
		}
		results.setSuccessful(true);
		return results;
	}

	/**
	 * 结束方法，实际运行时每个线程仅执行一次，在测试方法运行结束后执行，<br>
	 * 类似于LoadRunner中的end方法. {@inheritDoc}
	 * 
	 * @see org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient#teardownTest(org.apache.jmeter.protocol.java.sampler.JavaSamplerContext)
	 * @author ices 2013-3-18 上午8:45:53 <br>
	 */
	public void teardownTest(JavaSamplerContext arg0) {
	}
}
