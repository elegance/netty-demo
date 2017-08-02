package org.orh.netty.chapter05;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class FactorialClient {
    
    public static final int COUNT = 1000; // 计算 1000 的阶乘

    public static void main(String[] args) throws InterruptedException {
        
        EventLoopGroup group = new NioEventLoopGroup();
        
        try {
            Bootstrap b =  new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline()
                        .addLast(new NumberEncoder())
                        .addLast(new BigIntegerDecoder())
                        .addLast(new FactorialClientHandler());
                }});

            // Make a new connection.
            ChannelFuture f = b.connect("127.0.0.1", 8080).sync();
            
            // Get the handler instance to retrieve the answer
            FactorialClientHandler handler = (FactorialClientHandler) f.channel().pipeline().last(); 
            
            // Print out the answer
            System.err.format("Factorial of %,d is: %,d", COUNT, handler.getFactorial());

        } finally {
            group.shutdownGracefully();
        }
    }
}
