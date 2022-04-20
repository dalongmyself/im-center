package com.im.netty.protocol;

import com.google.common.collect.Maps;
import com.im.netty.protocol.impl.BindMessageConverterService;

import java.util.Map;

/**
 * @author dalong
 * @date 2022/4/16 23:59
 * <p>
 * 消息处理转换策略工厂
 */
public class MessageTypeStrategyFactory {

    private MessageTypeStrategyFactory() {
    }

    private static class MessageTypeStrategyFactoryHandler {
        private static MessageTypeStrategyFactory instance = new MessageTypeStrategyFactory();
    }

    public static MessageTypeStrategyFactory getInstance() {
        return MessageTypeStrategyFactoryHandler.instance;
    }

    private static final Map<Integer, MessageTypeService> MESSAGE_TYPE_GROUP = Maps.newConcurrentMap();

    static {
        MESSAGE_TYPE_GROUP.put(MessageTypeEnum.BIND.getCode(), new BindMessageConverterService());
    }

    public MessageTypeService messageConverter(int type) {
        return MESSAGE_TYPE_GROUP.get(type);
    }
}
