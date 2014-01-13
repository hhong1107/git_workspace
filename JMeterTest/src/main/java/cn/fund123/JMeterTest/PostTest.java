package cn.fund123.JMeterTest;

import java.util.Iterator;
import java.util.Map;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.log.Logger;

import cn.fund123.JMeterTest.tool.Conf;
import cn.fund123.JMeterTest.tool.HeadMsg;
import cn.fund123.JMeterTest.tool.HttpSend;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @ClassName CancelTest
 * @Description 撤单
 * @author lijh@fund123.cn
 * @date 2013年12月8日
 */
public class PostTest extends AbstractJavaSamplerClient {
	private Logger log = getLogger();
	private SampleResult results = new SampleResult();
	private HeadMsg headMsg = new HeadMsg();
	private Conf conf = new Conf();
	private Gson gson = new Gson();
	private StringBuffer sb = new StringBuffer();

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

		for (String key : paramMap.keySet()) {
			sb.append(key).append("=").append(paramMap.get(key).trim()).append("&");
		}
	}

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		String confStr = gson.toJson(conf);
		String handMsg = gson.toJson(headMsg);
		params.addArgument("conf", confStr);
		params.addArgument("handMsg", handMsg);
		params.addArgument("apiUri","");
		params.addArgument("paramsMap", "{这是一个Map按照Map的Json格式填入参数}");
		return params;
	}

	public SampleResult runTest(JavaSamplerContext arg0) {
		log.info("<<<发送地址：" + conf.getBaseUri() + conf.getApiUri());
		log.info("<<<发送数据：" + sb.toString());
		try {
			HttpSend.doPost(conf, headMsg, sb.toString(), log);
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
