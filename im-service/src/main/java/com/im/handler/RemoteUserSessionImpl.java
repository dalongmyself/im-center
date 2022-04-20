package com.im.handler;

import com.im.common.constants.RedisConstants;
import com.im.netty.constant.ChannelAttrConstants;
import com.im.netty.session.UserSession;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author dalong
 * @date 2022/4/18 20:39
 */
@Slf4j
@Component
public class RemoteUserSessionImpl implements UserSession {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void register(Channel channel) {
        String uid = lookup(channel);
        if (StringUtils.isNotBlank(uid)) {
            String sessionKey = RedisConstants.getRedisUserSessionRemoteKey(uid);
            String sessionValue = channelId(channel);
            //暂时使用String kV结构存储，当channel下线后移除
            stringRedisTemplate.opsForValue().set(sessionKey, sessionValue, RedisConstants.DEFAULT_REDIS_USER_SESSION_KEY_EXPIRED, TimeUnit.DAYS);
        }
    }

    @Override
    public void unRegister(String uid) {
        if (StringUtils.isNotBlank(uid)) {
            String sessionKey = RedisConstants.getRedisUserSessionRemoteKey(uid);
            stringRedisTemplate.delete(sessionKey);
        }
    }

    public String lookup(Channel channel) {
        return channel.attr(ChannelAttrConstants.UID).get();
    }

    private String channelId(Channel channel) {
        return channel.id().asLongText();
    }

    public long getRequestTimestamp(Channel channel) {
        return Long.valueOf(channel.attr(ChannelAttrConstants.TIMESTAMP).get());
    }

    /**
     * 通过UID 获取 channelID
     *
     * @param uid
     * @return
     */
    public String getChannelId(String uid) {
        String sessionKey = RedisConstants.getRedisUserSessionRemoteKey(uid);
        return stringRedisTemplate.opsForValue().get(sessionKey);
    }
}
