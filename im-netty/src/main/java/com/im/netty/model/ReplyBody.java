package com.im.netty.model;

import com.im.netty.model.proto.ReplyBodyProto;
import com.im.netty.protocol.MessageBody;
import com.im.netty.protocol.MessageTypeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author dalong
 * @date 2022/4/18 14:36
 * <p>
 * 回复消息
 */
@Data
public class ReplyBody extends MessageBody implements Serializable, Transportable {
    private static final long serialVersionUID = -579570020009871427L;

    private String uid;

    private Integer code;

    private String message;

    private Long timestamp;

    @Override
    public byte[] getBody() {
        ReplyBodyProto.ReplyModel.Builder builder = ReplyBodyProto.ReplyModel.newBuilder();
        builder.setCode(code);
        if (StringUtils.isNotBlank(message)) {
            builder.setMessage(message);
        }
        if (StringUtils.isNotBlank(uid)) {
            builder.setUid(uid);
        }
        builder.setTimestamp(timestamp);
        return builder.build().toByteArray();
    }

    @Override
    public int getType() {
        return MessageTypeEnum.RESPONSE.getCode();
    }
}
