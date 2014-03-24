import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hoho on 14-3-11.
 */
public class HttpTest {
    private static final String CHARSET = "UTF-8";

    public static void main(String[] args) {
        String ss = doGet("http://192.168.2.121:8080/tiger/rs/callback/umpay/payResultNotify/v1?amount=1&amt_type=RMB&charset=UTF-8&error_code=0000&mer_date=20140310&mer_id=6457&order_id=20140310000030&pay_date=20140310&pay_seq=4403940178042010&pay_type=DEBITCARD&service=pay_result_notify&settle_date=20140310&trade_no=1403102205442831&trade_state=TRADE_SUCCESS&version=4.0&sign=Lo%2BSNr%2FzemFMMeZBlJQ84tQLjaoVS%2FzSAcWsKAX085s0%2BmXhlVI52xlOvLbPs1cYGvBCXciyS%2BZ0MbZ2YXuzrqm%2F3SXRjyCkSz3csm6GJ7NiiplmEIBP9kHN19NhV%2FOv8Al9T3O2wZf9Phqhfwuk%2Bv32Vnljx8M5hhg6mtaKvw0%3D&sign_type=RSA");
        System.out.println(ss);
    }


    public static String doGet(String url) {
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

}
