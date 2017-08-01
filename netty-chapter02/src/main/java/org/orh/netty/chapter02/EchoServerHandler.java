package org.orh.netty.chapter02;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.write(msg); // (1)
        ctx.flush(); // (2)
    }

    // 1. ChannelHandlerContext 对象提供了许多操作，使你能触发各种各样的I/O事件和操作。这里我们调用了 write 方法来逐字
    // 地把接收到的消息写入。注意不同于 DISCARD 的例子我们并没有释放接收到的消息，这是因为当写入的时候Netty已经帮我们释放了。
    
    // 2. ctx.write 方法不会使消息写入到通道上，他被缓冲在内部，你需要调用 ctx.flush 方法把缓冲区中的数据强行输出。或者你
    // 还可以使用更简洁的 ctx.writeAndFlush(msg)达到同样的目的。
}
