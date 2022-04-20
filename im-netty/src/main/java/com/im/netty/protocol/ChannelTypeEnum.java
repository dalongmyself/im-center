package com.im.netty.protocol;

import lombok.Getter;

/**
 * @author dalong
 * @date 2022/4/18 14:10
 */
public enum ChannelTypeEnum {

    SOCKET("socket", "socket方式"),
    WEBSOCKET("webSocket", "webSocket方式"),
    ;

    @Getter
    final String type;
    @Getter
    final String desc;

    ChannelTypeEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
