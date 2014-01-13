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
 * @ClassName MOpenAccTest
 * @Description 商户级别开户
 * @author lijh@fund123.cn
 * @date 2013年12月8日
 */
public class MOpenAccTest extends AbstractJavaSamplerClient {
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
		conf.setApiUri("account/open/merchant/v1");
		String confStr = gson.toJson(conf);
		String handMsg = gson.toJson(headMsg);
		params.addArgument("conf", confStr);
		params.addArgument("handMsg", handMsg);
		
		params.addArgument("customerRealName", "客户真实姓名");
		params.addArgument("certificationType", "证件类型（0： 表示身份证，暂不支持其他证件）");
		params.addArgument("certificationNumber", "证件号码");
		params.addArgument("bankSerial", "银行编号");
		params.addArgument("bankCard", "银行卡号");
		params.addArgument("merchantUserId", "商户系统中用户ID");
		params.addArgument("paymentCard", "支付卡号（台账账号）");
		params.addArgument("paymentCode", "支付代码");
		params.addArgument("tradePassword", "交易密码（特殊授权商户可不传）");
		params.addArgument("email", "邮箱");
		params.addArgument("mobile", "手机号码");
		params.addArgument("vocationCode", "职业,01 政府部门;02 教科文;03 金融;04 商贸;05 房地产;06 制造业;07 自由职业;08 其");
		params.addArgument("contactAddress", "联系地址");
		params.addArgument("marriage", "婚姻状况（0：未婚；1：已婚；默认1）");
		params.addArgument("educational", "教育水平.01 初中及以下;02 高中/中专;03 大专/本科;04 硕士及以上");
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
