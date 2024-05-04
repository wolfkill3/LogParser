package com.example.logparser;

import com.example.logparser.service.LogProcessService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

public class LogParser {

    public static void main(String[] args) {
        var argByKey = Arrays.stream(args)
                .map(arg -> arg.split("="))
                .collect(Collectors.toMap(parts -> parts[0], parts -> parts[1], (a, b) -> b, HashMap::new));
        var service = new LogProcessService(argByKey);
        service.process();
    }
}
