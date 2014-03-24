import com.google.common.base.Splitter;
import com.umpay.api.common.Const;
import com.umpay.api.common.ReqData;
import com.umpay.api.paygate.v40.Mer2Plat_v40;
import com.umpay.api.paygate.v40.Plat2Mer_v40;
import com.umpay.api.util.ProFileUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoho on 14-3-5.
 */
public class MyTest {
    public static void main2(String[] args) throws UnsupportedEncodingException {
        String ss = "/tiger/rs/callback/umpay/payResultNotify/v1";

        ss = ss.substring(ss.indexOf("callback/") + "callback/".length());
        System.out.println(ss.substring(0,ss.indexOf("/")));
        ss = ss.substring(ss.indexOf("/")+1, ss.lastIndexOf("/"));
        System.out.println(ss);
    }

    /*加密报文测试*/
    public static void main3(String[] args) throws  Exception{
       /* Map<String, String> params = new HashMap<String, String>();
        params.put(Const.SERVICE, "pay_result_notify");
        params.put(Const.SIGN_TYPE, "RSA");//签名方式
        params.put(Const.CHARSET, "UTF-8");//参数字符编码集
        params.put(Const.VERSION, "4.0");//版本号
        params.put(Const.MER_ID, "6457");//商户编号

        params.put("amount", "1000");
        params.put("amt_type", "RMB");
        params.put("mer_date", "20110101");
        params.put("order_id", "123456789");
        params.put("pay_date", "20110101");
        params.put("pay_seq", "102222222");
        params.put("pay_type", "CREDITCARD");
        params.put("settle_date", "20110101");
        params.put("trade_no", "1006200023381650");
        params.put("trade_state", "TRADE_SUCCESS");
        params.put("", "");

        ReqData reqData = Mer2Plat_v40.ReqDataByGet(params);
        System.out.println("***************************************88");
        System.out.print(reqData.getUrl());*/

        String v = "amount=1&amt_type=RMB&charset=UTF-8&error_code=0000&mer_date=20140310&mer_id=6457&order_id=20140310000017&pay_date=20140310&pay_seq=4403940177456310&pay_type=DEBITCARD&service=pay_result_notify&settle_date=20140310&trade_no=1403101809556831&trade_state=TRADE_SUCCESS&version=4.0&sign=RYsqJFGoF5Zc2E38Yo0SnYo9tQcTqCl1pDD9MvW4iH%2FxGagEYbB8u6y2bKsR1b4YPm%2F8RBB5UAMtre91Alh3ErE3YK10Zw1um%2B9nxRkG1%2Bods7Vm0ZWH2e%2FgGprtoGj%2B%2Bok7A58ZeG75%2FRypgQBUon6fl7l0ZOhEcnlqBTnFGHE%3D&sign_type=RSA";


        Map<String,String> map = Splitter.on("&").withKeyValueSeparator("=").split(v);
        Map<String, String> map2 = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            map2.put(entry.getKey(), URLDecoder.decode(entry.getValue(), "UTF-8"));
        }
        map2 = Plat2Mer_v40.getPlatNotifyData(map2);


       /* Map<String ,String > p2 = Splitter.on("&").withKeyValueSeparator("=").split(v);
        Map<String, String> p3 = new HashMap<String, String>();
        p3.putAll(p2);
        String s1 = URLDecoder.decode("F2k%2BQc46i1meEoKVC0Ang8R3pjBIIzrccAxoQofzvs7pvBtCpPZjOQ57JZysJfNZHeYYnJggCq0cEvuSTp1L7xvdYWmX0JOpdu0s03A82g5PU3ukf%2F5KAIfWU51BThGH4K4FgoKfXTT8wI2jNc9TtlnorZKRvW8WRKoyZwgJrjU%3D","UTF-8");
        p3.put("sign", "CgGgRo/VPk87oQH/1rY9F49qmvRb6LiOYjIjnx/q53AgKYE5RqiXGih43K0snleEnPwVrQQHz0CflgQSXVlVeE6lsxfXMLBSWIvt1ztpprazbvTHTkAzrdMXei8aYBVRKEQl/GA5109pAfVsXZ36IdDhguFxoR6+DSMMiUwlQAQ=");
        Map<String ,String > map = Plat2Mer_v40.getPlatNotifyData(p3);*/
        System.out.println(map2.size());

    }


    /*测试umpay扣款通知返回报文打包*/
    public static void main(String[] args) throws IOException {
//        ProFileUtil.setPropsFilePath("C:\\D\\workspace\\MyTest\\test_umpay\\src\\main\\resources\\SignVerProp.properties");
        Map<String, String> map = new HashMap<String, String>();
        map.put("mer_date", "20110101");
        map.put("mer_id", "6457");
        map.put("order_id", "123456789");
        map.put("ret_code", "0000");
        map.put("sign_type", "RSA");
        map.put("version", "4.0");
        map.put("mer_check_date", "20140313");

//        map.put("sign","Tro1n6zehr6gD0O8hfYWHV+cqKJyoxyDftUZRfjSTIygYbfSgnYKCmEjITjnsJZ2+LLrN0mEaJdZECPQEXmst+IBuOg7tlu3Vf/DHbwgFlzj+BzlCqBpGOrOI2cJPYLyxxdpAQ2WxBHtm94KUBmPC+bnRYHEbhKRYXeUIHX8wFs=");

        String msg = Mer2Plat_v40.merNotifyResData(map);
        System.out.println(msg);
     }

}