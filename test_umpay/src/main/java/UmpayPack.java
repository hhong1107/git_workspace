import com.umpay.api.common.Const;
import com.umpay.api.common.ReqData;
import com.umpay.api.exception.ReqDataException;
import com.umpay.api.paygate.v40.Mer2Plat_v40;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hoho on 14-2-14.
 */
public class UmpayPack {

    /*涓嬪崟*/
    public static ReqData doPack() throws ReqDataException {

        Map<String, String> params = new HashMap<String, String>();
        params.put(Const.SERVICE, "debit_direct_pay");//接口名称
        params.put(Const.SIGN_TYPE, "RSA");//签名方式
        params.put(Const.CHARSET, "UTF-8");//参数字符编码集
        params.put(Const.VERSION, "4.0");//版本号
        params.put(Const.MER_ID, "6457");//商户编号
        params.put(Const.NOTIFY_URL, "http://122.224.228.59:7001/tiger/rs/callback/umpay/payResultNotify/v1");//服务器异步通知地址
        params.put(Const.ORDER_ID, "20140313000003");//订单号
        params.put(Const.MER_DATE, "20140313");//日期
//        params.put(Const.AMOUNT, UmpayUtils.convertToUmpayAmt(transAmt));//金额
        params.put(Const.AMOUNT, "1");//金额
        params.put(Const.AMT_TYPE, "RMB");//币种
        params.put("pay_type", "DEBITCARD");//支付方式 DEBITCARD--借记卡
        params.put("debit_pay_type", "1");//借记卡支付类别 1--借记卡无磁无密;2--借记卡无磁有密
        params.put("media_id", "");//媒介标识
        params.put("media_type", "");//媒介类型

/*        params.put("card_id", "6226095712155253");//卡号
        params.put("identity_type", "1");//证件类型
        params.put("identity_code", "330724198511075613");//证件号
        params.put("card_holder", "李剑虹");//持卡人姓名*/


        params.put("card_id", "6225885865451959");//卡号
        params.put("identity_type", "1");//证件类型
        params.put("identity_code", "362204198804098127");//证件号
        params.put("card_holder", "陈璐璐");//持卡人姓名
        ReqData reqData = Mer2Plat_v40.ReqDataByGet(params);

        return reqData;
    }

    /*鍗曠瑪鏌ヨ*/
    public static ReqData doPack4QueryOrder() throws ReqDataException {

        Map<String, String> params = new HashMap<String, String>();

        params.put("service","query_order");
        params.put("sign_type","RSA");
        params.put("charset","UTF-8");
        params.put("mer_id","6457");
        params.put("version","4.0");
        params.put("res_format","HTML");
        params.put("order_id","20140313000001");
        params.put("mer_date","20140305");
        params.put("amount","1");
//        params.put("trade_no","1402241102118921");
        params.put("goods_id","");
        params.put("media_id","");
        params.put("media_type","MOBILE");
        ReqData reqData = Mer2Plat_v40.ReqDataByGet(params);
        return reqData;
    }
    public static ReqData packDownloadSetteFileParams() throws ReqDataException {
        Map data = new HashMap();
        data.put(Const.SERVICE, "download_settle_file");
        data.put(Const.SIGN_TYPE, "RSA");
        data.put(Const.MER_ID, "6457");
        data.put(Const.SETTLE_DATE, "20140309");
        data.put(Const.VERSION, "4.0");
        ReqData reqData = null;
            reqData = Mer2Plat_v40.ReqDataByGet(data);
        return reqData;
    }

}
