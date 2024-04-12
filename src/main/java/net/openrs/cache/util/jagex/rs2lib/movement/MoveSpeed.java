package net.openrs.cache.util.jagex.rs2lib.movement;


import net.openrs.cache.util.jagex.core.constants.SerialEnum;

public enum MoveSpeed implements SerialEnum {
   CRAWL((byte)0),
    WALK((byte)1),
    RUN((byte)2),
    STATIONARY((byte)-1);

    public byte speed;

    MoveSpeed(byte arg0) {
        this.speed = arg0;
    }

    public int serial_id() {
        return this.speed;
    }
}
