package org.orh.netty.chapter04;

public class UnixTime {

    private final long value;

    public UnixTime(long value) {
        this.value = value;
    }

    public UnixTime() {
        this(System.currentTimeMillis() / 1000L + 2208988800L);
    }

    public long value() {
        return value;
    }

    @Override
    public String toString() {
        return "UnixTime [value=" + value + "]";
    }
}
