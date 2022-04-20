package com.im.netty.constant;

/**
 * @author dalong
 * @date 2022/4/18 18:11
 */
public class IdleConstants {

    /**
     * 设置读超时时间
     */
    public static final int READER_IDLE_TIME = 10;
    /**
     * 设置写超时时间
     */
    public static final int WRITER_IDLE_TIME = 0;
    /**
     * 同时为读或写设置超时时间
     */
    public static final int ALL_IDLE_TIME = 0;
}
