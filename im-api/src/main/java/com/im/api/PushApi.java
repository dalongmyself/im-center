package com.im.api;

import com.im.api.Result.PushResult;
import com.im.api.request.PushRequest;
import com.im.common.bean.Response;

/**
 * @author dalong
 * @date 2022/4/20 18:46
 */
public interface PushApi {

    /**
     * 业务推送
     *
     * @param request
     * @return
     */
    Response<PushResult> push(PushRequest request);
}
