package org.jeecg.modules.proxy.crawler.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.MD5Util;
import org.jeecg.common.util.PicDownloadUtil;
import org.jeecg.config.enumer.RequestHeaderEnum;
import org.jeecg.config.util.MyOkhttpHelper;
import org.jeecg.modules.proxy.crawler.entity.WxCrawlerInfo;
import org.jeecg.modules.proxy.crawler.mapper.WxCrawlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@RestController
@RequestMapping("/wx")
@Slf4j
public class WxBrowserController {

    private static Map<String, String> headerMap = Maps.newHashMap();
    private static final String PARAM_OFFSET            = "offset";
    private static final String WX_HOST                 = "https://mp.weixin.qq.com";

    // 最多拉取1w页
    private static final Integer MAX_PAGE = 10000;

    @Autowired
    private WxCrawlerMapper wxCrawlerMapper;

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
        // path 参数必须放在最后解析
        Enumeration<String> paramNames = request.getParameterNames();
        StringBuilder sb = new StringBuilder();
        while(paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            // path后边的参数全部加上地址符
            if(sb.toString().contains(RequestHeaderEnum.REQUEST_HEADER_ENUM_PATH.getName())) {
                sb.append("&");
            }
            sb.append(name).append("=").append(request.getParameter(name));
        }
        return sb.toString();
    }

    @GetMapping(value = "/crawler/{author}/page/{startStr}/{endStr}")
    public String crawlerByPage(@PathVariable("author") String author, @PathVariable("startStr") String startStr, @PathVariable("endStr") String endStr) {
        Integer startPage = Integer.parseInt(startStr);
        Integer endPage = 0;
        if(StringUtils.isEmpty(endStr)) {
            endPage = MAX_PAGE;
        } else {
            endPage = Integer.parseInt(endStr);
        }
        if(startPage >= endPage) {
            return JSON.toJSONString(Result.error("参数错误"));
        }
        if(!headerMap.containsKey(RequestHeaderEnum.REQUEST_HEADER_ENUM_PATH.getName())) {
            return JSON.toJSONString(Result.error("path参数为空，请先设置request header"));
        }
        if(!headerMap.get(RequestHeaderEnum.REQUEST_HEADER_ENUM_PATH.getName()).contains(PARAM_OFFSET)) {
            return JSON.toJSONString(Result.error("path中无offset参数"));
        }
        try {
            Request.Builder requestBuilder = MyOkhttpHelper.createRequestBuilder(null, headerMap, false, null);
            requestBuilder.addHeader("Accept-Encoding", "gzip");
            boolean isLastPage = false;
            String path = rebuildPath(headerMap.get(RequestHeaderEnum.REQUEST_HEADER_ENUM_PATH.getName()));
            for(int i=startPage; i<endPage && !isLastPage; i++) {
                requestBuilder.url(WX_HOST + path + "&" + PARAM_OFFSET + "=" + i*10);
                String responseStr = MyOkhttpHelper.getResponseBody(requestBuilder.build(), true, true);

                // 最后一页
                Integer canMsgContinue = JSON.parseObject(responseStr).getInteger("can_msg_continue");
                if(canMsgContinue != 1) {
                    isLastPage = true;
                }

                // 重复记录不插入
                List<WxCrawlerInfo> wxCrawlerInfoList = parseResponseInfo(responseStr, author);
                if(wxCrawlerInfoList.isEmpty()) {
                    System.out.println("Empty wxCrawlerInfoList!");
                    continue;
                }
                String[] md5Array = new String[wxCrawlerInfoList.size()];
                for(int j=0; j<wxCrawlerInfoList.size(); j++) {
                    md5Array[j] = wxCrawlerInfoList.get(j).getMd5();
                }
                List<WxCrawlerInfo> existCrawlerInfoList = wxCrawlerMapper.queryByMd5(md5Array);

                // 筛选出不重复的记录
                List<String> existMd5List = Lists.newArrayList();
                for(WxCrawlerInfo crawlerInfo : existCrawlerInfoList) {
                    existMd5List.add(crawlerInfo.getMd5());
                }
                // 爬取详情
                for(WxCrawlerInfo crawlerInfo : wxCrawlerInfoList) {
                    if(existMd5List.contains(crawlerInfo.getMd5()) || StringUtils.isEmpty(crawlerInfo.getContentUrl())) {
                        continue;
                    }
                    // 下载预览图片
                    PicDownloadUtil.downloadPic(crawlerInfo.getCover());
                    // 爬取详情
                    requestBuilder.url(crawlerInfo.getContentUrl().replace("http", "https"));
                    String articleResponse = MyOkhttpHelper.getResponseBody(requestBuilder.build(), true, false);
                    crawlerInfo.setContent(articleResponse);
                    wxCrawlerMapper.insert(crawlerInfo);
                }

                log.info("author -> " + author + ", i -> " + i + ", wxBaseInfoList size -> " + wxCrawlerInfoList.size());
//                System.out.println("wxBaseInfoList->" + JSON.toJSONString(wxCrawlerInfoList));
            }
        } catch (Exception e) {
            return JSON.toJSONString(Result.error(e.getMessage()));
        }

        return JSON.toJSONString(Result.ok(startStr + "-----" + endStr));
    }

