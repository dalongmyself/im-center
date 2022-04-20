package com.im.netty.protocol.impl;

import com.alibaba.fastjson.JSON;
import com.google.protobuf.InvalidProtocolBufferException;
import com.im.netty.model.BindBody;
import com.im.netty.model.proto.BindBodyProto;
import com.im.netty.protocol.ImRequestContext;
import com.im.netty.protocol.MessageTypeService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author dalong
 * @date 2022/4/17 00:05
 */
@Slf4j
public class BindMessageConverterService implements MessageTypeService {

    @Override
    public ImRequestContext byteToMessage(int serializationType, byte[] dataBytes) {
        log.info("[im-center][BindMessageConverterService] byteToMessage, serializationType:{},byte:{}", serializationType, dataBytes.toString());
        ImRequestContext requestContext = new ImRequestContext<>();

        /**
         * 根据不同的序列化协议，进行对象转换，这里默认使用了 protoBuf，如果使用了其它协议，进行转换转换可以了
         */
        try {
            BindBodyProto.BindModel bindModel = BindBodyProto.BindModel.parseFrom(dataBytes);
            if (null == bindModel) {
                log.error("[im-center][byteToMessage] serialType:{},bindModel is null");
                return requestContext;
            }
            BindBody bindBody = new BindBody();
            bindBody.setUid(bindModel.getUid());
            bindBody.setDeviceId(bindModel.getDeviceId());
            bindBody.setClientType(bindModel.getClientType());
            bindBody.setAppVersion(bindModel.getAppVersion());
            bindBody.setTimestamp(bindModel.getTimestamp());

            requestContext.setBody(bindBody);

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
        return requestContext;
    }

    @Override
    public ImRequestContext messageToMessage(int serializationType, String value) {
        log.info("[im-center][BindMessageConverterService] messageToMessage, serializationType:{},byte:{}", serializationType, value);
        /**
         * 这里因为业务场景，只是简单的StringToString,没有使用具体序列化协议，webSocket通信
         *
         * 同样预留：根据序列化类型，根据不同序列化协议转换
         */
        ImRequestContext requestContext = new ImRequestContext();
        BindBody bindBody = JSON.parseObject(value, BindBody.class);
        requestContext.setBody(bindBody);
        return requestContext;
    }
}
