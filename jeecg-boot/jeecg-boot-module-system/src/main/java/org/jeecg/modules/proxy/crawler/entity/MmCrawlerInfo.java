package org.jeecg.modules.proxy.crawler.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MmCrawlerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;

    /**
     * mm外键ID
     */
    private String mmid;
    
    /**
          * 基本信息
     */
    private String basicInfo;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private MmCrawlerInfo() {
        this.createTime = new Date();
        this.updateTime = new Date();
    }

    public static MmCrawlerInfo createInstance(String mmid, String basicInfo) {
        MmCrawlerInfo crawlerInfo = new MmCrawlerInfo();
        crawlerInfo.setMmid(mmid);
        crawlerInfo.setBasicInfo(basicInfo);
        return crawlerInfo;
    }

}
