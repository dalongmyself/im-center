/**
 * @author dalong
 * @date 2022/4/12 16:05
 */
package com.im.netty;

/**
 * 1、Netty 基于IP的黑名单过滤
 *
 *   RuleBasedIpFilter是基于ip的过滤器，可以自定义哪些ip或者ip范围允许通过或者被阻止。
 *   因为默认情况下，如果不添加任何IpSubnetFilterRule, RuleBasedIpFilter就会允许所有ip。
 *   如果我们使用白名单机制，要么继承RuleBasedIpFilter类，重写accept方法，要不在RuleBasedIpFilter， 添加一个rejectAll，并将该rejectAll放在数组的最后。
 *
 *   RuleBasedIpFilter是AbstractRemoteAddressFilter的子类，而AbstractRemoteAddressFilter是ChannelInboundHandlerAdapter的子类。
 *   它重载了channelRegistered、channelActive等方法。
 *
 *   调用链：
 *   channelRegistered()——>handleNewChannel()——>accept()——>IpFilterRule.matches()；
 *   channelActive()——>handleNewChannel()——>accept()——>IpFilterRule.matches()；
 *
 *   channelRegistered()和channelActive()会调用handleNewChannel()
 *
 *   handleNewChannel()代码中调用accept()方法，accept方法就是RuleBasedIpFilter类中方法。
 *
 *   关于：cidrPrefix
 *   用 CIDR 的方式表示一个 IP，比如：192.168.31.100/24，其中 24 就是 Netty 中的 cidrPrefix
 *   理解cidr就需要理解子网掩码 ，这边导入知乎大神的讲解如何理解子网掩码：https://www.zhihu.com/question/56895036
 *
 *
 *2、流量整形简介
 * AbstractTrafficShapingHandler的
 *
 *  GlobalTrafficShapingHandler、ChannelTrafficShapingHandler、GlobalChannelTrafficShapingHandler，其中ChannelTrafficShapingHandler最好理解，
 *  它是对单个通道进行限制；而 GlobalTrafficShapingHandler 是全局流量整形，也就是说它限制了全局的带宽，无论开启了几个channel。
 *  但Global 并不是对本地所有的channel 的总流量进行限流，而是仅仅maxGlobalWriteSize GlobalTrafficShapingHandler 对全局的channel 采取一致的 速度控制，
 *  并不是 总共的大小，进行控制。 GlobalChannelTrafficShapingHandler 最难理解， 它其是对GlobalTrafficShapingHandler做了优化，使得等待时间更加平衡.
 *
 * 重点难点：
 *  channelRead 是关键， 其作用就是在每次读到数据的时候计算其大小，然后判断是否超出限制，超出则等待一会(但不能超过最大值)，否则就直接读
 *  ReopenReadTimerTask 是重启读操作的定时任务。它在读的时候，如果需要等待则设置为channel 的属性，同时提交 该定时任务 到ctx.executor
 *  write 方法作用类似channelRead ，其作用就是在每次准备写数据的时候计算其大小，然后判断是否超出限制，超出则等待一会(但不能超过最大值)，否则就直接写.
 *
 *  更多细节理解，请Google
 *
 * 3、增强写功能
 *  FlushConsolidationHandler
 *  flush的条件有两个，一个是runsSinceFlush，还有一个是flushed的元素个数，两者有其一满足就可以flush，这样可以防止两种场景：
 * ①请求量很大，在run方法执行3次以前，flushed中已经积攒了太多的需要flush的内容，所以要设置flushed的阈值
 * ②请求量很小，flushed很久才会达到阈值，这样要等很久才能把之前积攒的消息flush出去，会有很大延时，所以要设置runWithNoWork的阈值
 *
 * 具体使用细节，可以根据实际业务调整。
 *
 *
 *
 *
 */