package com.hendrikweiler.testqueries;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timer;

import java.util.logging.Logger;

@Startup
@Singleton
public class ScheduleTimerBean {

    private final static Logger logger = Logger.getLogger(ScheduleTimerBean.class.getName());

    @Schedule(hour = "*", minute = "*", second = "*/5", info = "Every 5 seconds timer")
    public void automaticallyScheduled(Timer timer) {
        //logger.info("Timer event: " + timer.getInfo());

    }
}