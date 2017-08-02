package org.orh.netty.chapter04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

public class TimeEncoder extends ChannelOutboundHandlerAdapter {
    
    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        UnixTime m = (UnixTime) msg;
        ByteBuf encoded = ctx.alloc().buffer(4);
        encoded.writeInt((int) m.value());
        ctx.write(encoded, promise); // (1)
    }
    
    // 第一，通过 ChannelPromise ，当编码后的数据被写到了通道上，Netty 可以通过这个对象标记是成功还是失败。
    // 第二，我们不需要调用ctx.flush() ，因为处理器已经单独分离出一个方法 void flush，如果想自己实现 flush 方法，可以覆盖这个方法

    // 进一步简化的操作，还可以使用 MessageToByteEncode: TimeEncoder extends MessageToByteEncoder<UnixTime>
}
