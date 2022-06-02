package com.example.service.stats;

import com.example.service.process.Duet;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

@Service
public class StatsProvider {

    private static List<Duet> duetList = new ArrayList<>();

    private static boolean shouldBeRecalculated = true;

    private Long totalRequests = 0L;
    private Long wrongRequests = 0L;

    private Duet min = new Duet("0", 0);

    private Duet max = new Duet("0", 0);

    private Duet mostCommon = new Duet("0", 0);


    public void increaseTotalRequests() {
        totalRequests++;
    }

    public void increaseWrongRequests() {
        wrongRequests++;
    }

    public Stats calculate() {
        System.out.println(duetList);

        if (!shouldBeRecalculated) {
            return null;
        }

        try {
            mostCommon = duetList
                    .stream()
                    .reduce(
                            BinaryOperator.maxBy(Comparator.comparingInt(o -> Collections.frequency(duetList, o)))
                    ).orElse(new Duet("0", 0));

            duetList = duetList
                    .stream()
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());

            min = duetList
                    .stream()
                    .min(Comparator.comparing(Duet::getNumber))
                    .orElse(new Duet("0", 0));

            max = duetList
                    .stream()
                    .max(Comparator.comparing(Duet::getNumber))
                    .orElse(new Duet("0", 0));

            shouldBeRecalculated = false;
        } catch (NullPointerException exception) {
            throw new RuntimeException(exception);
        }
        return new Stats(totalRequests,wrongRequests,min,max,mostCommon);
    }

    public void addRoot(@NotNull String number, Integer system) {
        Duet tmp = new Duet(number, system);
        duetList.add(tmp);
        shouldBeRecalculated = true;
    }
}