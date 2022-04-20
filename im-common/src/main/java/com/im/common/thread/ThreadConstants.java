package com.im.common.thread;

/**
 * @author dalong
 * @date 2022/4/12 16:31
 */
public class ThreadConstants {

    public static final String NETTY_THREAD = "im";

    public static final String BOSS_THREAD = NETTY_THREAD + "-boss";

    public static final String WORKER_THREAD = NETTY_THREAD + "-worker";

    public static final String BIZ_THREAD = NETTY_THREAD + "-biz";


    public static final Integer BOSS_THREAD_COUNT_DEFAULT = 1;

    public static final Integer WORKER_THREAD_COUNT_DEFAULT = 0;

    public static final int UN_ORDERED_THREAD_POOL_EVENT_CORE_POOL_SIZE = 10;
}
