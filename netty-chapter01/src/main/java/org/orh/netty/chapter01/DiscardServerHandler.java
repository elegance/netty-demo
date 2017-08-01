package org.orh.netty.chapter01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 处理服务端 Channel
 *
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter { // (1)

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception { // (2)
        // 默默丢弃收到的数据 - 是不是类似 iptables 的 drop 呢
        System.out.println(msg);
        ((ByteBuf) msg).release(); // (3)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception { // (4)
        // 出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }

    // (1). DiscardServerHandler 继承了ChannelInboundHandlerAdapter，这个类实现了 ChannelInboundHandler 接口
    // ChannelInboundHandler 提供了许多事件处理的接口方法，然后你可以覆盖这些方法。现在仅仅只需要继承 ChannelInboundHandlerAdapter类
    // 而不是需要完整的去实现接口方法

    // (2). 这里我们覆盖了 channelRead 事件处理方法，每当客户端收到信的数据时，这个方法在收到消息时会被调用，这个例子中收到的消息类型是 ByteBuf

    // (3). 为了实现 DISCARD 协议，处理器忽略所有接受的消息。ByteBuf
    // 是一个引用计数对象，这个对象必须显示地调用release方法来释放。请记住处理器的职责是释放所有传递处理器的引用计数对象。
    // 通常，channelRead 方法代码是在 finally 中使用 ReferenceCountUtil.release(msg);

    // (4). exceptionCaught 是在出现 Throwable
    // 对象才被调用，即当Netty由于IO错误或处理器在处理事件抛出异常时。在大部分情况下，捕获的异常应该被记录下来并且把关联的 channel 给关闭掉。
    // 这个方法在遇到不同的异常情况下有不同的实现，比如你可能想在关闭连接前发送一个错误码的响应消息
    
    // 现在 DISCARD 服务已经实现了一半了，剩下编个 main 方法来启动 DiscardServerHandler 
}
