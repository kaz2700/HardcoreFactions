package me.kazuto.hcf.Timers;

import lombok.Getter;

public class Timer {
    @Getter
    private Double maxTime;

    @Getter
    private Runnable endTask;

    public Timer (Double maxTime) {
        this.maxTime = maxTime;
    }

    public Timer (Double maxTime, Runnable endTask) {
        this.maxTime = maxTime;
        this.endTask = endTask;
    }
}
