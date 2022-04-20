package com.im.api.request;

import com.im.common.bean.Request;
import lombok.Data;

/**
 * @author dalong
 * @date 2022/4/20 18:47
 */
@Data
public class PushRequest extends Request {

    /**
     * 用户UID
     */
    private String uid;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 消息类型
     */
    private Integer messageType;

    /**
     * 时间戳
     */
    private Long timestamps;
}
