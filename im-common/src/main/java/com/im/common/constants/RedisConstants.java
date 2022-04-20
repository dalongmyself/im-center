package com.im.common.constants;

/**
 * @author dalong
 * @date 2021/12/29 6:15 下午
 */
public class RedisConstants {

    /**
     * 默认用户远程缓存路由失效时间 （1天）
     */
    public static final int DEFAULT_REDIS_USER_SESSION_KEY_EXPIRED = 1;


    private static final String REDIS_ADD_USER_BASE_INFO_KEY = "im_add_user_base_info:";

    public static final String getRedisAddUserBaseInfoKey(String uid) {
        return REDIS_ADD_USER_BASE_INFO_KEY + uid;
    }


    private static final String REDIS_USER_SESSION_REMOTE_KEY = "im_R_S_R_key:";

    public static final String getRedisUserSessionRemoteKey(String uid) {
        return REDIS_USER_SESSION_REMOTE_KEY + uid;
    }
}
