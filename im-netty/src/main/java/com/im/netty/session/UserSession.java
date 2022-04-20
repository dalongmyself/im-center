package com.im.netty.session;

import io.netty.channel.Channel;

/**
 * @author dalong
 * @date 2022/4/18 17:49
 * <p>
 * 用户session绑定
 */
public interface UserSession {

    /**
     * 注册绑定
     *
     * @param channel
     */
    void register(Channel channel);

    /**
     * 删除绑定
     *
     * @param uid
     */
    void unRegister(String uid);
}
