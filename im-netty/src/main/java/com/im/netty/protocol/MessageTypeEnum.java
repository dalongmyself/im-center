package com.im.netty.protocol;

import com.im.netty.constant.MessageConstants;
import lombok.Getter;

/**
 * @author dalong
 * @date 2022/4/14 17:39
 * <p>
 * 消息类型枚举
 */
public enum MessageTypeEnum {

    BIND(1, MessageConstants.CLIENT_BIND, "客户端绑定"),
    REBIND(2, MessageConstants.CLINT_RE_BIND, "客户端重绑"),
    CLOSE(3, MessageConstants.CLINT_CLOSE, "客户端关闭"),
    RESPONSE(4, MessageConstants.CLINT_RESPONSE, "回复"),
    PING(5, MessageConstants.CLINT_PING, "ping"),
    PONG(6, MessageConstants.CLINT_PONG, "pong"),
    ;
    @Getter
    final int code;
    @Getter
    final String key;
    @Getter
    final String desc;

    MessageTypeEnum(int code, String key, String desc) {
        this.code = code;
        this.key = key;
        this.desc = desc;
    }

    public static MessageTypeEnum findByType(int type) {
        for (MessageTypeEnum typeEnum : MessageTypeEnum.values()) {
            if (typeEnum.code == type) {
                return typeEnum;
            }
        }
        return null;
    }
}
