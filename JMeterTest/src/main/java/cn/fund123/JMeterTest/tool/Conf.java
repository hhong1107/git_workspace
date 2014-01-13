package cn.fund123.JMeterTest.tool;


public class Conf {
	private String certificatePath = "D:/ssl/certs/server.cer";
	private String keyStorePath = "D:/ssl/certs/server.pfx";
	private String keyStorePass = "123456789";
	private String secretKey = "16ACE2B797EE376D1F4EC72A4EFB0C28";
	private String apiUri = "test";
	private String baseUri = "http://127.0.0.1:7001/tiger/rs/";
	
	
	public String getCertificatePath() {
		return certificatePath;
	}
	public void setCertificatePath(String certificatePath) {
		this.certificatePath = certificatePath;
	}
	public String getKeyStorePath() {
		return keyStorePath;
	}
	public void setKeyStorePath(String keyStorePath) {
		this.keyStorePath = keyStorePath;
	}
	public String getKeyStorePass() {
		return keyStorePass;
	}
	public void setKeyStorePass(String keyStorePass) {
		this.keyStorePass = keyStorePass;
	}
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getApiUri() {
		return apiUri;
	}
	public void setApiUri(String apiUri) {
		this.apiUri = apiUri;
	}
	public String getBaseUri() {
		return baseUri;
	}
	public void setBaseUri(String baseUri) {
		this.baseUri = baseUri;
	}
	
}
