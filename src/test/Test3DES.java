package test;

import java.io.ByteArrayOutputStream;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Test3DES {

	private static final String Algorithm = "DES"; // 定义 加密算法,可用
													// DES,DESede,Blowfish

	private static final String hexString = "0123456789ABCDEF";

	/**
	 * 
	 * 
	 * 
	 * @param keybyte
	 *            加密密钥，长度为24字节
	 * 
	 * @param src
	 *            字节数组(根据给定的字节数组构造一个密钥。 )
	 * 
	 * @return
	 */

	public static byte[] encryptMode(byte[] keybyte, byte[] src) {

		try {

			// 根据给定的字节数组和算法构造一个密钥

			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 加密

			Cipher c1 = Cipher.getInstance(Algorithm);

			c1.init(Cipher.ENCRYPT_MODE, deskey);

			return c1.doFinal(src);

		} catch (java.security.NoSuchAlgorithmException e1) {

			e1.printStackTrace();

		} catch (javax.crypto.NoSuchPaddingException e2) {

			e2.printStackTrace();

		} catch (java.lang.Exception e3) {

			e3.printStackTrace();

		}

		return null;

	}

	/**
	 * 
	 * 
	 * 
	 * @param keybyte
	 *            密钥
	 * 
	 * @param src
	 *            需要解密的数据
	 * 
	 * @return
	 */

	public static byte[] decryptMode(byte[] keybyte, byte[] src) {

		try {

			// 生成密钥

			SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

			// 解密

			Cipher c1 = Cipher.getInstance(Algorithm);

			c1.init(Cipher.DECRYPT_MODE, deskey);

			return c1.doFinal(src);

		} catch (java.security.NoSuchAlgorithmException e1) {

			e1.printStackTrace();

		} catch (javax.crypto.NoSuchPaddingException e2) {

			e2.printStackTrace();

		} catch (java.lang.Exception e3) {

			e3.printStackTrace();

		}

		return null;

	}

	/**
	 * 
	 * 字符串转为16进制
	 * 
	 * @param str
	 * 
	 * @return
	 */

	public static String encode(String str)

	{

		// 根据默认编码获取字节数组

		byte[] bytes = str.getBytes();

		StringBuilder sb = new StringBuilder(bytes.length * 2);

		// 将字节数组中每个字节拆解成2位16进制整数

		for (int i = 0; i < bytes.length; i++)

		{

			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));

			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));

		}

		return sb.toString();

	}

	/**
	 * 
	 * 
	 * 
	 * @param bytes
	 * 
	 * @return
	 * 
	 *         将16进制数字解码成字符串,适用于所有字符（包括中文）
	 */

	public static String decode(String bytes)

	{

		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);

		// 将每2位16进制整数组装成一个字节

		for (int i = 0; i < bytes.length(); i += 2)

			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));

		return new String(baos.toByteArray());

	}

	// 转换成十六进制字符串

	public static String byte2hex(byte[] b) {

		String hs = "";

		String stmp = "";

		for (int n = 0; n < b.length; n++) {

			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));

			if (stmp.length() == 1)

				hs = hs + "0" + stmp;

			else

				hs = hs + stmp;

			if (n < b.length - 1)

				hs = hs + ":";

		}

		return hs.toUpperCase();

	}

	public static String byte2HexStr(byte[] b) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
			sb.append("");
		}
		return sb.toString().toUpperCase().trim();
	}

	public static void main(String[] args) {

		// 添加新安全算法,如果用JCE就要把它添加进去

		// 这里addProvider方法是增加一个新的加密算法提供者(个人理解没有找到好的答案,求补充)

		Security.addProvider(new com.sun.crypto.provider.SunJCE());

		// byte数组(用来生成密钥的)

		final byte[] keyBytes = "12345678".getBytes();

		String szSrc = "1";

		System.out.println("加密前的字符串:" + szSrc);
		
		byte[] encoded = encryptMode(keyBytes, szSrc.getBytes());
		System.out.println("============"+encoded.length);
		System.out.println(byte2HexStr(encoded));

		byte[] srcBytes = decryptMode(keyBytes, encoded);
		
		System.out.println("解密后的字符串:" + new String(srcBytes));

		// ------------------------------------------------------------

		String byt = "字符串转换为16进制";

		byt = encode(byt);

		System.out.println(byt);

		byt = decode(byt);

		System.out.println(byt);

	}

}
