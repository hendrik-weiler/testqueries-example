package com.hendrikweiler.testqueries;

import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Startup
@Singleton
public class ScheduleTimerBean {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleTimerBean.class);

    @Schedule(hour = "*", minute = "*", second = "*/5", info = "Every 5 seconds timer")
    public void automaticallyScheduled(Timer timer) {
        logger.info("Timer event: " + timer.getInfo());
    }
}