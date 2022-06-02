package com.example.service.controllers;

import com.example.service.Config;
import com.example.service.asyncCounter.AsyncCounter;
import com.example.service.cache.Cache;
import com.example.service.process.Duet;
import com.example.service.process.Solution;
import com.example.service.stats.Stats;
import com.example.service.stats.StatsProvider;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MainController {

    public static AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(Config.class);

    Solution solution;
    @Autowired
    public void setSolution(Solution solution) {
        this.solution = solution;
    }


    private StatsProvider statsProvider;
    @Autowired
    public void setStatsProvider(StatsProvider statsProvider) {
        this.statsProvider = statsProvider;
    }

    @GetMapping("/home")
    public ResponseEntity<String> GetAnswer(
            @RequestParam(value="number") String number,
                @RequestParam(value="system") Integer system){

        AsyncCounter.increase();

        if (number == null) {
            return new ResponseEntity<>("ERROR", HttpStatus.BAD_REQUEST);
        }

        var params = solution.Convert(number, system);
        return new ResponseEntity<>(params, HttpStatus.OK);
    }

    @GetMapping("/cache")
    public ResponseEntity<String> printCache() {
        return new ResponseEntity<>(Cache.getStaticCache(), HttpStatus.OK);
    }

    @PostMapping("/solve_json")
    public ResponseEntity<String> solveSingleJson(
            @RequestBody Duet params
    ) {
        System.out.println(params.getNumber());
        var parameters = solution.Convert(params.getNumber(), params.getSystem());
        return new ResponseEntity<>(parameters, HttpStatus.OK);
    }

    @PostMapping("/solve_bulk")
    public ResponseEntity<List<String>> solveBulkJson(
            @RequestBody @NotNull List<Duet> params
    ) {
        var list = new ArrayList<String>();

        for (Duet param : params) {
            list.add(solution.Convert(param.getNumber(), param.getSystem()));
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/stats")
    public Stats getStats() {
        return statsProvider.calculate();
    }

}