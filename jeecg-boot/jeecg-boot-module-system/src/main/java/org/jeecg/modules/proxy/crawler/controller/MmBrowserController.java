package org.jeecg.modules.proxy.crawler.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.jeecg.common.api.vo.Result;
import org.jeecg.config.enumer.RequestHeaderEnum;
import org.jeecg.config.util.MyOkhttpHelper;
import org.jeecg.modules.proxy.crawler.entity.MmCrawlerInfo;
import org.jeecg.modules.proxy.crawler.mapper.MmCrawlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/mm")
@Slf4j
public class MmBrowserController {

    private static Map<String, String> headerMap = Maps.newHashMap();

    @Autowired
    private MmCrawlerMapper mmCrawlerMapper;

    @PostMapping(value = "/browser/update")
    public String postBrowserInfo(HttpServletRequest request) {
//        Enumeration<String> paramNames = request.getParameterNames();
//        System.out.println("========================Param Start========================");
//        while(paramNames.hasMoreElements()) {
//            String name = paramNames.nextElement();
//            System.out.println(name + "---" + request.getParameter(name));
//        }
//        System.out.println("========================Param End========================");

        String wholeParams = collectAllParams(request);
        Map<String, String> paramMap = parseParams(wholeParams);
        if(paramMap.size() > 0) {
            headerMap = paramMap;
        }

//        System.out.println("========================wholeParams========================");
//        System.out.println(wholeParams);

        System.out.println("========================paramMap========================");
        System.out.println(JSON.toJSONString(paramMap));

        return JSON.toJSONString(Result.ok("更新成功"));
    }

    private Map<String, String> parseParams(String wholeParams) {
        TreeMap<String, Integer> indexMap = Maps.newTreeMap();
        Map<String, String> paramMap = Maps.newHashMap();
        for(RequestHeaderEnum headerEnum : RequestHeaderEnum.values()) {
            int index = wholeParams.indexOf(headerEnum.getSpecialName());
            if(index >= 0) {
                indexMap.put(headerEnum.getName(), index);
            }
        }
        if(indexMap.size() > 0) {
            //这里将map.entrySet()转换成list
            List<Map.Entry<String, Integer>> list = new ArrayList<Map.Entry<String,Integer>>(indexMap.entrySet());
            //然后通过比较器来实现排序
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                //降序排序
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    return o2.getValue() - o1.getValue();
                }

            });

            for(Map.Entry<String, Integer> mapping : list) {
                String value = wholeParams.substring(mapping.getValue() + mapping.getKey().length() + 1);
                paramMap.put(mapping.getKey(), value);
                wholeParams = wholeParams.substring(0, mapping.getValue());
            }
        }
        return paramMap;
    }

    private String collectAllParams(HttpServletRequest request) {
        Enumeration<String> paramNames = request.getParameterNames();
        StringBuilder sb = new StringBuilder();
        while(paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            sb.append(name).append("=").append(request.getParameter(name));
        }
        return sb.toString();
    }

//    @RequestMapping(value = "/crawler/base/id/{range}")
////    @PostMapping(value = "/crawler/base/id")
//    public String crawlerBaseInfoById(HttpServletRequest req) {
//        Integer start = Integer.parseInt(req.getParameterMap().get("start")[0]);
//        Integer end = Integer.parseInt(req.getParameterMap().get("end")[0]);
//        if(start >= end) {
//            return JSON.toJSONString(Result.error("参数错误"));
//        }
//        try {
//            Request.Builder requestBuilder = MyOkhttpHelper.createRequestBuilder(null, headerMap, false, null);
//            for(int i=start; i<end; i++) {
//                String url = "https://maimai.cn/api/ent/talent/basic?channel=www&to_uid=" + start + "&version=1.0.0";
//                requestBuilder.url(url);
//                String responseStr = MyOkhttpHelper.getResponseBody(requestBuilder.build(), true);
//                MmCrawlerInfo mmInfo = MmCrawlerInfo.createInstance(i+"", responseStr);
//                mmCrawlerMapper.insert(mmInfo);
//            }
//        } catch (Exception e) {
//            return JSON.toJSONString(Result.error(e.getMessage()));
//        }
//
//        return JSON.toJSONString(Result.ok("更新成功"));
//    }

    @GetMapping(value = "/crawler/base/{startStr}/{endStr}")
    public String crawlerBaseInfoById(@PathVariable("startStr") String startStr, @PathVariable("endStr") String endStr) {
        Integer start = Integer.parseInt(startStr);
        Integer end = Integer.parseInt(endStr);
        if(start >= end) {
            return JSON.toJSONString(Result.error("参数错误"));
        }
        try {
            Request.Builder requestBuilder = MyOkhttpHelper.createRequestBuilder(null, headerMap, false, null);
            for(int i=start; i<=end; i++) {
                String url = "https://maimai.cn/api/ent/talent/basic?channel=www&to_uid=" + i + "&version=1.0.0";
                requestBuilder.url(url);
                String responseStr = MyOkhttpHelper.getResponseBody(requestBuilder.build(), true, true);
                MmCrawlerInfo mmInfo = MmCrawlerInfo.createInstance(i+"", responseStr);
                mmCrawlerMapper.insert(mmInfo);
            }
        } catch (Exception e) {
            return JSON.toJSONString(Result.error(e.getMessage()));
        }

        return JSON.toJSONString(Result.ok(startStr + "-----" + endStr));
    }

}
