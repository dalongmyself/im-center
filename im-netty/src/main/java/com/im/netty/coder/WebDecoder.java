package com.im.netty.coder;

import com.alibaba.fastjson.JSON;
import com.im.netty.protocol.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author dalong
 * @date 2022/4/12 21:02
 * <p>
 * webSocket协议，采用字符串方式，解码
 */
@Slf4j
public class WebDecoder extends MessageToMessageDecoder<WebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, WebSocketFrame frame, List<Object> list) throws Exception {
        if (frame instanceof TextWebSocketFrame) {
            String content = ((TextWebSocketFrame) frame).text();
            if (StringUtils.isBlank(content)) {
                return;
            }

            InetSocketAddress ipSocket = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();
            String clientIp = ipSocket.getAddress().getHostAddress();
            log.info("[im-client][webDecoder] socketAddress, ip:{}", clientIp);

            //简单处理，暂定JSON形式，同样可以适配不同的序列化协议
            WebSocketMessageBody messageBody = JSON.parseObject(content, WebSocketMessageBody.class);

            int messageType = messageBody.getType();

            MessageTypeEnum messageTypeEnum = MessageTypeEnum.findByType(messageType);
            if (null == messageTypeEnum) {
                log.error("[im-center][webDecoder] message type not EXIST.");
                return;
            }

            String messageData = messageBody.getData();
            ImRequestContext requestContext = MessageTypeStrategyFactory.getInstance().messageConverter(messageType).messageToMessage(SerializationTypeEnum.JSON.getType(), messageData);

            requestContext.setChannelType(ChannelTypeEnum.WEBSOCKET.getType());
            requestContext.setMessageType(messageType);
            list.add(requestContext);
        }

        if (frame instanceof PingWebSocketFrame) {
            log.info("[im-center][WebDecoder] ping message:{}", frame);
            channelHandlerContext.write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
    }
}
