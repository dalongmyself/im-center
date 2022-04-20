package com.im.netty.coder;

import com.alibaba.fastjson.JSON;
import com.im.netty.model.ReplyBody;
import com.im.netty.model.WebSocketRespBody;
import com.im.netty.protocol.ImRequestContext;
import com.im.netty.protocol.MessageTypeEnum;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author dalong
 * @date 2022/4/12 21:03
 * <p>
 * webSocket编码
 */
@Slf4j
public class WebEncoder extends MessageToMessageEncoder<ImRequestContext> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ImRequestContext requestContext, List<Object> list) throws Exception {
        MessageTypeEnum messageTypeEnum = MessageTypeEnum.findByType(requestContext.getMessageType());

        switch (messageTypeEnum) {
            case RESPONSE:
                replayMessageEncoder(list, requestContext.getMessageType(), requestContext.getBody());
            default:
                return;
        }
    }

    /**
     * 回复
     *
     * @param list
     * @param messageType
     * @param messageBody
     */
    private void replayMessageEncoder(List<Object> list, Integer messageType, Object messageBody) {
        log.info("[im-center][WebMessageEncoder] replayMessage, messageBody:{}", JSON.toJSONString(messageBody));
        ReplyBody replyBody = (ReplyBody) messageBody;
        WebSocketRespBody respBody = new WebSocketRespBody();
        respBody.setUid(replyBody.getUid());
        respBody.setCode(replyBody.getCode());
        respBody.setMessage(replyBody.getMessage());
        respBody.setType(messageType);
        respBody.setTimestamp(respBody.getTimestamp());
        list.add(new TextWebSocketFrame(JSON.toJSONString(respBody)));
    }
}
