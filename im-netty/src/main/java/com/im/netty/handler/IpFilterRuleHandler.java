package com.im.netty.handler;

import io.netty.handler.ipfilter.IpFilterRule;
import io.netty.handler.ipfilter.IpFilterRuleType;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author dalong
 * @date 2022/4/20 16:53
 *
 * IP规则过滤处理
 */
@Slf4j
public class IpFilterRuleHandler implements IpFilterRule {

    @Override
    public boolean matches(InetSocketAddress inetSocketAddress) {

        //客户端IP
        String ip = inetSocketAddress.getHostString();
        /**
         * 黑名单处理
         * 对于白名单，可以采用配置文件或者REDIS来实现
         */
        /**
         * 返回True 表示执行过滤器
         * 返回False 表示不执行过滤器
         */

        
        return false;
    }

    @Override
    public IpFilterRuleType ruleType() {
        /**
         * 返回拒绝:则表示拒绝链接
         */
        return IpFilterRuleType.REJECT;
    }
}
