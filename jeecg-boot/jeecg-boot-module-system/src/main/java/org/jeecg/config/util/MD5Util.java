//package org.jeecg.config.util;
//
//import org.springframework.util.DigestUtils;
//
//public class MD5Util {
//
//	//盐，用于混交md5
//	private static final String slat = "&%5123***&&%%$$#@";
//
//	public static String getMD5(String str) {
//		String base = str + "/" + slat;
//		String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
//		return md5;
//	}
//
////	public static void main(String[] args) {
////		String md5 = getMD5("fddfdafda");
////		System.out.println("md5->" + md5);
////	}
//
//}