package cn.fund123.shumi.security;

import java.security.MessageDigest;

import cn.fund123.shumi.exceptions.VerifySignException;



/**
 * @ClassName Secure
 * @Description 实现加密解密签名和验证签名
 * @author lijh@fund123.cn
 * @date 2013年10月23日
 */
public class Secure {
	private static final String CHARSET = "UTF-8"; 
	private  static  Secure securce = new Secure();
	private CertificateCoder certificateCoder;
	private AESCoder aesCoder;
	
	public static Secure getInstance(){
		return securce;
	}
	
	
	private Secure(){
		this.certificateCoder = new CertificateCoder();
		this.aesCoder = new AESCoder();
	}
	
	/**
	 *  密钥加密
	 * @param  data 明文数据
	 * @param  alias 证书在密钥库中的别名
	 * @throws VerifySignException
	 * @return String 返回密文
	 */
	public String enByprivateKey(String keyStorePath,String password,String alias,String data) throws VerifySignException{
		/**
		 * 1.生成AES密钥
		 * 2.利用AES密钥对数据做对称加密
		 * 3.获取密钥库密钥keystore
		 * 4.利用密钥库密钥对AES密钥做非对称加密
		 * 5.将AES密钥加密后字段和数据加密后字段合并
		 */
		String enmsg;
		try {
			byte[] aesKey = aesCoder.initKey();
			byte[] dataEn = aesCoder.encrypt(data.getBytes(CHARSET), aesKey);
			byte[] aesKeyEn = certificateCoder.encryptByPrivateKey(aesKey, keyStorePath, alias, password);
			String aesKeyStr = new String(Base64.encode(aesKeyEn),CHARSET);
			enmsg = length2Mark(aesKeyStr.length()) + aesKeyStr + new String(Base64.encode(dataEn),CHARSET);
			/*aeskeyEn转字符串后长度344*/
			return enmsg;
		} catch (Exception e) {
			throw new VerifySignException(data,e);
		}
	}
	/**
	 * 密钥加密
	 * @param  data 明文数据
	 * @throws VerifySignException
	 * @return String 返回密文
	 */
	public String enByprivateKey(String keyStorePath,String password,String data) throws VerifySignException{
		return enByprivateKey(keyStorePath,password,null,data);
	}
	
