package cn.fund123.shumi.security;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * @ClassName CertificateCoder
 * @Description 使用数字证书加密解密以及签名和验证签名类
 * @author lijh@fund123.cn
 * @date 2013年10月22日
 */
public class CertificateCoder {

	public static final String CERT_TYPE = "X.509";
	private static final String STORE_TYPE = "PKCS12";
	private static Map<String, KeyStore> keyStoreMaps = new HashMap<String, KeyStore>();
	private static Map<String,Certificate> certificateMaps = new HashMap<String, Certificate>();

	private  PrivateKey getPrivateKeyByKeyStore(String keyStorePath,
			String alias, String password) throws Exception {
		KeyStore ks = null;
		ks = getKeyStore(keyStorePath, password);
		if(alias == null){
			return (PrivateKey) ks.getKey((String)ks.aliases().nextElement(), password.toCharArray());
		}else{
			return (PrivateKey) ks.getKey(alias, password.toCharArray());
		}
	}

	private  PublicKey getPublicKeyByCertificate(String certificatePath)
			throws Exception {

		Certificate certificate = getCertificate(certificatePath);
		return certificate.getPublicKey();

	}

	private  X509Certificate getCertificate(String certificatePath)
			throws Exception {
		String lastModified = Long.toString(new File(certificatePath).lastModified());
		Certificate certificate = certificateMaps.get(certificatePath+lastModified);
		if(certificate != null) return (X509Certificate)certificate;
		CertificateFactory certificateFactory = CertificateFactory
				.getInstance(CERT_TYPE);
		FileInputStream in = new FileInputStream(certificatePath);
		certificate = certificateFactory.generateCertificate(in);
		in.close();
		certificateMaps.put(certificatePath+lastModified, certificate);
		return (X509Certificate) certificate;
	}

	private  KeyStore getKeyStore(String keyStorePath, String password)throws Exception{
		String lastModified = Long.toString(new File(keyStorePath).lastModified());
		KeyStore ks = keyStoreMaps.get(keyStorePath + lastModified);
		if(ks!=null)return ks;
//	KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
		ks = KeyStore.getInstance(STORE_TYPE);
		FileInputStream in = new FileInputStream(keyStorePath);
		ks.load(in, password.toCharArray());
		keyStoreMaps.put(keyStorePath + lastModified, ks);
		in.close();
		return ks;
	}
	public byte[] encryptByPrivateKey(byte[] data, String keyStorePath,
			String alias, String password) throws Exception {
		PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias,
				password);
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return cipher.doFinal(data);

	}

	public byte[] decryptByPrivateKey(byte[] data, String keyStorePath,
			String alias, String password) throws Exception {

		PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias,
				password);
		Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(data);

	}

	public byte[] encryptByPublicKey(byte[] data, String certificatePath)
			throws Exception {

		PublicKey publicKey = getPublicKeyByCertificate(certificatePath);
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);

	}

	public byte[] decryptByPublicKey(byte[] data, String certificatePath)
			throws Exception {

		PublicKey publicKey = getPublicKeyByCertificate(certificatePath);
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return cipher.doFinal(data);

	}

	public byte[] sign(byte[] sign, String keyStorePath, String alias,
			String password, String certificatePath) throws Exception {

		X509Certificate x509Certificate = getCertificate(certificatePath);
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		PrivateKey privateKey = getPrivateKeyByKeyStore(keyStorePath, alias,
				password);
		signature.initSign(privateKey);
		signature.update(sign);
		return signature.sign();
	}

	public  boolean verify(byte[] data, byte[] sign,
			String certificatePath) throws Exception {
		X509Certificate x509Certificate = (X509Certificate) getCertificate(certificatePath);
		Signature signature = Signature.getInstance(x509Certificate
				.getSigAlgName());
		signature.initVerify(x509Certificate);
		signature.update(data);
		return signature.verify(sign);

	}

	public byte[] encryptByDBPublicKey(byte[] data, String certificateStr,
		String merchantName) throws Exception{
		Certificate certificate = certificateMaps.get(merchantName);
		if(certificate == null){
			CertificateFactory certificateFactory = CertificateFactory.getInstance(CERT_TYPE);
			InputStream in = new ByteArrayInputStream(certificateStr.getBytes("UTF-8"));
			certificate = certificateFactory.generateCertificate(in);
			certificateMaps.put(merchantName, certificate);
		}
		PublicKey publicKey = ((X509Certificate) certificate).getPublicKey();
		Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return cipher.doFinal(data);
	}}
