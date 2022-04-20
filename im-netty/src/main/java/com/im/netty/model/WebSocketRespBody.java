package com.im.netty.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author dalong
 * @date 2022/4/18 17:03
 */
@Data
public class WebSocketRespBody implements Serializable {
    private static final long serialVersionUID = -7841800942811201475L;

    /**
     * UID
     */
    private String uid;

    /**
     * MESSAGE
     */
    private String message;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 时间戳
     */
    private Long timestamp;
}
