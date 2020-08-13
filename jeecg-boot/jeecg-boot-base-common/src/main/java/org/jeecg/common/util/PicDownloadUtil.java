package org.jeecg.common.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

@Slf4j
public class PicDownloadUtil {

	private static final String WX_PIC_PATH_DIR         	= "/www/download/mamamiya/wx-pic/pic/";
	private static final String PIC_HOST                 	= "http://mamamiya.club/pic/";
	private static final String PIC_SUFFIX_JPG              = ".jpg";

	public static String downloadPic(String urlStr) {
		if(StringUtils.isEmpty(urlStr) || !urlStr.contains("http")) {
			log.warn("PicDownloadUtil downloadPic params error!");
			return "";
		}
		String md5 = MD5Util.MD5Encode(urlStr, "utf8");
		String fileName = md5 + PIC_SUFFIX_JPG;
		String newPicdUrl = PIC_HOST + fileName;
		try{
			URL url = new URL(urlStr);
			URLConnection con = url.openConnection();
			con.setConnectTimeout(5000);
			InputStream is = con.getInputStream();
			byte[] bs = new byte[1024];
			int len;
			File sf = new File(WX_PIC_PATH_DIR + fileName);
			if(sf.exists()) {
				is.close();
				log.info("Repeat Pic, Location -> " + newPicdUrl);
				return newPicdUrl;
			}
			OutputStream os = new FileOutputStream(sf);
			while ((len = is.read(bs)) != -1) {
				os.write(bs, 0, len);
			}
			os.close();
			is.close();


			log.info("PicDownloadUtil downloadPic success -> " + newPicdUrl);
			return newPicdUrl;
		} catch (IOException e) {
			log.error("PicDownloadUtil downloadPic error -> " + e.getMessage(), e);
			return "";
		}
	}

}
