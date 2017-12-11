package com.even.util;

public class ThreadSleepUtil {
    private static final long PERIOD_TIME = 3 * 1000;           //间隔时间

    public static void threadSleep(Thread thread) {
        try {
            thread.sleep(PERIOD_TIME);
        } catch (Exception e) {
            System.out.println("Error：线程休眠出错");
        }
    }
}
