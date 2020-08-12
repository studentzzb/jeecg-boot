package org.jeecg.modules.proxy.crawler.controller;

import lombok.extern.slf4j.Slf4j;
import org.jeecg.modules.proxy.crawler.mapper.WxCrawlerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/detail")
@Slf4j
public class WxDetailController {

    @Autowired
    private WxCrawlerMapper wxCrawlerMapper;

    // 返回页面详情
    @GetMapping(value = "/{md5}.html")
    public String getCrawlerArticleList(@PathVariable("md5") String md5, HttpServletResponse response) {
        String content = wxCrawlerMapper.queryContentByMd5(md5);
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
    }

}
