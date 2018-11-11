package com.chris.box.net;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class HttpTask implements Runnable,Delayed {
    @Override
    public long getDelay(TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(Delayed o) {
        return 0;
    }

    @Override
    public void run() {

    }
}