//    private void downloadCrawlerCoverPic(String urlStr) {
//        if(StringUtils.isEmpty(urlStr) || !urlStr.contains("http")) {
//            return;
//        }
//        String md5 = MD5Util.MD5Encode(urlStr, "utf8");
//        String fileName = md5 + ".jpg";
//        try{
//            URL url = new URL(urlStr);
//            URLConnection con = url.openConnection();
//            con.setConnectTimeout(5000);
//            InputStream is = con.getInputStream();
//            byte[] bs = new byte[1024];
//            int len;
//            File sf=new File(WX_PIC_PATH_DIR + fileName);
//            OutputStream os = new FileOutputStream(sf);
//            while ((len = is.read(bs)) != -1) {
//                os.write(bs, 0, len);
//            }
//            os.close();
//            is.close();
//
//        } catch (IOException e) {
//            log.error("WxBrowserController downloadCrawlerCoverPic error -> " + e.getMessage(), e);
//        }
//    }

    private List<WxCrawlerInfo> parseResponseInfo(String responseStr, String defaultAuthor) {
        JSONObject object = JSON.parseObject(responseStr);
        JSONObject msglist = object.getJSONObject("general_msg_list");
        JSONArray array = msglist.getJSONArray("list");
        List<WxCrawlerInfo> wxCrawlerInfoList = Lists.newArrayList();
        for(int i=0; i<array.size(); i++) {
            JSONObject msgInfo = array.getJSONObject(i).getJSONObject("app_msg_ext_info");
            wxCrawlerInfoList.add(parseJson(msgInfo, defaultAuthor));

            if(msgInfo.getInteger("is_multi") > 0) {
                JSONArray multiMsgList = msgInfo.getJSONArray("multi_app_msg_item_list");
                for(int j=0; j<multiMsgList.size(); j++) {
                    wxCrawlerInfoList.add(parseJson(multiMsgList.getJSONObject(j), defaultAuthor));
                }
            }
        }
        return wxCrawlerInfoList;
    }

    private WxCrawlerInfo parseJson(JSONObject msgInfo, String defaultAuthor) {
        WxCrawlerInfo wxCrawlerInfo = new WxCrawlerInfo();
        if(!StringUtils.isEmpty(msgInfo.getString("author"))) {
            wxCrawlerInfo.setAuthor(msgInfo.getString("author"));
        } else {
            wxCrawlerInfo.setAuthor(defaultAuthor);
        }
        wxCrawlerInfo.setTitle(msgInfo.getString("title"));
        wxCrawlerInfo.setContentUrl(msgInfo.getString("content_url"));
        wxCrawlerInfo.setCover(msgInfo.getString("cover"));
        wxCrawlerInfo.setDigist(msgInfo.getString("digist"));
        wxCrawlerInfo.setMd5(MD5Util.MD5Encode((wxCrawlerInfo.getAuthor() + "-" + wxCrawlerInfo.getTitle()), "utf8"));
        return wxCrawlerInfo;
    }

    private String rebuildPath(String originPath) {
        // 摘掉offset参数
        int index1 = originPath.indexOf(PARAM_OFFSET);
        String header = originPath.substring(0, index1);
        String tail = originPath.substring(index1);
        int index2 = tail.indexOf("&");
        tail = tail.substring(index2 + 1);
        return header + tail;
    }

    // 按页返回显示内容
    @GetMapping(value = "/crawler/list/{page}/{pageSize}")
    public String getCrawlerArticleList(@PathVariable("page") String pageStr, @PathVariable("pageSize") String pageSizeStr) {
        // page从第一页开始，无第0页
        Integer page = Integer.parseInt(pageStr);
        Integer pageSize = Integer.parseInt(pageSizeStr);
        if(StringUtils.isEmpty(pageStr) || StringUtils.isEmpty(pageSizeStr)) {
            return JSON.toJSONString(Result.error("参数错误"));
        }
        List<WxCrawlerInfo> crawlerInfoList = wxCrawlerMapper.queryList((page-1) * pageSize, pageSize);
        Integer count = wxCrawlerMapper.queryCount();
        Map<String, Object> mapResult = Maps.newHashMap();
        mapResult.put("articles", crawlerInfoList);
        mapResult.put("count", count);
        return JSON.toJSONString(Result.ok(mapResult));
    }

}
