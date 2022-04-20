package com.im.component.message;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author dalong
 * @date 2022/4/18 20:55
 *
 * 发布/订阅/redis
 */
@Data
@ToString(callSuper = true)
public class ClientBroadcastMessage implements Serializable {
    private static final long serialVersionUID = 7994611135733377557L;

    private String uid;

    private Long timestamp;
}
