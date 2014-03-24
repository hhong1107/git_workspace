import com.google.common.base.Joiner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoho on 14-2-22.
 */
public class DoTest {
    private static final String SEPARATOR = "do?";
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("w","dwqq");
        map.put("sencd", "23123");
        String body = Joiner.on("|").withKeyValueSeparator("=").join(map);
        System.out.println(body);
        for (Map.Entry<String, String> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
        String url = "http://www.companyname.com.cn/tradeResult.do?amount=1000&amt_type=RMB&mer_date=20110101&mer_id=9996&order_id=123456789&pay_date=20110101&pay_seq=102222222&pay_type=CREDITCARD&settle_date=20110101&sign_type=RSA&trade_no=1006200023381650&trade_state=TRADE_SUCCESS&version=4.0&sign=dRQAoWIcbGrUK47/jhchOgv9ZeXpRxWT0s+MPPuvfZLUxUQrQ0WZML0QYau4oGuNAXxAK3AwmWIV4CjYCnAD0elzxTdBciIEytX3izDk0HaOITp8ZZytrHif7gvtFLeh5cRfsg1TwG2Wp1wBanb+wWY7YQizk/l0TNikfg68aH4=";
        System.out.println(url.substring(url.indexOf(SEPARATOR) + SEPARATOR.length()));
    }
}
