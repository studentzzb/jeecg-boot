package org.jeecg.config.util;

import com.google.common.collect.Maps;
import okhttp3.*;
import org.jeecg.config.util.ssl.HttpsUrlValidator;
import org.jeecg.config.util.ssl.TrustAllCerts;
import org.jeecg.config.util.ssl.TrustAllHostnameVerifier;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class MyOkhttpHelper {

    public static Request buildRequest(String url, Map<String, String> headerMap, boolean isPost, RequestBody requestBody) {
//        Request.Builder builder = new Request.Builder();
//        if(headerMap.size() > 0) {
//            for (String key : headerMap.keySet()) {
//                if(!key.startsWith(":")) {
//                    builder.addHeader(key, headerMap.get(key));
//                }
//            }
//        }
//        if(!StringUtils.isEmpty(url)) {
//            builder.url(url);
//        }
//        if(isPost) {
//            builder.post(requestBody);
//        }

        return  createRequestBuilder(url, headerMap, isPost, requestBody).build();
    }

    public static Request.Builder createRequestBuilder(String url, Map<String, String> headerMap, boolean isPost, RequestBody requestBody) {
        Request.Builder builder = new Request.Builder();
        if(headerMap.size() > 0) {
            for (String key : headerMap.keySet()) {
                if(!key.startsWith(":")) {
                    builder.addHeader(key, headerMap.get(key));
                }
            }
        }
        if(!StringUtils.isEmpty(url)) {
            builder.url(url);
        }
        if(isPost) {
            builder.post(requestBody);
        }

        return  builder;
    }

    private static Map<String, String> getHeaderMap(Request request) {
        Headers headers = request.headers();
        Map<String, String> headerMap = Maps.newHashMap();
        Set<String> headerNames = headers.names();
        if(headerNames != null && headerNames.isEmpty()) {
            for(String name : headerNames) {
                headerMap.put(name, headers.get(name));
            }
        }
        return headerMap;
    }

    public static String getResponseBody(Request request, boolean unzip, boolean isJson) throws Exception {
        OkHttpClient client = new OkHttpClient();
        if(request.isHttps()) {
//            if(isJson) {
                client = new OkHttpClient.Builder()
                        .sslSocketFactory(createSSLSocketFactory(), new TrustAllCerts())
                        .hostnameVerifier(new TrustAllHostnameVerifier()).build();
//            } else {
//                // 用jsoup的方式获取
//                String url = request.url().url().toString();
//                HttpsUrlValidator.retrieveResponseFromServer(url);
//                Document doc = Jsoup.connect(url)
//                        .headers(getHeaderMap(request))
//                        .timeout(10000).get();
//                return doc.getElementsByTag("body").html();
//            }

        }

        Response response = client.newCall(request).execute();
        if(response.isSuccessful() && response.body() != null) {
            try {
                return uncompress(response.body().bytes());
            } catch (Exception e) {
                return response.body().string();
            }

//            if(unzip) {
//                return uncompress(response.body().bytes());
//            } else {
//                return response.body().string();
//            }
        }
        return "";
    }

    private static byte[] compress(String str) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            try (GZIPOutputStream gzip = new GZIPOutputStream(out)) {
                gzip.write(str.getBytes(StandardCharsets.UTF_8));
            }
            return out.toByteArray();
            //return out.toString(StandardCharsets.ISO_8859_1);
            // Some single byte encoding
        }
    }

    private static String uncompress(byte[] str) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(str))) {
            int b;
            while ((b = gis.read()) != -1) {
                baos.write((byte) b);
            }
        }
        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }

    private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

}
