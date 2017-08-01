package org.orh.netty.chapter03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class TimeServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception { // (1)
        final ByteBuf time = ctx.alloc().buffer(4); // 4个字节，32位 (2)
        time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));

        final ChannelFuture f = ctx.writeAndFlush(time); // (3)
        f.addListener(new ChannelFutureListener() {

            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                assert f == future;
                ctx.close();
            }

        }); // (4)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    // 1. channelActive 在连接建立且准备通信时被调用。
    
    // 2. 为了发一个新的消息，我们需要分配一个包含这个消息的新的缓冲。因为写入的是一位32位的整数，因此至少需要一个4字节的 ByteBuf.
    
    // 3. 写入内部缓冲，并flush到通道
    
    // 4. 当一个写请求完成了通知我们，只需要在 ChannelFuture上增加一个ChannelFutureListener，这里我们构建了一个匿名的 ChannelFuture类来
    // 关闭 Channel，或者你可以使用简单的预定义监听器代码：
    // f.addListener(ChannelFutureListener.CLOSE);
}

