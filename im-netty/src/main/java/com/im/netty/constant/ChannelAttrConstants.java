package com.im.netty.constant;

import io.netty.util.AttributeKey;

/**
 * @author dalong
 * @date 2022/4/18 17:54
 */
public interface ChannelAttrConstants {

    AttributeKey<String> UID = AttributeKey.valueOf("uid");

    AttributeKey<String> DEVICE_ID = AttributeKey.valueOf("deviceId");

    AttributeKey<String> CHANNEL = AttributeKey.valueOf("channel");

    AttributeKey<String> TIMESTAMP = AttributeKey.valueOf("timestamp");
}
