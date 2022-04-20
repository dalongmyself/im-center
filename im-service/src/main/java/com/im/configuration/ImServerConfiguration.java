package com.im.configuration;

import com.google.common.collect.Maps;
import com.im.component.annotation.ImBizHandler;
import com.im.configuration.properties.ImProperties;
import com.im.netty.handler.ImServerBizHandler;
import com.im.netty.handler.IpFilterRuleHandler;
import com.im.netty.protocol.ImRequestContext;
import com.im.netty.protocol.MessageTypeEnum;
import com.im.netty.server.ImSocketServer;
import com.im.netty.session.LocalUserSessionImpl;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationContextEvent;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author dalong
 * @date 2022/4/12 18:18
 */
@Slf4j
@Configuration
public class ImServerConfiguration implements ImServerBizHandler, ApplicationListener<ApplicationContextEvent> {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<String, ImServerBizHandler> handlerMap = Maps.newConcurrentMap();

    @Bean(destroyMethod = "destroy")
    public ImSocketServer setImSocketServer(ImProperties properties) {
        return new ImSocketServer.Builder()
                .setAppPort(properties.getSocketPort())
                .setWebPort(properties.getWebSocketPort())
                .setImServerBizHandler(this)
                .build();
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent applicationContextEvent) {
        Map<String, ImServerBizHandler> beans = applicationContext.getBeansOfType(ImServerBizHandler.class);
        for (Map.Entry<String, ImServerBizHandler> entry : beans.entrySet()) {
            ImServerBizHandler handler = entry.getValue();
            ImBizHandler annotation = handler.getClass().getAnnotation(ImBizHandler.class);
            if (null != annotation) {
                handlerMap.put(annotation.key(), handler);
            }
        }
        applicationContext.getBean(ImSocketServer.class).bind();
    }

    @Override
    public void process(Channel channel, ImRequestContext requestContext) {
        log.info("[im-center][serverConfig] handler process..");
        if (null == requestContext.getBody()) {
            return;
        }
        ImServerBizHandler handler = handlerMap.get(MessageTypeEnum.findByType(requestContext.getMessageType()).getKey());
        if (null == handler) {
            return;
        }
        handler.process(channel, requestContext);
    }

    @Bean
    public LocalUserSessionImpl userSession() {
        return new LocalUserSessionImpl();
    }

    @Bean
    public IpFilterRuleHandler ipFilterRuleHandler() {
        return new IpFilterRuleHandler();
    }
}
