package com.im.netty.coder;

import com.im.netty.constant.ProtocolConstants;
import com.im.netty.protocol.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author dalong
 * @date 2022/4/12 20:55
 * <p>
 * 服务端接收消息路由解码 SOCKET
 */
@Slf4j
public class AppDecoder extends ByteToMessageDecoder {

    /**
     * +---------------------------------------------------------------+
     * | 魔数 2byte | 协议版本号 1byte | 序列化算法 1byte | 报文类型 1byte  |
     * +---------------------------------------------------------------+
     * | 状态 1byte |        消息 ID 8byte     |      数据长度 4byte     |
     * +---------------------------------------------------------------+
     * |                   数据内容 （长度不定）                          |
     * +---------------------------------------------------------------+
     */

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

        //Header 消息体不足3位，发生断包情况
        if (byteBuf.readableBytes() < ProtocolConstants.HEADER_TOTAL_LEN) {
            return;
        }

        //防止socket字节流攻击、客户端传来的数据过大，这里需要对数据进行过滤掉
        if (byteBuf.readableBytes() >= ProtocolConstants.HEADER_READ_MAX_LEN) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            return;
        }

        //标记开头
        byteBuf.markReaderIndex();

        //魔数
        short magic = byteBuf.readShort();
        if (magic != ProtocolConstants.MAGIC) {
            throw new IllegalArgumentException("magic is illegal," + magic);
        }

        //版本
        byte version = byteBuf.readByte();
        //序列化类型
        byte serializeType = byteBuf.readByte();
        //消息类型
        byte msgType = byteBuf.readByte();
        //状态
        byte status = byteBuf.readByte();
        //请求ID
        long requestId = byteBuf.readLong();
        //消息长度
        int dataLen = byteBuf.readInt();

        //字节流长度小于消息长度，重置
        if (byteBuf.readableBytes() < dataLen) {
            byteBuf.resetReaderIndex();
            return;
        }

        byte[] dataBytes = new byte[dataLen];
        byteBuf.readBytes(dataBytes);

        MessageHeader header = new MessageHeader();
        header.setMagic(magic);
        header.setVersion(version);
        header.setSerialization(serializeType);
        header.setStatus(status);
        header.setRequestId(requestId);
        header.setMessageType(msgType);
        header.setMessageLength(dataLen);

        MessageTypeEnum typeEnum = MessageTypeEnum.findByType(msgType);
        if (null != typeEnum) {
            log.error("[im-center][appDecoder]typeEnum is null.");
            return;
        }

        ImRequestContext requestContext = MessageTypeStrategyFactory.getInstance().messageConverter(msgType).byteToMessage(serializeType, dataBytes);
        requestContext.setMessageHeader(header);
        requestContext.setChannelType(ChannelTypeEnum.SOCKET.getType());
        requestContext.setMessageType(typeEnum.getCode());
        list.add(requestContext);
    }
}
