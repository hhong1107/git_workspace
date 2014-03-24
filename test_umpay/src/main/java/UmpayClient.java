import com.umpay.api.common.ReqData;
import com.umpay.api.exception.ReqDataException;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import sun.net.www.http.HttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * Created by hoho on 14-2-14.
 */
public class UmpayClient {

    public static void doGet(ReqData reqData) throws IOException {


        String url = reqData.getUrl();
//        String url = "http://122.224.228.55:9000/payment/pay/Complete/umpay";
//        url = URLEncoder.encode()
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            HttpEntity responseEntity = response.getEntity();
            InputStream input = responseEntity.getContent();
            String responseStr = IOUtils.toString(input);
            System.out.println("*****************************************************8");
            System.out.println(responseStr);
        } finally {
            response.close();
        }
    }

    public static void main(String[] args) throws IOException, ReqDataException {
        doGet(UmpayPack.doPack());
    }
}
