package cn.fund123.JMeterTest;

import java.util.Iterator;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.log.Logger;

import cn.fund123.JMeterTest.tool.Conf;
import cn.fund123.JMeterTest.tool.HeadMsg;
import cn.fund123.JMeterTest.tool.HttpSend;

import com.google.gson.Gson;

/**
 * @ClassName CancelTest
 * @Description 撤单
 * @author lijh@fund123.cn
 * @date 2013年12月8日
 */
public class CancelTest extends AbstractJavaSamplerClient {
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
		Iterator<String> it = jsc.getParameterNamesIterator();
		while(it.hasNext()){
			String name = it.next();
			String value = jsc.getParameter(name);
			if(name.equals("conf")||name.equals("handMsg")||name.equals("TestElement.name"))continue;
			sb.append(name).append("=").append(value).append("&");
		}
	}

	public Arguments getDefaultParameters() {
		Arguments params = new Arguments();
		conf.setApiUri("tradeorder/cancel/v1");
		String confStr = gson.toJson(conf);
		String handMsg = gson.toJson(headMsg);
		params.addArgument("conf", confStr);
		params.addArgument("handMsg", handMsg);
		
		params.addArgument("orderSerial", "订单号，由商户生成");
		params.addArgument("accountSerial", "账户编号");
		params.addArgument("sourceApplySerial", "原基金公司订单编号");
		params.addArgument("tradePassword", "交易密码，特殊授权的商户可以不传该字段");
		params.addArgument("remark", "备注");
		return params;
	}

	public SampleResult runTest(JavaSamplerContext arg0) {
		log.info("<<<发送地址：" + conf.getBaseUri() + conf.getApiUri());
		log.info("<<<发送数据：" + sb.toString());
		try {
			HttpSend.doPost(conf,headMsg,sb.toString(), log);
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
