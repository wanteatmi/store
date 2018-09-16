package com.wut.store.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PaymentUtil {

	private static String encodingCharset = "UTF-8";
	
	/**
	 * 鐢熸垚hmac鏂规硶
	 * 
	 * @param p0_Cmd 涓氬姟绫诲瀷
	 * @param p1_MerId 鍟嗘埛缂栧彿
	 * @param p2_Order 鍟嗘埛璁㈠崟鍙�
	 * @param p3_Amt 鏀粯閲戦
	 * @param p4_Cur 浜ゆ槗甯佺
	 * @param p5_Pid 鍟嗗搧鍚嶇О
	 * @param p6_Pcat 鍟嗗搧绉嶇被
	 * @param p7_Pdesc 鍟嗗搧鎻忚堪
	 * @param p8_Url 鍟嗘埛鎺ユ敹鏀粯鎴愬姛鏁版嵁鐨勫湴鍧�
	 * @param p9_SAF 閫佽揣鍦板潃
	 * @param pa_MP 鍟嗘埛鎵╁睍淇℃伅
	 * @param pd_FrpId 閾惰缂栫爜
	 * @param pr_NeedResponse 搴旂瓟鏈哄埗
	 * @param keyValue 鍟嗘埛瀵嗛挜
	 * @return
	 */
	public static String buildHmac(String p0_Cmd,String p1_MerId,
			String p2_Order, String p3_Amt, String p4_Cur,String p5_Pid, String p6_Pcat,
			String p7_Pdesc,String p8_Url, String p9_SAF,String pa_MP,String pd_FrpId,
			String pr_NeedResponse,String keyValue) {
		StringBuilder sValue = new StringBuilder();
		// 涓氬姟绫诲瀷
		sValue.append(p0_Cmd);
		// 鍟嗘埛缂栧彿
		sValue.append(p1_MerId);
		// 鍟嗘埛璁㈠崟鍙�
		sValue.append(p2_Order);
		// 鏀粯閲戦
		sValue.append(p3_Amt);
		// 浜ゆ槗甯佺
		sValue.append(p4_Cur);
		// 鍟嗗搧鍚嶇О
		sValue.append(p5_Pid);
		// 鍟嗗搧绉嶇被
		sValue.append(p6_Pcat);
		// 鍟嗗搧鎻忚堪
		sValue.append(p7_Pdesc);
		// 鍟嗘埛鎺ユ敹鏀粯鎴愬姛鏁版嵁鐨勫湴鍧�
		sValue.append(p8_Url);
		// 閫佽揣鍦板潃
		sValue.append(p9_SAF);
		// 鍟嗘埛鎵╁睍淇℃伅
		sValue.append(pa_MP);
		// 閾惰缂栫爜
		sValue.append(pd_FrpId);
		// 搴旂瓟鏈哄埗
		sValue.append(pr_NeedResponse);
		
		return PaymentUtil.hmacSign(sValue.toString(), keyValue);
	}
	
	/**
	 * 杩斿洖鏍￠獙hmac鏂规硶
	 * 
	 * @param hmac 鏀粯缃戝叧鍙戞潵鐨勫姞瀵嗛獙璇佺爜
	 * @param p1_MerId 鍟嗘埛缂栧彿
	 * @param r0_Cmd 涓氬姟绫诲瀷
	 * @param r1_Code 鏀粯缁撴灉
	 * @param r2_TrxId 鏄撳疂鏀粯浜ゆ槗娴佹按鍙�
	 * @param r3_Amt 鏀粯閲戦
	 * @param r4_Cur 浜ゆ槗甯佺
	 * @param r5_Pid 鍟嗗搧鍚嶇О
	 * @param r6_Order 鍟嗘埛璁㈠崟鍙�
	 * @param r7_Uid 鏄撳疂鏀粯浼氬憳ID
	 * @param r8_MP 鍟嗘埛鎵╁睍淇℃伅
	 * @param r9_BType 浜ゆ槗缁撴灉杩斿洖绫诲瀷
	 * @param keyValue 瀵嗛挜
	 * @return
	 */
	public static boolean verifyCallback(String hmac, String p1_MerId,
			String r0_Cmd, String r1_Code, String r2_TrxId, String r3_Amt,
			String r4_Cur, String r5_Pid, String r6_Order, String r7_Uid,
			String r8_MP, String r9_BType, String keyValue) {
		StringBuilder sValue = new StringBuilder();
		// 鍟嗘埛缂栧彿
		sValue.append(p1_MerId);
		// 涓氬姟绫诲瀷
		sValue.append(r0_Cmd);
		// 鏀粯缁撴灉
		sValue.append(r1_Code);
		// 鏄撳疂鏀粯浜ゆ槗娴佹按鍙�
		sValue.append(r2_TrxId);
		// 鏀粯閲戦
		sValue.append(r3_Amt);
		// 浜ゆ槗甯佺
		sValue.append(r4_Cur);
		// 鍟嗗搧鍚嶇О
		sValue.append(r5_Pid);
		// 鍟嗘埛璁㈠崟鍙�
		sValue.append(r6_Order);
		// 鏄撳疂鏀粯浼氬憳ID
		sValue.append(r7_Uid);
		// 鍟嗘埛鎵╁睍淇℃伅
		sValue.append(r8_MP);
		// 浜ゆ槗缁撴灉杩斿洖绫诲瀷
		sValue.append(r9_BType);
		String sNewString = PaymentUtil.hmacSign(sValue.toString(), keyValue);
		return sNewString.equals(hmac);
	}
	
	/**
	 * @param aValue
	 * @param aKey
	 * @return
	 */
	public static String hmacSign(String aValue, String aKey) {
		byte k_ipad[] = new byte[64];
		byte k_opad[] = new byte[64];
		byte keyb[];
		byte value[];
		try {
			keyb = aKey.getBytes(encodingCharset);
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			keyb = aKey.getBytes();
			value = aValue.getBytes();
		}

		Arrays.fill(k_ipad, keyb.length, 64, (byte) 54);
		Arrays.fill(k_opad, keyb.length, 64, (byte) 92);
		for (int i = 0; i < keyb.length; i++) {
			k_ipad[i] = (byte) (keyb[i] ^ 0x36);
			k_opad[i] = (byte) (keyb[i] ^ 0x5c);
		}

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {

			return null;
		}
		md.update(k_ipad);
		md.update(value);
		byte dg[] = md.digest();
		md.reset();
		md.update(k_opad);
		md.update(dg, 0, 16);
		dg = md.digest();
		return toHex(dg);
	}

	public static String toHex(byte input[]) {
		if (input == null)
			return null;
		StringBuffer output = new StringBuffer(input.length * 2);
		for (int i = 0; i < input.length; i++) {
			int current = input[i] & 0xff;
			if (current < 16)
				output.append("0");
			output.append(Integer.toString(current, 16));
		}

		return output.toString();
	}

	/**
	 * 
	 * @param args
	 * @param key
	 * @return
	 */
	public static String getHmac(String[] args, String key) {
		if (args == null || args.length == 0) {
			return (null);
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < args.length; i++) {
			str.append(args[i]);
		}
		return (hmacSign(str.toString(), key));
	}

	/**
	 * @param aValue
	 * @return
	 */
	public static String digest(String aValue) {
		aValue = aValue.trim();
		byte value[];
		try {
			value = aValue.getBytes(encodingCharset);
		} catch (UnsupportedEncodingException e) {
			value = aValue.getBytes();
		}
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		return toHex(md.digest(value));

	}
	
//	public static void main(String[] args) {
//		System.out.println(hmacSign("AnnulCard1000043252120080620160450.0http://localhost/SZXpro/callback.asp鏉�?4564868265473632445648682654736324511","8UPp0KE8sq73zVP370vko7C39403rtK1YwX40Td6irH216036H27Eb12792t"));
//	}
}

