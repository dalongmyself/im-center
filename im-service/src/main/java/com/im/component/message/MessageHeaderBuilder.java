package com.im.component.message;

import com.im.netty.constant.ProtocolConstants;
import com.im.netty.protocol.MessageHeader;
import com.im.netty.protocol.SerializationTypeEnum;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author dalong
 * @date 2022/4/18 21:09
 */
public class MessageHeaderBuilder {

    public final static AtomicLong REQUEST_ID = new AtomicLong(0);

    public static MessageHeader build(int msgType) {
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setMagic(ProtocolConstants.MAGIC);
        messageHeader.setVersion(ProtocolConstants.VERSION);
        messageHeader.setRequestId(REQUEST_ID.incrementAndGet());
        messageHeader.setSerialization((byte) SerializationTypeEnum.PROTOBUF.getType());
        messageHeader.setMessageType((byte) msgType);
        messageHeader.setStatus(ProtocolConstants.STATUS);
        return messageHeader;
    }
}
