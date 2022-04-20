package com.im.netty.protocol;

import lombok.Getter;

/**
 * @author dalong
 * @date 2022/4/18 16:52
 * <p>
 * 序列化协议枚举
 */
public enum SerializationTypeEnum {

    PROTOBUF(0x10),
    JSON(0x20),
    ;

    @Getter
    final int type;

    SerializationTypeEnum(int type) {
        this.type = type;
    }

    public static SerializationTypeEnum findByType(byte type) {
        for (SerializationTypeEnum typeEnum : values()) {
            if (typeEnum.getType() == type) {
                return typeEnum;
            }
        }
        return JSON;
    }
}
