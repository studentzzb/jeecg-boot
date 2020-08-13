package org.jeecg.modules.proxy.crawler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.proxy.crawler.entity.WxCrawlerInfo;

import java.util.List;

public interface WxCrawlerMapper extends BaseMapper<WxCrawlerInfo> {

    public List<WxCrawlerInfo> queryByMd5(@Param("md5Array") String[] md5Array);

    @Select("SELECT author,title,digist,cover,content_url,create_time,md5 FROM wx_crawler_info limit #{start}, #{pageSize}")
    public List<WxCrawlerInfo> queryList(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    @Select("SELECT id,md5,content FROM wx_crawler_info  where wrapped=0 limit 0, 10")
    public List<WxCrawlerInfo> queryTransList();

    @Select("SELECT content FROM wx_crawler_info where md5=#{md5}")
    public String queryContentByMd5(@Param("md5") String md5);

    @Select("SELECT content_wrapped FROM wx_crawler_info where md5=#{md5}")
    public String queryWrappedContentByMd5(@Param("md5") String md5);

    @Select("SELECT author,title,digist,cover,content_url,create_time,md5,content FROM wx_crawler_info where md5=#{md5}")
    public WxCrawlerInfo queryByMd5(@Param("md5") String md5);

    @Select("SELECT count(*) FROM wx_crawler_info")
    public Integer queryCount();

    @Update("update wx_crawler_info set content_wrapped=#{contentWrapped}, wrapped=1 where id = #{id}")
    public int setContentWrapped(@Param("id") String id, @Param("contentWrapped") String contentWrapped);

}
