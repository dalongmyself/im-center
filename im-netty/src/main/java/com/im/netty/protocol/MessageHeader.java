package com.im.netty.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dalong
 * @date 2022/4/12 16:22
 * <p>
 * 消息头
 */
@Data
public class MessageHeader implements Serializable {
    private static final long serialVersionUID = 7982404850819642880L;

    /**
     +---------------------------------------------------------------+
     | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
     +---------------------------------------------------------------+
     | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
     +---------------------------------------------------------------+
     */
    /**
     * 魔数
     */
    private short magic;

    /**
     * 协议版本号
     */
    private byte version;

    /**
     * 序列化算法
     */
    private byte serialization;

    /**
     * 报文类型
     */
    private byte messageType;

    /**
     * 状态
     */
    private byte status;

    /**
     * 消息ID
     */
    private long requestId;

    /**
     * 消息长度
     */
    private int messageLength;
}
