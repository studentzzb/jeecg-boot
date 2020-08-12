package org.jeecg;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class TestMain {

  private static String wxJson = "{\n" +
          "    \"ret\":0,\n" +
          "    \"errmsg\":\"ok\",\n" +
          "    \"msg_count\":10,\n" +
          "    \"can_msg_continue\":1,\n" +
          "    \"general_msg_list\":\"{\\\"list\\\":[{\\\"comm_msg_info\\\":{\\\"id\\\":1000001120,\\\"type\\\":49,\\\"datetime\\\":1595466000,\\\"fakeid\\\":\\\"2398274380\\\",\\\"status\\\":2,\\\"content\\\":\\\"\\\"},\\\"app_msg_ext_info\\\":{\\\"title\\\":\\\"猪八戒网：如何实现跨越式增长？\\\",\\\"digest\\\":\\\"打造一个“有生意的社区”。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":0,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595845&amp;idx=1&amp;sn=708bc99674fd01c832c5f9f0aba4d9b2&amp;chksm=bd35b0668a4239705ccbdef55b475630c82ba829de809617b8594b8617ac705c13264b10db28&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmljiaFv5lUXMTet7PcTqialOhQVnQItPKzX33udKjZM5N26xe6ibdMsfEYLJicaIZvE3HjibUcRDV6BfvA\\/0?wx_fmt=jpeg\\\",\\\"subtype\\\":9,\\\"is_multi\\\":1,\\\"multi_app_msg_item_list\\\":[{\\\"title\\\":\\\"那些化解冲突的高手，都有这5个好习惯！\\\",\\\"digest\\\":\\\"一个人的能力变强，从掌握关键对话开始。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":0,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595845&amp;idx=2&amp;sn=b1f367b60bfc64e53a6a43db68419d43&amp;chksm=bd35b0668a4239704cb9e30f22fece4bb42b7b307783a06a5557a789cb269d0c4965ff25bf5f&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"https:\\/\\/mp.weixin.qq.com\\/s\\/0o0xb_kHSm6-DAL3opMIHA\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmljiaFv5lUXMTet7PcTqialOhCoVneuJN4z2JF07foMtecx77UGONXEZnHrw7UNaPmM1nzJ3PNSJVeA\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"张力\\\",\\\"copyright_stat\\\":101,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}],\\\"author\\\":\\\"周文辉 何奇松\\\",\\\"copyright_stat\\\":11,\\\"duration\\\":0,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}},{\\\"comm_msg_info\\\":{\\\"id\\\":1000001119,\\\"type\\\":49,\\\"datetime\\\":1595379600,\\\"fakeid\\\":\\\"2398274380\\\",\\\"status\\\":2,\\\"content\\\":\\\"\\\"},\\\"app_msg_ext_info\\\":{\\\"title\\\":\\\"3个标准，帮你建立世界上最好的商业模式\\\",\\\"digest\\\":\\\"如题。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":0,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595792&amp;idx=1&amp;sn=6fdeceeeb8294c273d963f9424cc832f&amp;chksm=bd35b0338a423925b8095c2321c3627713af5636a3df28abdccd98e63393730b93e29f0301a6&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"https:\\/\\/mp.weixin.qq.com\\/s\\/9lt8-H-Z4nIELguU3ZIxIQ\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmmoHkyxXzf9nWODZ3jribRv8ObqTuQsibrCCblUU2XoKQhRWDGZTUO4FDXAiaJxEmUGdO922lRM0K98g\\/0?wx_fmt=jpeg\\\",\\\"subtype\\\":9,\\\"is_multi\\\":1,\\\"multi_app_msg_item_list\\\":[{\\\"title\\\":\\\"乘风破浪的印度裔CEO，这五大领导力特质可以学习\\\",\\\"digest\\\":\\\"如何在过度约束的条件下，仍可持续创造成功？\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":0,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595792&amp;idx=2&amp;sn=bffffe8cdbad25e68390cbe91011ec4d&amp;chksm=bd35b0338a4239259d99a228e85f7ad2c4cb1fe1728bcf50f179e6734a372ec2cadbd0656d8f&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"https:\\/\\/mp.weixin.qq.com\\/s\\/9lt8-H-Z4nIELguU3ZIxIQ\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmmoHkyxXzf9nWODZ3jribRv8tqe1JiaficOyF98JZrkg0uw6TJnItn62Dibzb8iaj2OicOf91cNAULUA9Pg\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"泰普洛领导力\\\",\\\"copyright_stat\\\":101,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0},{\\\"title\\\":\\\"阿里式销售铁军，你也能拥有！\\\",\\\"digest\\\":\\\"阿里式铁军锻造2.0，7月30日-8月1日，杭州等你！\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504109807,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595792&amp;idx=3&amp;sn=b41ee79058528a796cd5f0a5a4c5f2b6&amp;chksm=bd35b0338a423925b98389871de0ad269cc565ae084be973eac08a9f9e23df857f42c49050ae&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"https:\\/\\/www.wjx.top\\/m\\/78292544.aspx\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmkicMj4FvdibSuQJQhLtX5SqBXuhyPOTYnlEofPq7l9fNM1jhA9tLySm4VC57sUh9SSfHsDmdEcuvDg\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"新商业学院\\\",\\\"copyright_stat\\\":100,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}],\\\"author\\\":\\\"刘润\\\",\\\"copyright_stat\\\":101,\\\"duration\\\":0,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}},{\\\"comm_msg_info\\\":{\\\"id\\\":1000001117,\\\"type\\\":49,\\\"datetime\\\":1595293200,\\\"fakeid\\\":\\\"2398274380\\\",\\\"status\\\":2,\\\"content\\\":\\\"\\\"},\\\"app_msg_ext_info\\\":{\\\"title\\\":\\\"营收利润双双大跌的优衣库，离超越Zara和H&amp;M反而更近了\\\",\\\"digest\\\":\\\"优衣库手里握着两张关键底牌\u200B。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504112072,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595759&amp;idx=1&amp;sn=3749be53d7a8221e8bbc657c10fed5d0&amp;chksm=bd35b7cc8a423eda2dd6081492ef7c122b720fa6bd46457e62b187d23d5529bf0270899331d8&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595018&amp;idx=3&amp;sn=c836fd51a87a8dcd5974bc189f3826c1&amp;chksm=bd35b5298a423c3fdbff4e2f93e0bf89479d531f469089233c24892e209997fc20e4a36059aa&amp;scene=21#wechat_redirect\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmkvW1AGCo2wEQicKseC3e6VH1aDiaRlEh4KxYQDoYdp1XMeZY0dE4OmcXZMrEOIbEhON5OTnZCgwLSQ\\/0?wx_fmt=jpeg\\\",\\\"subtype\\\":9,\\\"is_multi\\\":1,\\\"multi_app_msg_item_list\\\":[{\\\"title\\\":\\\"没有“底线”的公司，最为致命\\\",\\\"digest\\\":\\\"瑞幸并没有读懂星巴克的增长设计。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":0,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595759&amp;idx=2&amp;sn=8db40922d5ce682a38b69fd5bb1bffb5&amp;chksm=bd35b7cc8a423eda9f7a3905447fabc6171b4edd179ffc78f8edad163687dd46da7a60ee4ee3&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"https:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595692&amp;idx=3&amp;sn=6d035a4a53cb0738b077b9f91e9d6b10&amp;chksm=bd35b78f8a423e9962b77f547a14adc69c84f549ebf6dc7a5a19d41cb7db02667abf054fdeac&amp;token=1327910986&amp;lang=zh_CN#rd\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmkvW1AGCo2wEQicKseC3e6VHicI0bicZf5dfOawicVib5aXKkhcicNofNmKsJUXd34Seu9aXcGmq2zDnw3A\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"混沌大学\\\",\\\"copyright_stat\\\":101,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}],\\\"author\\\":\\\"鸿键\\\",\\\"copyright_stat\\\":101,\\\"duration\\\":0,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}},{\\\"comm_msg_info\\\":{\\\"id\\\":1000001116,\\\"type\\\":49,\\\"datetime\\\":1595206800,\\\"fakeid\\\":\\\"2398274380\\\",\\\"status\\\":2,\\\"content\\\":\\\"\\\"},\\\"app_msg_ext_info\\\":{\\\"title\\\":\\\"销售业绩不好，可能是你对人性还没研究透\\\",\\\"digest\\\":\\\"打一场有准备之仗。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504111953,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595692&amp;idx=1&amp;sn=47ee70d85afdc30acc85d80df4fc4025&amp;chksm=bd35b78f8a423e99a776c3c5b633080ff69779bd3402720912b856ba42ab2e501611196ea7e0&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595018&amp;idx=3&amp;sn=c836fd51a87a8dcd5974bc189f3826c1&amp;chksm=bd35b5298a423c3fdbff4e2f93e0bf89479d531f469089233c24892e209997fc20e4a36059aa&amp;scene=21#wechat_redirect\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmnz2YniayOQoznpGhgmLIlaYWpmicgtAgCwS1f0UCKZwjs2Pk4eVl2KnMrFr9uHMd8D1Iwg7XTWumKQ\\/0?wx_fmt=jpeg\\\",\\\"subtype\\\":9,\\\"is_multi\\\":1,\\\"multi_app_msg_item_list\\\":[{\\\"title\\\":\\\"产品主义，没有“价值感”也白搭\\\",\\\"digest\\\":\\\"人们买的不是东西，而是他们的期望。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504111991,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595692&amp;idx=2&amp;sn=d3a7d9245ceb6730cc35ca12a3c80818&amp;chksm=bd35b78f8a423e996ce587439cd5a5df26273efe4f77cfcd774c4d13952cc903fd6f774b7006&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"https:\\/\\/wx66f637399a936e6a.h5.xiaoe-tech.com\\/v1\\/course\\/alive\\/l_5f111b1de4b04349896c86ff?app_id=app5zVr78mc3046&amp;channel_id=274135&amp;is_redirect=1&amp;type=2\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmnz2YniayOQoznpGhgmLIlaYILYZWZBY0XwyEicJzUaXlbYdc843dCrUPUxvYwoTmBOebwawkxW2wOg\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"刘馨忆\\\",\\\"copyright_stat\\\":101,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0},{\\\"title\\\":\\\"《总裁进化三板斧》定制版，限量招生，欲报从速！\\\",\\\"digest\\\":\\\"阿里味儿总裁训练营第五期，2020年8月13日-8月15日杭州等你，欢迎报名咨询。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504109616,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595692&amp;idx=3&amp;sn=6d035a4a53cb0738b077b9f91e9d6b10&amp;chksm=bd35b78f8a423e9962b77f547a14adc69c84f549ebf6dc7a5a19d41cb7db02667abf054fdeac&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"https:\\/\\/www.wjx.top\\/jq\\/81249138.aspx\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmmRzwPntYbic0QGy4ibUpJvq7tTggRib166nsUHHU7F3Ybic5YUls6f8yXzHJm7bmR8R0vIj6mp5xTibZw\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"新商业学院\\\",\\\"copyright_stat\\\":100,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}],\\\"author\\\":\\\"李立恒\\\",\\\"copyright_stat\\\":11,\\\"duration\\\":0,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}},{\\\"comm_msg_info\\\":{\\\"id\\\":1000001115,\\\"type\\\":49,\\\"datetime\\\":1594947600,\\\"fakeid\\\":\\\"2398274380\\\",\\\"status\\\":2,\\\"content\\\":\\\"\\\"},\\\"app_msg_ext_info\\\":{\\\"title\\\":\\\"创作者死于视频\\\",\\\"digest\\\":\\\"B站和西瓜别争了，中国没有Youtube。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":0,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595580&amp;idx=1&amp;sn=2769e32244bd76fb45632e3453d674fd&amp;chksm=bd35b71f8a423e09fb95ba20590a09a1da4599d62e24d1c0664d97c8ad2a60709426fbec8971&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmmzDxV9a8vE1oyO5OTIaLicjPgYmYzBib3o6fajLS9qzelNaCB2kB4mpMSGl8WLGoibSll8CZ08y5Ekw\\/0?wx_fmt=jpeg\\\",\\\"subtype\\\":9,\\\"is_multi\\\":1,\\\"multi_app_msg_item_list\\\":[{\\\"title\\\":\\\"生意增长乏力？阿里给你秘密武器\\\",\\\"digest\\\":\\\"业绩暴增的秘密。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504110363,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595580&amp;idx=2&amp;sn=9741df14fcfa6c0fed6c83f59c7439a3&amp;chksm=bd35b71f8a423e09efbc156a608198dc658c7b03ad1a18dca565ff19749809e2f3478ea05033&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmmzDxV9a8vE1oyO5OTIaLicjeibvIIVkCE4f44arCY8ScMFuicVQ1VNBHp0SDk0mhtVEFibhGEBCoF4vQ\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"新商业学院\\\",\\\"copyright_stat\\\":100,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}],\\\"author\\\":\\\"程杰\\\",\\\"copyright_stat\\\":101,\\\"duration\\\":0,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}},{\\\"comm_msg_info\\\":{\\\"id\\\":1000001114,\\\"type\\\":49,\\\"datetime\\\":1594884562,\\\"fakeid\\\":\\\"2398274380\\\",\\\"status\\\":2,\\\"content\\\":\\\"\\\"},\\\"app_msg_ext_info\\\":{\\\"title\\\":\\\"方太创始人茅理翔：破解家族企业传承的十大难题\\\",\\\"digest\\\":\\\"家族企业的大考验时代到来！\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504111787,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595495&amp;idx=1&amp;sn=379257156cfaf8ea9185e991d86096b8&amp;chksm=bd35b6c48a423fd2c3a6516c2632a80c2e1f5ebb48a53c7683f180a2918cff4617a883c1f58e&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmmNNma3GyB63atVnTptA3KoBYNXjiaK4tRlISib6Dd6y9aeMFSL9sqicyD7LnkwqpUxAMUgv0ww7vztA\\/0?wx_fmt=jpeg\\\",\\\"subtype\\\":9,\\\"is_multi\\\":1,\\\"multi_app_msg_item_list\\\":[{\\\"title\\\":\\\"阿里式销售铁军，你也能拥有！\\\",\\\"digest\\\":\\\"阿里式铁军锻造2.0，7月30日-8月1日，杭州等你！\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504109807,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595495&amp;idx=3&amp;sn=ae67ee9646f486d0482f015b93529726&amp;chksm=bd35b6c48a423fd25948f0f19ff83fd5ee79521638df23df441ab06da4cbe8a67441a9cf3237&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"https:\\/\\/www.wjx.top\\/m\\/78292544.aspx\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmkicMj4FvdibSuQJQhLtX5SqBXuhyPOTYnlEofPq7l9fNM1jhA9tLySm4VC57sUh9SSfHsDmdEcuvDg\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"新商业学院\\\",\\\"copyright_stat\\\":100,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}],\\\"author\\\":\\\"茅理翔\\\",\\\"copyright_stat\\\":11,\\\"duration\\\":0,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}},{\\\"comm_msg_info\\\":{\\\"id\\\":1000001113,\\\"type\\\":49,\\\"datetime\\\":1594774800,\\\"fakeid\\\":\\\"2398274380\\\",\\\"status\\\":2,\\\"content\\\":\\\"\\\"},\\\"app_msg_ext_info\\\":{\\\"title\\\":\\\"股市有毒？5个大坑你需要规避\\\",\\\"digest\\\":\\\"\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":0,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595425&amp;idx=1&amp;sn=99766428c0985b482749a408a62d6278&amp;chksm=bd35b6828a423f9405660ff338674a3fbba6b2e8fb2720cface2672bfeb547a2a37c297183a6&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmn5ddmfqDeSpLlQSccX8icgJwuR9Bzib6565EJxbGanibPGogIHNic0QtjcVjicFAFv7zO6VJhQ8QWIicXQ\\/0?wx_fmt=jpeg\\\",\\\"subtype\\\":9,\\\"is_multi\\\":1,\\\"multi_app_msg_item_list\\\":[{\\\"title\\\":\\\"抖音做社交，难上加难\\\",\\\"digest\\\":\\\"谁的技术、团队、资本过硬，谁就能跑出来。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":0,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595425&amp;idx=2&amp;sn=9f99ab0cc3af05b7dae510bba00fe62d&amp;chksm=bd35b6828a423f9415956f0a51acfd8638803ea02ff72eb315e3a86bd96158698f1a875603e6&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmn5ddmfqDeSpLlQSccX8icgJBmvgEBprhE2ovvNpFtesdavKic7Y8VdDscIib1J0xgEPLAk8BUQWvsew\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"杨硕\\\",\\\"copyright_stat\\\":101,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0},{\\\"title\\\":\\\"后疫情时代，企业增长的秘密\\\",\\\"digest\\\":\\\"淘宝大学联合商业评论，一起赋能企业增长新动力！\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504111765,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595425&amp;idx=3&amp;sn=a6e0920ca3344542c338163401b4af80&amp;chksm=bd35b6828a423f94ae603f825e3c6fc9a49a312fe835f193f909e80aeed0cb049acfd3f2eb18&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmn5ddmfqDeSpLlQSccX8icgJARlKepmg92yLrUOH8T9LT392k3fXhdWE4PzkFQIuZoFMtncsY0CdvA\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"新商业学院\\\",\\\"copyright_stat\\\":100,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}],\\\"author\\\":\\\"\\\",\\\"copyright_stat\\\":100,\\\"duration\\\":0,\\\"del_flag\\\":1,\\\"item_show_type\\\":5,\\\"vid\\\":\\\"wxv_1428075898863190017\\\",\\\"ckey\\\":\\\"3QcQ0Nvne_xHRQVpc61ZVJCwrSG1eN0hlB3wSao2PquntD9PUmet3ZD-jcUG2KMSrGhD1rkVDzaiOdt7jAPSlIBxkN1xiqtAzTuwnkpFxQg=\\\",\\\"audio_fileid\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}},{\\\"comm_msg_info\\\":{\\\"id\\\":1000001112,\\\"type\\\":49,\\\"datetime\\\":1594688400,\\\"fakeid\\\":\\\"2398274380\\\",\\\"status\\\":2,\\\"content\\\":\\\"\\\"},\\\"app_msg_ext_info\\\":{\\\"title\\\":\\\"华为，无非两种命运\\\",\\\"digest\\\":\\\"能打败华为的，只能是华为自己。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504111729,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595380&amp;idx=1&amp;sn=12a043d29822679751c6bbd07072b4da&amp;chksm=bd35b6578a423f4163ab321faac2dc70f4219c7d0f27e5f1d0e6ec6ed78406068267432c8bb6&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmnBV6I1TB2RyNVDBN9YCeGGDQu7dsAqicNoIv9jexB6XvmwiaZ0Ph0qAoqF4drgHylicOStFdKkwQTUw\\/0?wx_fmt=jpeg\\\",\\\"subtype\\\":9,\\\"is_multi\\\":1,\\\"multi_app_msg_item_list\\\":[{\\\"title\\\":\\\"工作中那些“迷之自信”都是哪儿来的？\\\",\\\"digest\\\":\\\"善于自我说服的人，却永远说服不了别人。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504111715,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595380&amp;idx=2&amp;sn=bf9262f21a36e70001e29ac66f049c19&amp;chksm=bd35b6578a423f419575023e51cac7b6b2be39c94a494907f8bd477d806ffe7b277073ade0f4&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595018&amp;idx=3&amp;sn=c836fd51a87a8dcd5974bc189f3826c1&amp;chksm=bd35b5298a423c3fdbff4e2f93e0bf89479d531f469089233c24892e209997fc20e4a36059aa&amp;scene=21#wechat_redirect\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmnBV6I1TB2RyNVDBN9YCeGGfvaXfBO488SsTAHRfvslmScdTvTzDicTFpYHBJkk6hH7pMCTlUE2P2A\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"蔺雷\\\",\\\"copyright_stat\\\":101,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0},{\\\"title\\\":\\\"阿里持续增长的秘诀是……\\\",\\\"digest\\\":\\\"《向阿里学管理》第十四期，7月25-26日杭州开班，学员招募中！\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504111477,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595380&amp;idx=3&amp;sn=2a0af8e7af802c812b607579be3bf286&amp;chksm=bd35b6578a423f41e1c74cabffb5e1c2016265a85ff201b9482c5b916bce74894d4e210526b0&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"https:\\/\\/www.wjx.top\\/jq\\/84375697.aspx\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmmXleDUF4rQHxzaQibTwAzRQu7SErpH5TZUGLsIqxInRrq4pvbW2JoqR0iabDnWgFBib3wEuOX7xIIrw\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"新商业学院\\\",\\\"copyright_stat\\\":100,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}],\\\"author\\\":\\\"田涛\\\",\\\"copyright_stat\\\":101,\\\"duration\\\":0,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}},{\\\"comm_msg_info\\\":{\\\"id\\\":1000001111,\\\"type\\\":49,\\\"datetime\\\":1594602000,\\\"fakeid\\\":\\\"2398274380\\\",\\\"status\\\":2,\\\"content\\\":\\\"\\\"},\\\"app_msg_ext_info\\\":{\\\"title\\\":\\\"疫情中陷入困境的企业，如何东山再起？\\\",\\\"digest\\\":\\\"在紧缩和复苏之间找到平衡之路。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504111657,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595343&amp;idx=1&amp;sn=f6ce6d706b9169d1b68966c152a7d25d&amp;chksm=bd35b66c8a423f7ad909320c3094705a1137e666b608630b653c40e4ef3a694b2bfaa1d3fe2e&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmnXEloQqpYFvy1EQJ8LWhrtdvGic3HW3ia521NMjfwSp8KaQdOPAhsvicxAWQw5a5NCwbOUO6jcJkcZA\\/0?wx_fmt=jpeg\\\",\\\"subtype\\\":9,\\\"is_multi\\\":1,\\\"multi_app_msg_item_list\\\":[{\\\"title\\\":\\\"爱奇艺、优酷有救了\\\",\\\"digest\\\":\\\"救命稻草是什么？\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":0,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595343&amp;idx=2&amp;sn=b13d00c2ca2c13627be8dbecb3a3292a&amp;chksm=bd35b66c8a423f7a68454ce88e1ebcd36818eca7ddec5f9ba99417dde946f8b0bed04cd1a442&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595018&amp;idx=3&amp;sn=c836fd51a87a8dcd5974bc189f3826c1&amp;chksm=bd35b5298a423c3fdbff4e2f93e0bf89479d531f469089233c24892e209997fc20e4a36059aa&amp;scene=21#wechat_redirect\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmlwDBhd8gmHXZbLv657uTtLxib7MwWtQCicTkYfdh3ermn3W1sDJUQgrrtKOV2jFJ5uW9ibrpxFxPpQw\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"章蔚玮\\\",\\\"copyright_stat\\\":101,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}],\\\"author\\\":\\\"英格玛·赫曼\\\",\\\"copyright_stat\\\":11,\\\"duration\\\":0,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}},{\\\"comm_msg_info\\\":{\\\"id\\\":1000001110,\\\"type\\\":49,\\\"datetime\\\":1594342800,\\\"fakeid\\\":\\\"2398274380\\\",\\\"status\\\":2,\\\"content\\\":\\\"\\\"},\\\"app_msg_ext_info\\\":{\\\"title\\\":\\\"美团最好战的那个男人要走了\\\",\\\"digest\\\":\\\"“老王”终究还是要离开美团了。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504111638,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595303&amp;idx=1&amp;sn=57a316649636c48ae5dd39b4c2956de5&amp;chksm=bd35b6048a423f1238ed8ef3bcf48149bbe3687ae9892c8f7a6359da3cd50704b90372c2c06a&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmmougUjFyV850sGueMY6f6fkRSu8FUZ7BYEqVcsXCbueiaU5jjWAtkkbJjaalk0YT7RUtcyrHTxXDw\\/0?wx_fmt=jpeg\\\",\\\"subtype\\\":9,\\\"is_multi\\\":1,\\\"multi_app_msg_item_list\\\":[{\\\"title\\\":\\\"如何判断一个管理者称不称职？\\\",\\\"digest\\\":\\\"管理大师明茨伯格的7个评估准则。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":0,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595303&amp;idx=2&amp;sn=ba6dfa54234795b145b0c2219b5c979d&amp;chksm=bd35b6048a423f12f8fe8aed5e0ab4726b63a68b9abcd10295b87b1ccf3da139c6cfa3ed4ed3&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595018&amp;idx=3&amp;sn=c836fd51a87a8dcd5974bc189f3826c1&amp;chksm=bd35b5298a423c3fdbff4e2f93e0bf89479d531f469089233c24892e209997fc20e4a36059aa&amp;scene=21#wechat_redirect\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmmougUjFyV850sGueMY6f6fEKEbBKv7q4gxLnS2V1TtX2ONQTia0rx3R20jq21GEBgpRABnMKQatOg\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"亨利·明茨伯格\\\",\\\"copyright_stat\\\":101,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0},{\\\"title\\\":\\\"数字经济时代的未来，你要如何把握？\\\",\\\"digest\\\":\\\"向阿里学数字化转型。\\\",\\\"content\\\":\\\"\\\",\\\"fileid\\\":504110612,\\\"content_url\\\":\\\"http:\\/\\/mp.weixin.qq.com\\/s?__biz=MjM5ODI3NDM4MA==&amp;mid=2651595303&amp;idx=3&amp;sn=999fe394035caa24e47b544635bbd98a&amp;chksm=bd35b6048a423f12d6cb7b57f5acb1aba4d846e616fa47225286ab26f57e255fb40fd49e3e26&amp;scene=27#wechat_redirect\\\",\\\"source_url\\\":\\\"https:\\/\\/www.wjx.top\\/jq\\/81838463.aspx\\\",\\\"cover\\\":\\\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_jpg\\/YaibMVf3bGmklKTLHic0cboysX8vuc3IKibRc5GFvvmLKoknrBjFQY15wib3UQXHEvasjViapyA6z14A8JibeRvX5SrA\\/0?wx_fmt=jpeg\\\",\\\"author\\\":\\\"新商业学院\\\",\\\"copyright_stat\\\":100,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"duration\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}],\\\"author\\\":\\\"陈桥辉\\\",\\\"copyright_stat\\\":101,\\\"duration\\\":0,\\\"del_flag\\\":1,\\\"item_show_type\\\":0,\\\"audio_fileid\\\":0,\\\"play_url\\\":\\\"\\\",\\\"malicious_title_reason_id\\\":0,\\\"malicious_content_type\\\":0}}]}\",\n" +
          "    \"next_offset\":10,\n" +
          "    \"video_count\":1,\n" +
          "    \"use_video_tab\":1,\n" +
          "    \"real_type\":0,\n" +
          "    \"home_page_list\":[\n" +
          "    ]\n" +
          "}\n";

  public static void main(String[] args) throws UnknownHostException {
      try{
        String _url = "http://mmbiz.qpic.cn/mmbiz_jpg/8cu01Kavc5bRLQyAPBz5PC0bC8xmZtUD0608ZjjWtBdVnNQpzPP4IxjicVgd0KWS5mpQTHREeyRZ8VNzTZLzmmA/0?wx_fmt=jpeg";
        String path = "/Users/jerry.zhou/temp/test/8cu01Kavc5bRLQyAPBz5PC0bC8xmZtUD0608ZjjWtBdVnNQpzPP4IxjicVgd0KWS5mpQTHREeyRZ8VNzTZLzmmA";
        URL url = new URL(_url);
        URLConnection con = url.openConnection();
        con.setConnectTimeout(5000);
        InputStream is = con.getInputStream();
        byte[] bs = new byte[1024];
        int len;
        File sf=new File(path);
        OutputStream os = new FileOutputStream(sf);
        while ((len = is.read(bs)) != -1) {
          os.write(bs, 0, len);
        }
        os.close();
        is.close();

      } catch (IOException e) {
      }
  }

//  public static void main(String[] args) throws UnknownHostException {
//    JSONObject object = JSON.parseObject(wxJson);
//    Integer canMsgContinue = object.getInteger("can_msg_continue");
//    JSONObject msglist = object.getJSONObject("general_msg_list");
//    JSONArray array = msglist.getJSONArray("list");
//    for(int i=0; i<array.size(); i++) {
//      JSONObject msgInfo = array.getJSONObject(i).getJSONObject("app_msg_ext_info");
//      String author = msgInfo.getString("author");
//      String title = msgInfo.getString("title");
//      String cover = msgInfo.getString("cover");
//      String digist = msgInfo.getString("digist");
//      String contentUrl = msgInfo.getString("content_url");
//
//      if(msgInfo.getInteger("is_multi") > 0) {
//        JSONArray multiMsgList = msgInfo.getJSONArray("multi_app_msg_item_list");
//        System.out.println(msgInfo);
//      }
//      System.out.println(msgInfo);
//    }
//    System.out.println(object);
//  }

}