/**
 * Copyright 2021 json.cn
 */
package com.wtt.agribusiness.auth.vo;

import lombok.Data;

/**
 * Auto-generated: 2021-03-18 14:26:57
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class SocialUser {

    private String access_token;
    private String remind_in;
    private long expires_in;
    private String uid;
    private String isRealName;

}