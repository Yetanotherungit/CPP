package com.example.service.stats;

import com.example.service.process.Duet;

public class Stats {

    public Long getTotalRequests() {
        return totalRequests;
    }

    public Long getWrongRequests() {
        return wrongRequests;
    }

    public Duet getMin() {
        return min;
    }

    public Duet getMax() {
        return max;
    }

    public Duet getMostCommon() {
        return mostCommon;
    }

    private Long totalRequests = 0L;
    private Long wrongRequests = 0L;

    private Duet min = new Duet("0", 0);

    private Duet max = new Duet("0", 0);

    private Duet mostCommon = new Duet("0", 0);
    public Stats(Long total,
                 Long wrong,
                 Duet min,
                 Duet max,
                 Duet common) {
        totalRequests = total;
        wrongRequests = wrong;
        this.min = min;
        this.max = max;
        mostCommon = common;
    }
}

