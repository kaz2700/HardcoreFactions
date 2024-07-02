package me.kazuto.hcf.Timers;

import lombok.Getter;

public class Timer {
    @Getter
    private Double maxTime;

    public Timer (Double maxTime) {
        this.maxTime = maxTime;
    }

}
