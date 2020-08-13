package org.jeecg.modules.proxy.crawler.controller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.PicDownloadUtil;
import org.jeecg.modules.proxy.crawler.entity.WxCrawlerInfo;
import org.jeecg.modules.proxy.crawler.mapper.WxCrawlerMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/detail")
@Slf4j
public class WxDetailController {

    @Autowired
    private WxCrawlerMapper wxCrawlerMapper;

    // 返回页面详情
    @GetMapping(value = "/{md5}.html")
    public String getCrawlerArticleList(@PathVariable("md5") String md5) {
        String content = wxCrawlerMapper.queryWrappedContentByMd5(md5);
        if(StringUtils.isEmpty(content)) {
            content = wxCrawlerMapper.queryContentByMd5(md5);
        }
//        response.setContentType("text/html;charset=utf-8");
//        PrintWriter writer;
//        try {
//            writer = response.getWriter();
//        } catch (IOException e) {
//            writer = new PrintWriter(System.out);
//            log.error("IO异常:", e);
//        }
//
//        writer.println(content);
//        writer.close();
        return content;
//        return replacePartContent(Jsoup.parse(content));
    }

    // 返回页面详情
    @GetMapping(value = "/trans")
    public String articleDetailTrans() {
        List<WxCrawlerInfo> transList = wxCrawlerMapper.queryTransList();
        if(transList != null && transList.size()>0) {
            for(WxCrawlerInfo wxCrawlerInfo : transList) {
                String contentWrapped = replacePartContent(Jsoup.parse(wxCrawlerInfo.getContent()));
                wxCrawlerMapper.setContentWrapped(wxCrawlerInfo.getId(), contentWrapped);
            }
        }
        return JSON.toJSONString(Result.ok("trans success recode count -> " + transList.size()));
    }

    private String replacePartContent(Document document) {
        document = replacePicFile(document);
        return document.html();
    }

    private Document replacePicFile(Document document) {
        Elements elements = document.getElementsByTag("img");
        for(int i=0; i<elements.size(); i++) {
            Element img = elements.get(i);
            String picUrl = img.attr("data-src");
            if(StringUtils.isEmpty(picUrl)) {
                continue;
            }
            String wrappedPicUrl = PicDownloadUtil.downloadPic(picUrl);
            if(!StringUtils.isEmpty(wrappedPicUrl)) {
                img.attr("data-src", wrappedPicUrl);
            }
        }
        return document;
    }

}
