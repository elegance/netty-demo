package org.orh.netty.chapter03;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * https://waylau.com/netty-4-user-guide/Getting%20Started/Dealing%20with%20a%20Stream%20based%20Transport.html
 *
 */
public class TimeDecoder extends ByteToMessageDecoder { // (1)

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception { // (2)
        if (in.readableBytes() < 4) {
            return; // (3)
        }
        
        out.add(in.readBytes(4)); // (4)
    }
    
    // 1. ByteToMessageDecoder 是 ChannelInboundHandler 的一个实现类，他可以在处理数据拆分问题上变得很简单

    // 2. 每当有新的数据提交时，ByteToMessageDecoder 都会调用 decode 方法，这个方法处理内部的那个累积缓冲
    
    // 3. Decode() 可以决定当累积缓冲里没有足够的数据时可以往out对象里放任意数据。当有更多的数据被接收了，ByteToMessageDecoder会再一次调用 decode 方法
    
    // 4. 如果在 decode 方法中增加了一个对象到 out对象里，这意味着解码器解码消息成功。
    // ByteToMessageDecoder 将会丢弃在累积缓冲里已经被读过的数据。请记得你不需要对多条消息调用 decode, ByteToMessageDecoder 会持续调用decode 
    // 直到不放任何数据到 out 里。
    
    // 此外，Netty 提供了 更多开箱即用的解码器使你可以更简单的实现更多的协议：
    // * 对于二进制协议请看：http://netty.io/4.0/xref/io/netty/example/factorial/package-summary.html
    // * 对于基于文本协议请看：http://netty.io/4.0/xref/io/netty/example/telnet/package-summary.html
}
