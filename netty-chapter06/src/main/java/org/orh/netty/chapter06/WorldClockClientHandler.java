package org.orh.netty.chapter06;

import java.util.Collection;
import java.util.Formatter;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.orh.netty.chapter06.WorldClockProtocol.Continent;
import org.orh.netty.chapter06.WorldClockProtocol.LocalTimes;
import org.orh.netty.chapter06.WorldClockProtocol.Location;
import org.orh.netty.chapter06.WorldClockProtocol.Locations;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class WorldClockClientHandler extends SimpleChannelInboundHandler<LocalTimes> {

    private static final Pattern DELIM = Pattern.compile("/");

    // Stateful properties
    private volatile Channel channel;
    private final LinkedBlockingQueue<LocalTimes> answer = new LinkedBlockingQueue<>();

    public WorldClockClientHandler() {
        super(false);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        channel = ctx.channel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LocalTimes msg) throws Exception {
        answer.add(msg);
    }

    public List<String> getLocalTimes(Collection<String> cities) {
        Locations.Builder builder = Locations.newBuilder();

        for (String c : cities) {
            String[] components = DELIM.split(c);
            builder.addLocation(Location.newBuilder()
                    .setContinent(Continent.valueOf(components[0].toUpperCase()))
                    .setCity(components[1])
                    .build());
        }
        
        channel.writeAndFlush(builder.build()); // send request
        
        LocalTimes localTimes;
        boolean interrupted = false;
        
        for(;;) {
            try {
                localTimes = answer.take();
                break;
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
        
        if (interrupted) {
            Thread.currentThread().interrupt();
        }
        
        return localTimes.getLocalTimeList().stream().map(lt -> {
            return new Formatter().format(
                    "%4d-%02d-%02d %02d:%02d:%02d %s",
                    lt.getYear(),
                    lt.getMonth(),
                    lt.getDayOfMonth(),
                    lt.getHour(),
                    lt.getMinute(),
                    lt.getSecond(),
                    lt.getDayOfWeek().name()).toString(); 
        }).collect(Collectors.toList());
    }
    
    
}
