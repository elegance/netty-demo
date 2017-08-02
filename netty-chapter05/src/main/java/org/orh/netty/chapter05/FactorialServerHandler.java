package org.orh.netty.chapter05;

import java.math.BigInteger;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class FactorialServerHandler extends SimpleChannelInboundHandler<BigInteger> {
    private BigInteger lastMultiplier = new BigInteger("1");  // 乘数，  额。。 这个好像可以优化省略掉
    private BigInteger factorial = new BigInteger("1"); // 客户端实例变量，之前的阶乘结果

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BigInteger msg) throws Exception {
        lastMultiplier = msg;
        factorial = factorial.multiply(lastMultiplier);
        ctx.writeAndFlush(factorial);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.printf("Factorial of %,d is: %d,%n", lastMultiplier, factorial);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
