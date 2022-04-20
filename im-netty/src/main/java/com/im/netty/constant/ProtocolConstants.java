package com.im.netty.constant;

/**
 * @author dalong
 * @date 2022/4/12 20:59
 */
public interface ProtocolConstants {

    /**
     * 消息头大小
     */
    int HEADER_TOTAL_LEN = 18;
    /**
     * 消息头最大数
     */
    int HEADER_READ_MAX_LEN = 4096;
    /**
     * 魔数
     */
    short MAGIC = 0x10;

    byte VERSION = 0x1;

    byte STATUS = 0x1;

    /**
     * webSocket aggregator maxContentLength
     */
    int AGGREGATOR_MAX_CONTENT_LENGTH = 65536;

    /**
     * webSocket serverProtocol maxFrameSize
     */
    int SERVER_PROTOCOL_MAX_FRAME_SIZE = 65536 * 10;


    int EXPLICIT_FLUSH_AFTER_FLUSHES = 10;

    String IP_ADDRESS_FILTER_RULE_ACCEPT = "111.207.194.209";

    int IP_ADDRESS_FILTER_RULE_ACCEPT_CIDR_PREFIX = 24;

    String IP_ADDRESS_FILTER__RULE_REJECT = "127.1.1.1";

    int IP_ADDRESS_FILTER_RULE_REJECT_CIDR_PREFIX = 16;
}
