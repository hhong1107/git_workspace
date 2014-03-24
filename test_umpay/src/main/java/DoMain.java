import com.umpay.api.common.ReqData;
import com.umpay.api.exception.ReqDataException;
import com.umpay.api.util.ProFileUtil;

import java.io.IOException;

/**
 * Created by hoho on 14-2-14.
 */
public class DoMain {
    public static void main(String[] args) throws IOException, ReqDataException {
        ProFileUtil.setPropsFilePath("C:\\D\\workspace\\MyTest\\test_umpay\\src\\main\\resources\\SignVerProp.properties");



        UmpayPack umpayPack = new UmpayPack();

        UmpayClient umpayClient = new UmpayClient();
//        ReqData reqData = umpayPack.doPack4QueryOrder();
//        ReqData reqData = umpayPack.doPack();
        ReqData reqData = umpayPack.packDownloadSetteFileParams();
        System.out.println(reqData.getUrl());
        umpayClient.doGet(reqData);
    }
}
