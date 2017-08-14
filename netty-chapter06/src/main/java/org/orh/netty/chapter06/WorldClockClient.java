package org.orh.netty.chapter06;

import java.util.Arrays;
import java.util.List;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class WorldClockClient {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new WorldClockClientInitializer());

            // Make a new connection
            Channel ch = b.connect("127.0.0.1", 8080).sync().channel();

            // Get the handler instance to initiate the request
            WorldClockClientHandler handler = ch.pipeline().get(WorldClockClientHandler.class);

            // Request and get the response
            List<String> cities = Arrays.asList("Asia/Seoul,Europe/Berlin,America/Los_Angeles".split(","));
            List<String> response = handler.getLocalTimes(cities);

            // Close the connection
            ch.close();

            // Print the response at last but not least
            for (int i = 0; i < cities.size(); i++) {
                System.out.format("%28s: %s%n", cities.get(i), response.get(i));
            }

        } finally {
            group.shutdownGracefully();
        }
    }
}
