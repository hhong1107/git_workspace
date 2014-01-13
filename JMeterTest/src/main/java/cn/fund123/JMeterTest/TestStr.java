package cn.fund123.JMeterTest;

import java.util.Iterator;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.log.Logger;

import cn.fund123.JMeterTest.tool.Conf;
import cn.fund123.JMeterTest.tool.HeadMsg;
import cn.fund123.JMeterTest.tool.HttpSend_2;

import com.google.gson.Gson;

public class TestStr extends AbstractJavaSamplerClient {
	/**
	 * 输出到Jmeter控制台的日志类. 需要引用Jmeter lib目录下的logkit-2.0.jar.
	 */
	private Logger log = getLogger();
	/**
	 * 运行结果.
	 */
	private SampleResult results;
	private HeadMsg headMsg = new HeadMsg();
	private Conf conf = new Conf();
	private Gson gson = new Gson();
	private StringBuffer sb = new StringBuffer();

	/**
	 * Jmeter控制台输入的参数.
	 */

	/**
	 * 初始化方法，实际运行时每个线程仅执行一次，在测试方法运行前执行，<br>
	 * 类似于LoadRunner中的init方法. {@inheritDoc}
	 * 
	 * @see org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient#setupTest(org.apache.jmeter.protocol.java.sampler.JavaSamplerContext)
	 * @author ices 2013-3-18 上午8:44:51 <br>
	 */
	@Override
	public void setupTest(JavaSamplerContext jsc) {
		log.info("<<<初始化测试...");
		conf = gson.fromJson(jsc.getParameter("conf"), Conf.class);
		headMsg = gson.fromJson(jsc.getParameter("handMsg"), HeadMsg.class);
		Iterator<String> it = jsc.getParameterNamesIterator();
		while(it.hasNext()){
			String name = it.next();
			String value = jsc.getParameter(name);
			if(name.equals("conf")||name.equals("handMsg")||name.equals("TestElement.name"))continue;
			sb.append(name).append("=").append(value).append("&");
		}
//		log.info("213123213");
//		results = new SampleResult();
//		request = new Request();
//		secureConf = new SecureConf();
//		request.setApiuri(jsc.getParameter("apiuri"));
//		request.setBaseuri(jsc.getParameter("baseuri"));
//		request.setData(jsc.getParameter("data"));
//		request.setMerchantId(jsc.getParameter("merchantId"));
//		request.setSecretKey(jsc.getParameter("secretKey"));
//		request.setSysDateTime(jsc.getParameter("sysDateTime"));
//		request.setVersion(jsc.getParameter("version"));
//
//		secureConf.setCertificatePath(jsc.getParameter("certificatePath"));
//		secureConf.setKeyStorePass(jsc.getParameter("keyStorePass"));
//		secureConf.setKeyStorePath(jsc.getParameter("keyStorePath"));

		// results.setSamplerData(DoPost.doPost(testStr));
	}

	/**
	 * 设置传入的参数，可以设置多个，已设置的参数会显示到Jmeter的参数列表中. {@inheritDoc}
	 * 
	 * @see org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient
	 *      #getDefaultParameters()
	 * @author ices 2013-3-18 上午8:45:02 <br>
	 */
	public Arguments getDefaultParameters() {

		Arguments params = new Arguments();
		/*
		 * 定义一个参数，显示到Jmeter的参数列表中， 第一个参数为参数默认的显示名称， 第二个参数为默认值
		 */
		String confStr = gson.toJson(conf);
		String handMsg = gson.toJson(headMsg);

		params.addArgument("conf", confStr);
		params.addArgument("handMsg", handMsg);
		params.addArgument("account", "");
		params.addArgument("paymentcade","112312");
		return params;
	}

	/**
	 * 测试执行的循环体，根据线程数和循环次数的不同可执行多次，类似于LoadRunner中的Action方法 {@inheritDoc}
	 * 
	 * @see org.apache.jmeter.protocol.java.sampler.JavaSamplerClient#runTest(org.apache.jmeter.protocol.java.sampler.JavaSamplerContext)
	 * @author ices 2013-3-18 上午8:45:18 <br>
	 */
	public SampleResult runTest(JavaSamplerContext arg0) {
		log.info("execute runTest...");
		// //定义一个事务，表示这是事务的起始点，类似于LoadRunner的lr.start_transaction
		// results.sampleStart();
		// //定义一个事务，表示这是事务的结束点，类似于LoadRunner的lr.end_transaction
		// results.sampleEnd();
		// if(testStr.length() < 5){
		// log.info("fail...");
		// //用于设置运行结果的成功或失败，如果是"false"则表示结果失败，否则则表示成功
		// results.setSuccessful(false);
		// } else {
		// log.info("Success...");
		// results.setSuccessful(true);
		// }
		// results.sampleStart();
		log.info(sb.toString());
		try {
			HttpSend_2.doPost(conf,headMsg,sb.toString(), log);
		} catch (Exception e) {
			log.info("HTTP ERROR...", e);
		}
		results.setResponseMessage("22122222");
		// results.sampleEnd();
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
