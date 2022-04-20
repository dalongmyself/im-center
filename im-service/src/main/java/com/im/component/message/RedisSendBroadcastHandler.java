package com.im.component.message;

import com.alibaba.fastjson.JSON;
import com.im.common.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author dalong
 * @date 2022/4/18 20:58
 */
@Slf4j
@Component
public class RedisSendBroadcastHandler {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 注册
     *
     * @param clientBroadcastMessage
     */
    public void register(ClientBroadcastMessage clientBroadcastMessage) {
        log.info("[im-center] Redis Publish & subscribe，bind.. bindMessage:{}", JSON.toJSONString(clientBroadcastMessage));
        try {
            stringRedisTemplate.convertAndSend(CommonConstants.BIND_MESSAGE_REDIS_TOPIC, JSON.toJSONString(clientBroadcastMessage));
        } catch (Exception e) {
            log.error("[im-center] Redis Publish & subscribe，bind.. bind exception. e,", e);
        }
    }

    /**
     * 解绑
     *
     * @param clientBroadcastMessage
     */
    public void unRegister(ClientBroadcastMessage clientBroadcastMessage) {
        log.info("[im-center] Redis Publish & subscribe，unBind.. unBindMessage:{}", clientBroadcastMessage);
        try {
            stringRedisTemplate.convertAndSend(CommonConstants.UNBIND_MESSAGE_REDIS_TOPIC, JSON.toJSONString(clientBroadcastMessage));
        } catch (Exception e) {
            log.error("[im-center] Redis Publish & subscribe，unBind.. unBind exception. e,", e);
        }
    }
}
