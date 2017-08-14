package org.orh.netty.chapter06;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class WorldClockServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();
        p
            .addLast(new ProtobufVarint32FrameDecoder())
            .addLast(new ProtobufDecoder(WorldClockProtocol.Locations.getDefaultInstance()))

            .addLast(new ProtobufVarint32LengthFieldPrepender())
            .addLast(new ProtobufEncoder())
        
            .addLast(new WorldClockServerHandler());
    }
}
