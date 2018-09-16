package com.wut.store.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

/**
 * 鑷畾涔� java.util.Date鏃ユ湡杞崲鍣�
 * 
 * 
 */
public class MyDateConverter implements Converter {

	@Override
	// 灏唙alue 杞崲 c 瀵瑰簲绫诲瀷
	// 瀛樺湪Class鍙傛暟鐩殑缂栧啓閫氱敤杞崲鍣紝濡傛灉杞崲鐩爣绫诲瀷鏄‘瀹氱殑锛屽彲浠ヤ笉浣跨敤c 鍙傛暟
	public Object convert(Class c, Object value) {
		String strVal = (String) value;
		// 灏哠tring杞崲涓篋ate --- 闇�瑕佷娇鐢ㄦ棩鏈熸牸寮忓寲
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date date = dateFormat.parse(strVal);
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}
