package com.im.netty.protocol;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dalong
 * @date 2022/4/18 16:29
 * <p>
 * webSocket 消息体
 */
@Data
public class WebSocketMessageBody implements Serializable {
    private static final long serialVersionUID = -9206969066340855870L;

    /**
     * 场景
     */
    private Integer type;

    /**
     * 场景对应的数据
     */
    private String data;
}
