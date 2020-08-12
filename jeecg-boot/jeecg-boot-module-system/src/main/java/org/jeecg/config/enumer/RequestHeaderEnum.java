package org.jeecg.config.enumer;

/**
 * @author zhouzhibin
 * @date 2020/7/20
 * @time 4:25 下午
 */
public enum RequestHeaderEnum {

    // 请求域名头部分
    REQUEST_HEADER_ENUM_AUTHORITY(":authority"),
    REQUEST_HEADER_ENUM_METHOD(":method"),
    REQUEST_HEADER_ENUM_PATH(":path"),
    REQUEST_HEADER_ENUM_SCHEME(":scheme"),

    // 其它请求头部分
    REQUEST_HEADER_ENUM_ACCEPT("accept"),
    REQUEST_HEADER_ENUM_ACCEPT_ENCODING("accept-encoding"),
    REQUEST_HEADER_ENUM_ACCEPT_LANGUAGE("accept-language"),
    REQUEST_HEADER_ENUM_CACHE_CONTROL("cache-control"),
    REQUEST_HEADER_ENUM_COOKIE("cookie"),
    REQUEST_HEADER_ENUM_SEC_FETCH_MODE("sec-fetch-mode"),
    REQUEST_HEADER_ENUM_SEC_FETCH_SITE("sec-fetch-site"),
    REQUEST_HEADER_ENUM_SEC_FETCH_USER("sec-fetch-user"),
    REQUEST_HEADER_ENUM_UPGRADE_INSECURE_REQUESTS("upgrade-insecure-requests"),
    REQUEST_HEADER_ENUM_USER_AGENT("user-agent"),
    REQUEST_HEADER_ENUM_REFERER("referer"),
    REQUEST_HEADER_ENUM_X_CSRF_TOKEN("x-csrf-token"),
    REQUEST_HEADER_ENUM_ORIGIN("origin"),


    ;

    private String name;

    private RequestHeaderEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getSpecialName() {
        // 名字后面带冒号属性，避免key跟文本重复，保持唯一区分
        return name + ":";
    }

    public String getSimpleName() {
        // 冒号开头的属性，去掉冒号
        if(name.startsWith(":")) {
            return name.substring(1);
        }
        return name;
    }

}
