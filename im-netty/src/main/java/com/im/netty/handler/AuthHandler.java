package com.im.netty.handler;

import com.im.netty.model.BindBody;
import com.im.netty.protocol.ImRequestContext;
import com.im.netty.protocol.MessageBody;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dalong
 * @date 2022/4/18 18:19
 */
@Slf4j
@ChannelHandler.Sharable
public class AuthHandler extends SimpleChannelInboundHandler<ImRequestContext<MessageBody>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ImRequestContext<MessageBody> messageBodyImRequestContext) throws Exception {
        try {
            MessageBody messageBody = messageBodyImRequestContext.getBody();
            if (messageBody instanceof BindBody) {
                //TODO:业务判断处理
            }
            //TODO：授权业务处理

            channelHandlerContext.fireChannelRead(messageBodyImRequestContext);
        } finally {
            channelHandlerContext.pipeline().remove(this);
        }
    }
}
