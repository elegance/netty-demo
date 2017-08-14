package org.orh.netty.chapter06;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_WEEK;
import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.SECOND;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

import java.util.Calendar;
import java.util.TimeZone;

import org.orh.netty.chapter06.WorldClockProtocol.Continent;
import org.orh.netty.chapter06.WorldClockProtocol.DayOfWeek;
import org.orh.netty.chapter06.WorldClockProtocol.LocalTime;
import org.orh.netty.chapter06.WorldClockProtocol.LocalTimes;
import org.orh.netty.chapter06.WorldClockProtocol.Location;
import org.orh.netty.chapter06.WorldClockProtocol.Locations;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class WorldClockServerHandler extends SimpleChannelInboundHandler<Locations> {
    
    /**
     * 读取client 消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Locations locations) throws Exception {
        System.out.println("server: I'm got locations!");
        locations.getLocationList().forEach(l -> {
            System.out.println(l.getContinent().getNumber() + " " + l.getCity());
        });
        long currentTime = System.currentTimeMillis();
        
        LocalTimes.Builder builder =  LocalTimes.newBuilder();
        
        for (Location l : locations.getLocationList()) {
            TimeZone tz = TimeZone.getTimeZone(
                    toString(l.getContinent()) + '/' + l.getCity());
            Calendar calendar = getInstance(tz);
            calendar.setTimeInMillis(currentTime);
            
            builder.addLocalTime(LocalTime.newBuilder()
                    .setYear(calendar.get(YEAR))
                    .setMonth(calendar.get(MONTH) + 1)
                    .setDayOfMonth(calendar.get(DAY_OF_MONTH))
                    .setDayOfWeek(DayOfWeek.valueOf(calendar.get(DAY_OF_WEEK)))
                    .setHour(calendar.get(HOUR_OF_DAY))
                    .setMinute(calendar.get(MINUTE))
                    .setSecond(calendar.get(SECOND)))
                    .build();
        }
        ctx.write(builder.build());
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    private static String toString(Continent c) {
        return c.name().charAt(0) + c.name().toLowerCase().substring(1);
    }

}
