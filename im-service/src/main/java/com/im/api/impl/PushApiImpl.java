package com.im.api.impl;

import com.alibaba.fastjson.JSON;
import com.im.api.PushApi;
import com.im.api.Result.PushResult;
import com.im.api.request.PushRequest;
import com.im.common.bean.Response;
import com.im.netty.session.LocalUserSessionImpl;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import javax.annotation.Resource;

/**
 * @author dalong
 * @date 2022/4/20 19:02
 */
@Slf4j
@Service
public class PushApiImpl implements PushApi {

    @Resource
    private LocalUserSessionImpl localUserSession;

    @Override
    public Response<PushResult> push(PushRequest request) {
        log.info("[im-center] biz push .req:{}", JSON.toJSONString(request));
        String uid = request.getUid();
        Channel channel = localUserSession.get(uid);
        /**
         * TODO: 业务处理..
         */
        return null;
    }
}
