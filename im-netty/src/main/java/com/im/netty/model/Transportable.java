package com.im.netty.model;

/**
 * @author dalong
 * @date 2022/4/18 14:38
 */
public interface Transportable {

    /**
     * 获取body体
     *
     * @return
     */
    byte[] getBody();

    /**
     * 获取类型
     *
     * @return
     */
    int getType();
}
