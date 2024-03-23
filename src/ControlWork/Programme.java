package ControlWork;

public class Programme {
    String channel;
    BroadcastsTime time;
    String name;

    public Programme(String channel, BroadcastsTime time, String name) {
        this.channel = channel;
        this.time = time;
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " (" + channel + ") " + time;
    }
}
