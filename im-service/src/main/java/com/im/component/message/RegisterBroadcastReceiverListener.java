package com.im.component.message;

import com.alibaba.fastjson.JSON;
import com.im.handler.BaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author dalong
 * @date 2022/4/18 21:15
 * <p>
 * 注册绑定消息处理
 */
@Slf4j
@Component
public class RegisterBroadcastReceiverListener extends BaseHandler implements MessageListener {

    @Override
    public void onMessage(Message message, byte[] bytes) {
        log.info("[im-center][RegisterBroadcastReceiverListener] message:{}", JSON.toJSONString(message));

        try {
            ClientBroadcastMessage clientBroadcastMessage = JSON.parseObject(message.getBody(), ClientBroadcastMessage.class);
            /**
             * 1、判断当前实例是否为链接实例
             * 2、如果非链接实例，且存在此链接，则断开
             */
            String uid = clientBroadcastMessage.getUid();
            Long timestamp = super.getChannelTimestamp(uid);

            if (clientBroadcastMessage.getTimestamp().equals(timestamp)
                    && userSession.getChannelId(uid).equals(remoteUserSession.getChannelId(uid))) {
                return;
            }

            super.closeChannel(userSession.getOrDefault(uid, null));

            userSession.unRegister(uid);

        } catch (Exception e) {
            log.error("[im-center][RegisterBroadcastReceiverListener] register exception.");
        }
    }
}
