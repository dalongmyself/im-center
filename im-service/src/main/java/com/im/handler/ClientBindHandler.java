package com.im.handler;

import com.alibaba.fastjson.JSON;
import com.im.component.annotation.ImBizHandler;
import com.im.component.message.ClientBroadcastMessage;
import com.im.netty.constant.MessageConstants;
import com.im.netty.handler.ImServerBizHandler;
import com.im.netty.model.BindBody;
import com.im.netty.protocol.ImRequestContext;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dalong
 * @date 2022/4/18 19:40
 */
@Slf4j
@ImBizHandler(key = MessageConstants.CLIENT_BIND)
public class ClientBindHandler extends BaseHandler implements ImServerBizHandler {

    @Override
    public void process(Channel channel, ImRequestContext requestContext) {
        log.info("[im-center][ClientBindHandler] content:{}", JSON.toJSONString(requestContext));
        BindBody bindBody = (BindBody) requestContext.getBody();
        /**
         * 业务处理
         * .....
         *
         */
        //存储用户信息
        addUserSession(channel);

        //发送绑定订阅消息（防止其它节点多次绑定，申请下线）
        ClientBroadcastMessage broadcastMessage = new ClientBroadcastMessage();
        broadcastMessage.setUid(bindBody.getUid());
        broadcastMessage.setTimestamp(bindBody.getTimestamp());
        redisSendBroadcastHandler.register(broadcastMessage);

        //回复客户端
        super.replaySuccess(channel, requestContext.getChannelType());

    }
}
