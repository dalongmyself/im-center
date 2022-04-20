package com.im.netty.constant;

/**
 * @author dalong
 * @date 2022/4/14 18:44
 * <p>
 * 消息类型常量
 */
public interface MessageConstants {

    String CLIENT_BIND = "client_bind";

    String CLINT_RE_BIND = "client_reBind";

    String CLINT_CLOSE = "client_close";

    String CLINT_RESPONSE = "client_response";

    String CLINT_PING = "client_ping";

    String CLINT_PONG = "client_pong";
}
