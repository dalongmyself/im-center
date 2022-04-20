package com.im.netty.coder;

import com.alibaba.fastjson.JSON;
import com.im.netty.model.ReplyBody;
import com.im.netty.protocol.ImRequestContext;
import com.im.netty.protocol.MessageHeader;
import com.im.netty.protocol.MessageTypeEnum;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dalong
 * @date 2022/4/12 21:01
 */
@Slf4j
public class AppEncoder extends MessageToByteEncoder<ImRequestContext> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ImRequestContext requestContext, ByteBuf byteBuf) throws Exception {
        MessageHeader messageHeader = requestContext.getMessageHeader();
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.findByType(messageHeader.getMessageType());
        switch (messageTypeEnum) {
            case RESPONSE:
                replayMessageEncoder(byteBuf, messageHeader, messageHeader);
                break;
            default:
                return;
        }
    }

    /**
     * 回复消息编码
     *
     * @param byteBuf
     * @param messageHeader
     * @param messageBody
     */
    private void replayMessageEncoder(ByteBuf byteBuf, MessageHeader messageHeader, Object messageBody) {
        log.info("[im-center][appMessageEncoder]repayMessage messageBody:{}", JSON.toJSONString(messageBody));
        ReplyBody replyBody = (ReplyBody) messageBody;
        commonHeaderSend(byteBuf, messageHeader);
        byte[] bodyData = replyBody.getBody();
        byteBuf.writeInt(bodyData.length);
        byteBuf.writeBytes(bodyData);
    }

    /**
     * 写入协议头信息
     *
     * @param byteBuf
     * @param messageHeader
     */
    private void commonHeaderSend(ByteBuf byteBuf, MessageHeader messageHeader) {
        byteBuf.writeShort(messageHeader.getMagic());
        byteBuf.writeByte(messageHeader.getVersion());
        byteBuf.writeByte(messageHeader.getSerialization());
        byteBuf.writeByte(messageHeader.getMessageType());
        byteBuf.writeByte(messageHeader.getStatus());
        byteBuf.writeLong(messageHeader.getRequestId());
    }
}
