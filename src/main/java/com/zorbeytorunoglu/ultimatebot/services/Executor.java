package com.zorbeytorunoglu.ultimatebot.services;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Executor {

    private static ScheduledExecutorService scheduledExecutorService;

    public static void init() {
        scheduledExecutorService = Executors.newScheduledThreadPool(3);
    }
    
    public static void scheduleRepeatingTask(Runnable runnable, int delay, int period, TimeUnit timeUnit) {
        scheduledExecutorService.scheduleAtFixedRate(runnable,delay,period,timeUnit);
    }

    public static void scheduleDelayedTask(Runnable runnable, int delay, TimeUnit timeUnit) {
        scheduledExecutorService.schedule(runnable,delay,timeUnit);
    }

    public static void scheduleDelayedTask(Runnable runnable, long delay, TimeUnit timeUnit) {
        scheduledExecutorService.schedule(runnable,delay,timeUnit);
    }

    public static void scheduleRepeatingTask(Runnable runnable, int delay, int period) {
        scheduledExecutorService.scheduleAtFixedRate(runnable,delay,period,TimeUnit.MILLISECONDS);
    }

    public static void scheduleDelayedTask(Runnable runnable, int delay) {
        scheduledExecutorService.schedule(runnable,delay,TimeUnit.MILLISECONDS);
    }

    public static ScheduledExecutorService getScheduledExecutorService() {
        return scheduledExecutorService;
    }

}
