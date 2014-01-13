package cn.fund123.JMeterTest.tool;

public class Response {

    public static final String DATE_FORMAT = "YYYYMMDDHHMMSS";
    public static final String VERSION = "version";
    public static final String CODE = "code";
    public static final String CODE_DESC = "description";
    public static final String SYS_DATETIME = "sysDateTime";
    public static final String MERCHANT_ID = "merchantId";
    public static final String SIGN = "sign";
    public static final String DATA = "data";

	private String version;	     //服务接口版本
	private String code;	     //返回码
	private String description;  //返回码（错误）信息
	private String sign;	     //签名
    private String data;         //响应体
    private String sysDateTime;  //系统时间YYYYMMDDHHMMSS
    private String merchantId;   //商户号

    public String getSysDateTime() {
        return sysDateTime;
    }

    public void setSysDateTime(String sysDateTime) {
        this.sysDateTime = sysDateTime;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

}
