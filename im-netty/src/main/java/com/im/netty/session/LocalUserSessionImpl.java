package com.im.netty.session;

import com.im.netty.constant.ChannelAttrConstants;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dalong
 * @date 2022/4/18 17:52
 */
@Slf4j
public class LocalUserSessionImpl extends ConcurrentHashMap<String, Channel> implements UserSession {

    public LocalUserSessionImpl() {
    }

    @Override
    public void register(Channel channel) {
        String uid = this.getKey(channel);
        if (StringUtils.isNotBlank(uid) && channel.isActive()) {
            this.putIfAbsent(uid, channel);
        }
    }

    @Override
    public void unRegister(String uid) {
        if (StringUtils.isNotBlank(uid)) {
            this.remove(uid);
        }
    }

    /**
     * 移除 channel key
     *
     * @param channel
     */
    private void remove(Channel channel) {
        String uid = this.getKey(channel);
        if (StringUtils.isNotBlank(uid)) {
            this.remove(uid);
        }
    }

    /**
     * 获取Channel Key
     *
     * @param channel
     * @return
     */
    private String getKey(Channel channel) {
        return channel.attr(ChannelAttrConstants.UID).get();
    }


    /**
     * 通道ID
     *
     * @param uid
     * @return
     */
    public String getChannelId(String uid) {
        Channel channel = get(uid);
        return channel.id().asLongText();
    }
}
