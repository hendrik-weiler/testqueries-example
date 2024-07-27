package com.hendrikweiler.testqueries;

public class TimerEvent {

    private final String info;

    public TimerEvent(String info) {
        System.out.println(info);
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}
