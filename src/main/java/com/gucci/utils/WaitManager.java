package com.gucci.utils;

import java.util.concurrent.TimeUnit;

public class WaitManager {

    public static void pauseInSeconds(Integer seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
