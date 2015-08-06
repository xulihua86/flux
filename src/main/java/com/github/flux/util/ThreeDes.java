package com.github.flux.util;

import java.net.URLEncoder;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 3des加密解密
 */
public class ThreeDes {
	private static final Logger logger = LoggerFactory.getLogger(ThreeDes.class);
	private static final String Algorithm = "desede" + "/CBC/PKCS5Padding"; // 定义
																			// 加密算法,可用
	// DES,DESede,Blowfish

	final static byte[] keyBytes = { 0x11, 0x22, 0x4F, 0x58, (byte) 0x88, 0x10,
			0x40, 0x38, 0x28, 0x25, 0x79, 0x51, (byte) 0xCB, (byte) 0xDD, 0x55,
			0x66, 0x77, 0x29, 0x74, (byte) 0x98, 0x30, 0x40, 0x36, (byte) 0xE2 }; // 24字节的密钥

	// keybyte为加密密钥，长度为24字节
	// src为被加密的数据缓冲区（源）
	public static byte[] encryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(keybyte);
			SecretKeyFactory keyfactory = SecretKeyFactory
					.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			// 加密
			Cipher c1 = Cipher.getInstance(Algorithm);
			byte[] iv = new byte[8];
			IvParameterSpec ips = new IvParameterSpec(iv);
			c1.init(Cipher.ENCRYPT_MODE, deskey, ips);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			logger.error("",e1);
		} catch (javax.crypto.NoSuchPaddingException e2) {
			logger.error("",e2);
		} catch (java.lang.Exception e3) {
			logger.error("",e3);
		}
		return null;
	}

	// keybyte为加密密钥，长度为24字节
	// src为加密后的缓冲区
	public static byte[] decryptMode(byte[] keybyte, byte[] src) {
		try {
			// 生成密钥
			Key deskey = null;
			DESedeKeySpec spec = new DESedeKeySpec(keybyte);
			SecretKeyFactory keyfactory = SecretKeyFactory
					.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			byte[] iv = new byte[8];
			IvParameterSpec ips = new IvParameterSpec(iv);
			c1.init(Cipher.DECRYPT_MODE, deskey, ips);
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			logger.error("",e1);
		} catch (javax.crypto.NoSuchPaddingException e2) {
			logger.error("",e2);
		} catch (java.lang.Exception e3) {
			logger.error("",e3);
		}
		return null;
	}


	/**
	 * 二行制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) { // 一个字节的数，
		// 转成16进制字符串
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			// 整数转成十六进制表示
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase(); // 转成大写
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			// 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个进制字节
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static String encrypt(String KeyMing) {
		try {
			byte[] encoded = encryptMode(keyBytes, KeyMing.getBytes());
			String str1 = byte2hex(encoded);
			return str1;
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}

	public static String decrypt(String KeyMi) {
		try {
			byte[] encoded = hex2byte(KeyMi.getBytes());
			byte[] srcBytes = decryptMode(keyBytes, encoded);
			return new String(srcBytes);
		} catch (Exception e) {
			logger.error("",e);
			return null;
		}
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		// 添加新安全算法,如果用JCE就要把它添加进去
		// Security.addProvider(new com.sun.crypto.provider.SunJCE());

//		String szSrc = "1581033222115810332221水电费都是发";// "This is a 3DES test. 测试";
//		System.out.println("加密前的字符串:" + szSrc);
		// String s1 = encrypt("60000170230|WEB|WEB|813e7f5a-b134-410a-bfe8-958e533bee19|1408590570297");
		// System.out.println("加密后的字符串:" + s1);
		// userId|deviceType|deviceId|token|lastLoginTime
		//String szSrc1 = decrypt("A50D7943372BEE6EEE209D3B0ED62684FA7F35E1EF06D34B5CB4C7BC620D3D01F1CA919C40D6A0152764FDAD1A68737F8DB007E15C1230B19EE0B6F06EBE2644A23222E11B19CFC2AF6776428B8FF07661BFBE5AE0C304A9ACF410697EAAAB408CAA76E6FEBCEC7CC2ADADA97F43D94E");
		//System.out.println("解密后的字符串:" + szSrc1);
		//System.out.println("对比结果=" + szSrc1.equals(szSrc));
		
		String s1 = "60000021643|APH|A000004F0B97A2|5876ad5a-1062-441a-b09f-e289a72a4ce5|1415588808032";
		System.out.println(encrypt(s1));
		
		// String s = "{'resId':4}";
		//String s = "{'resId':12,'appId':,'appname':12,'orgid':,'orgname':'','userid':'','mtitle':'请购单','stitle':'请购单','summary':'部门:a、整单金额:5.85,quote:','auditCount':1,'unreadCount':0}";
		// String s = "{'resId':12,'appId':23,'appname':12,'orgid':23,'orgname':'ww','userid':'23','mtitle':'请购单','stitle':'请购单','summary':'部门','auditCount':1,'unreadCount':0}";
		
		// System.out.println(URLEncoder.encode(s));
		
		String s = "{'resId':1,'appId':1,'appname':'1','orgid':1,'orgname':'2','userid':'1','mtitle':'请购单','stitle':'张三请您审批','summary':'整单金额5.85','quote':'请登录到T+系统或手机端的工作圈处理!','auditCount':1,'unreadCount':0}";
		System.out.println(URLEncoder.encode(s));
		
		
		
	}
}