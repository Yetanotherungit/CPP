package com.example.service.cache;

import com.example.service.process.Duet;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class Cache {

    static public Map<Duet, String> cache = new HashMap<>();

    public void add(Duet input, String result) {
        if (!cache.containsKey(input)) {
            cache.put(input, result);
        }
    }

    public void printMap() {
        System.out.println(cache);
    }

    public @Nullable String find(Duet input) {
        if (cache.containsKey(input)) {
            return cache.get(input);
        }
        return null;
    }


    public static String getStaticCache() {
        return ("Cache:\n" + cache);
    }

}
