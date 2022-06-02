package com.example.service.process;

import com.example.service.Config;
import com.example.service.cache.Cache;
import com.example.service.controllers.MainController;
import com.example.service.stats.StatsProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class Solution {

    Logger logger = LoggerFactory.getLogger(Solution.class);

    Cache cache;
    @Autowired
    public void setCache(Cache cache) {
        this.cache = cache;
    }


    private StatsProvider statsProvider;

    @Autowired
    public void setStatsProvider(StatsProvider statsProvider) {
        this.statsProvider = statsProvider;
    }

    public boolean checkNumber(String number, Integer system) {


        return true;
    }

    public String Convert(String number, Integer system) {
        statsProvider.increaseTotalRequests();

        if (!checkNumber(number, system)) {
            statsProvider.increaseWrongRequests();
            throw new RuntimeException("Incorrect number!");
        }

        Duet test = new Duet(number, system);
        String tmp = cache.find(test);

        if (tmp == null) {
            logger.warn("Calculating output parameters");

            if (system == 2) {
                System.out.println(number);
                int decimal = Integer.parseInt(number,2);
                String dec = Integer.toBinaryString(decimal);
                cache.add(test, dec);
                statsProvider.addRoot(number, system);
                return Integer.toString(decimal);
            }

            if (system == 10) {
                int temp = Integer.parseInt(number);
                String bin = Integer.toBinaryString(temp);
                cache.add(test, bin);
                statsProvider.addRoot(number, system);
                return bin;
            }
        }

        return "NOWAY";
    }

}