	/**
	 *  密钥解密
	 * @param  enMsg 密文
	 * @param  alias 密钥别名
	 * @throws VerifySignException
	 * @return String 明文
	 */
	public String deByprivateKey(String keyStorePath,String password,String alias,String enMsg)throws VerifySignException{
		try {
			int aesKeyLen = mark2Length(enMsg.substring(0, 4));
			byte[] aesKeyEn = enMsg.substring(4, 4 + aesKeyLen).getBytes(CHARSET);
//			byte[] aesKeyEn = enMsg.substring(0, 344).getBytes();
//		      byte[] dataEn = enMsg.substring(344).getBytes();
			byte[] dataEn = enMsg.substring(4 + aesKeyLen).getBytes(CHARSET);
			byte[] aesKey = certificateCoder.decryptByPrivateKey(Base64.decode(new String(aesKeyEn,CHARSET)), keyStorePath, alias, password);
			byte[] data = aesCoder.decrypt(Base64.decode(new String(dataEn,CHARSET)), aesKey);
			return new String(data,CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
			throw new VerifySignException(enMsg,e);
		}
	}
	/**
	 * 
	 *  密钥解密
	 * @param  enMsg 密文
	 * @throws VerifySignException
	 * @return String 明文
	 */
	public String deByprivateKey(String keyStorePath,String password,String enMsg)throws VerifySignException{
		return deByprivateKey(keyStorePath,password, null,enMsg);
	}
	
	/**
	 *  公钥解密
	 * @param  enMsg 密文
	 * @param  certificatePath 证书地址
	 * @throws Exception
	 */
	public String deByPublicKey(String certificatePath,String enMsg)throws VerifySignException{
		/**
		 * 1.截取出AES密钥加密后的字段，长度为344
		 * 2.使用证书公钥解密AES密钥获得密钥字符串
		 * 3.还原AES密钥
		 * 4.使用AES密钥解密真实密文部分
		 */
		try {
			int aesKeyLen = mark2Length(enMsg.substring(0, 4));
			byte[] aesKeyEn = enMsg.substring(4, 4 + aesKeyLen).getBytes(CHARSET);
			byte[] dataEn = enMsg.substring(4 + aesKeyLen).getBytes(CHARSET);
			byte[] aesKey = certificateCoder.decryptByPublicKey(Base64.decode(new String(aesKeyEn)),certificatePath);
			byte[] data = aesCoder.decrypt(Base64.decode(new String(dataEn)), aesKey);
			return new String(data);
		} catch (Exception e) {
			throw new VerifySignException(enMsg,e);
		}
	}
	
	/**
	 * 公钥加密 
	 * @param  data 明文
	 * @param  certificatePath 公钥地址
	 * @throws VerifySignException
	 * @return String 密文
	 */
	public String enByPublicKey(String certificatePath,String data)throws VerifySignException{
		String enmsg;
		try {
			byte[] aesKey = aesCoder.initKey();
			byte[] dataEn = aesCoder.encrypt(data.getBytes(CHARSET), aesKey);
			byte[] aesKeyEn = certificateCoder.encryptByPublicKey(aesKey, certificatePath);
			String aesKeyStr = new String(Base64.encode(aesKeyEn),CHARSET);
			enmsg = length2Mark(aesKeyStr.length()) + aesKeyStr + new String(Base64.encode(dataEn),CHARSET);
			return enmsg;
		} catch (Exception e) {
			throw new VerifySignException(data,e);
		}
	}
	
	/**
	 * 公钥加密 (数据库使用)
	 * @param  data 明文
	 * @param  certificatePath 公钥地址
	 * @throws VerifySignException
	 * @return String 密文
	 */
	public String enByDBPublicKey(String merchantName,String certificate,String data)throws VerifySignException{
		String enmsg;
		try {
			byte[] aesKey = aesCoder.initKey();
			byte[] dataEn = aesCoder.encrypt(data.getBytes(CHARSET), aesKey);
			byte[] aesKeyEn = certificateCoder.encryptByDBPublicKey(aesKey, certificate,merchantName);
			String aesKeyStr = new String(Base64.encode(aesKeyEn),CHARSET);
			enmsg = length2Mark(aesKeyStr.length()) + aesKeyStr + new String(Base64.encode(dataEn),CHARSET);
			return enmsg;
		} catch (Exception e) {
			throw new VerifySignException(data,e);
		}
	}
	
	/**
	 * 签名
	 * @param  sign 数据
	 * @param  keyStorePath 密钥库路径
	 * @param  password 密钥库密码
	 * @param  certificatePath 证书路径
	 * @throws Exception
	 * @return String
	 */
	public  String sign(String keyStorePath,String password,String certificatePath,String sign) throws Exception {
		try{
			return new String(Base64.encode(certificateCoder.sign(sign.getBytes(CHARSET),keyStorePath,null,password,certificatePath)),CHARSET);
		}catch(Exception e){
			throw new VerifySignException(sign,e);
		}
		
	}
	public  String sign( String keyStorePath, String password,String certificatePath,String alias,String sign) throws Exception {
		try{
			return new String(Base64.encode(certificateCoder.sign(sign.getBytes(CHARSET),keyStorePath,alias,password,certificatePath)),CHARSET);
		}catch(Exception e){
			throw new VerifySignException(sign,e);
		}
		
		
	}
	
	/**
	 * 签名验证
	 * @param data 数据
	 * @param sign	签名数据
	 * @param certificatePath 证书路径
	 * @throws Exception
	 * @return boolean
	 */
	public  boolean verify(String certificatePath,String data, String sign) throws VerifySignException{
		try{
			return certificateCoder.verify(data.getBytes(CHARSET), Base64.decode(sign), certificatePath);
		}catch(Exception e){
			throw new VerifySignException(data,e);
		}
	}
	
	/**
	 * @Description: MD5生成签名 
	 * @param  data	签名数据
	 * @return String 32位签名数据
	 */
	public String signByMD5(String data){
		StringBuffer buf = new StringBuffer();
		StringBuffer resultString = new StringBuffer();
		try {
			byte[] signbyte = MessageDigest.getInstance("MD5").digest(data.getBytes(CHARSET));
			int i;
            for (int offset = 0; offset < signbyte.length; offset++) {
                    i = signbyte[offset];
                    if (i < 0)
                    i += 256;
                    if (i < 16)
                    buf.append("0");
                    buf.append(Integer.toHexString(i));
            }
    		resultString.append(buf.substring(7, 11)).append(buf.substring(0, 7))
			.append(buf.substring(22, 32)).append(buf.substring(11, 22));
		} catch (Exception e) {
			throw new VerifySignException(data,e);
		}
		return resultString.toString().toUpperCase();
	}
	
	/**
	 * @Description: MD5验证签名
	 * @param  data 原数据
	 * @param  signData 签名数据
	 * @return boolean
	 */
	public boolean verifyByMD5(String data,String signData){
		return signData.equalsIgnoreCase(signByMD5(data));
	}
	
	/**
	 * 将AESkey非对称加密后的长度转换
	 * @param len
	 * @return
	 * String
	 */
	private String length2Mark(int len){
		String lenStr = null;
		try {
			lenStr = String.valueOf(len);
			lenStr = "000"+lenStr;
			lenStr = lenStr.substring(lenStr.length()-4,lenStr.length());
			byte[] lenByte = {Byte.valueOf(lenStr.substring(2,4)),Byte.valueOf(lenStr.substring(0,2))};
			return new String(Base64.encode(lenByte),CHARSET);
		} catch (Exception e) {
			throw new VerifySignException(lenStr,e);
		}
	}
	
	/**
	 * 将标记秘钥长度的字符串转换成int
	 * @param mark
	 * @return
	 * int
	 */
	private int mark2Length(String mark){
		byte[] markByte = Base64.decode(mark);
		mark = String.valueOf(markByte[1]) + String.valueOf(markByte[0]);
		return Integer.valueOf(mark);
	}
}
