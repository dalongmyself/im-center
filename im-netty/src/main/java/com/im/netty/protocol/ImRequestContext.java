package com.im.netty.protocol;

import lombok.Data;

/**
 * @author dalong
 * @date 2022/4/12 16:17
 */
@Data
public class ImRequestContext<T extends MessageBody> {

    /**
     * 消息头
     */
    private MessageHeader messageHeader;

    /**
     * 通道类型
     */
    private String channelType;

    /**
     * 消息枚举
     */
    private Integer messageType;

    /**
     * 消息体
     */
    private T body;
}
