package ControlWork;

class BroadcastsTime implements Comparable<BroadcastsTime> {
    private byte hour;
    private byte minutes;

    public BroadcastsTime(String s) {
        String[] a = s.split(":");
        this.hour = (byte) Integer.parseInt(a[0]);
        this.minutes = (byte) Integer.parseInt(a[1]);
    }

    public byte getHour() {
        return hour;
    }

    public byte getMinutes() {
        return minutes;
    }

    public boolean after(BroadcastsTime t) {
        if (hour > t.getHour()) {
            return true;
        } else if (hour == t.getHour() && minutes > t.getMinutes()) {
            return true;
        }
        return false;
    }

    public boolean before(BroadcastsTime t) {
        if (hour < t.getHour()) {
            return true;
        } else if (hour == t.getHour() && minutes < t.getMinutes()) {
            return true;
        }
        return false;
    }

    public boolean between(BroadcastsTime t1, BroadcastsTime t2) {
        return this.after(t1) && this.before(t2);
    }

    @Override
    public int compareTo(BroadcastsTime t) {
        if (hour != t.getHour()) {
            return hour - t.getHour();
        } else {
            return minutes - t.getMinutes();
        }
    }

    @Override
    public String toString() {
        return hour + ":" + minutes;
    }


    public boolean equals(BroadcastsTime obj) {
        return this.hour == obj.hour && this.minutes == obj.minutes;
    }
}