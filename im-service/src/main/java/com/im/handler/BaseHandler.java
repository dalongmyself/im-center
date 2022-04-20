package com.im.handler;

import com.im.component.message.ClientBroadcastMessage;
import com.im.component.message.MessageHeaderBuilder;
import com.im.component.message.RedisSendBroadcastHandler;
import com.im.netty.constant.ChannelAttrConstants;
import com.im.netty.model.ReplyBody;
import com.im.netty.protocol.ChannelTypeEnum;
import com.im.netty.protocol.ImRequestContext;
import com.im.netty.protocol.MessageHeader;
import com.im.netty.protocol.MessageTypeEnum;
import com.im.netty.session.LocalUserSessionImpl;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @author dalong
 * @date 2022/4/18 19:44
 */
@Slf4j
public class BaseHandler {

    @Resource
    protected LocalUserSessionImpl userSession;
    @Resource
    protected RemoteUserSessionImpl remoteUserSession;
    @Resource
    protected RedisSendBroadcastHandler redisSendBroadcastHandler;

    private final transient ChannelFutureListener remover = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture channelFuture) throws Exception {
            log.info("[im-center][ChannelFutureListener] channel close listener ..");
            channelFuture.removeListener(this);
            String uid = remoteUserSession.lookup(channelFuture.channel());
            //local
            userSession.unRegister(uid);
            //remote
            remoteUserSession.unRegister(uid);
            //本地local数据变更
            CompletableFuture.runAsync(() -> {
                //todo
            });
            //发送广播下线
            ClientBroadcastMessage broadcastMessage = new ClientBroadcastMessage();
            broadcastMessage.setUid(uid);
            broadcastMessage.setTimestamp(remoteUserSession.getRequestTimestamp(channelFuture.channel()));
            redisSendBroadcastHandler.unRegister(broadcastMessage);
        }
    };

    public void addUserSession(Channel channel) {
        //添加channel
        channel.closeFuture().addListener(this.remover);
        //本地
        userSession.register(channel);
        //远程
        remoteUserSession.register(channel);
    }

    public void replaySuccess(Channel channel, String channelType) {
        ReplyBody replyBody = new ReplyBody();
        /**
         * 基础返回信息构建
         */
        ImRequestContext<ReplyBody> requestContext = new ImRequestContext<>();
        requestContext.setMessageType(MessageTypeEnum.RESPONSE.getCode());

        if (ChannelTypeEnum.SOCKET.getType().equals(channelType)) {
            MessageHeader messageHeader = MessageHeaderBuilder.build(MessageTypeEnum.RESPONSE.getCode());
            requestContext.setMessageHeader(messageHeader);
        }

        requestContext.setBody(replyBody);

        if (channel.isActive() && channel.isWritable()) {
            channel.writeAndFlush(requestContext);
        }
    }

    public Long getChannelTimestamp(String uid) {
        Channel channel = userSession.get(uid);
        String timestampStr = channel.attr(ChannelAttrConstants.TIMESTAMP).get();
        if (StringUtils.isEmpty(timestampStr)) {
            return 0L;
        }
        return Long.valueOf(timestampStr);
    }

    public void closeChannel(Channel channel) {
        if (null != channel && channel.isActive() && channel.isOpen()) {
            channel.close();
        }
    }
}
