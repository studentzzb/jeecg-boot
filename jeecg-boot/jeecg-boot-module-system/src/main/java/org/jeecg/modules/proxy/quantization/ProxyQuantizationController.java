package org.jeecg.modules.proxy.quantization;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/proxy")
@Slf4j
public class ProxyQuantizationController {

    private static final String PROXY_HOST = "http://athena.iphonepad.cn";
    private static final String PROXY_PRE = "proxy";

    @GetMapping(value = "/quant/macd/**")
    public String proxyMacd(HttpServletRequest httpRequest) {

//        Authenticator proxyAuthenticator = new Authenticator() {
//            @Override
//            public Request authenticate(Route route, Response response) throws IOException {
//                String credential = Credentials.basic(username, password);
//                return response.request().newBuilder()
//                        .header("Proxy-Authorization", credential)
//                        .build();
//            }
//        };

        OkHttpClient client = new OkHttpClient().newBuilder().connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)   //.proxyAuthenticator(proxyAuthenticator)    // 解决内存溢出问题
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS)).build();


        // 参数获取
        Map<String, String> paramsMap = Maps.newHashMap();
        Enumeration<String> paramNames = httpRequest.getParameterNames();
        while(paramNames.hasMoreElements()) {
            String element = paramNames.nextElement();
            paramsMap.put(element, httpRequest.getParameter(element));
        }
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody responseBody = RequestBody.create(JSON, com.alibaba.fastjson.JSON.toJSONString(paramsMap));

        String path = httpRequest.getServletPath();
        String url = PROXY_HOST + path.substring(path.indexOf(PROXY_PRE) + PROXY_PRE.length());
        Request request = new Request.Builder().url(url).post(responseBody).build();

        try {
            Response response = client.newCall(request).execute();
            if(response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new IOException("服务器端错误: " + response);
            }
        } catch (Exception e) {
            log.error("Exception happens->{}, by url->{}", e.getMessage(), url);
            return "";
        }
    }
}