package com.example.logparser.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Map;
import java.util.logging.Logger;

public class LogProcessService {
    private static final String MAX_RESPONSE_TIME_KEY = "t";
    private static final String AVAILABILITY_KEY = "u";
    private static final String MAX_LOAD_KEY = "l";
    private static final Integer DEFAULT_MAX_LOAD = 100;
    private final LogParseService parseService;
    private final LogInfoService logInfoService;
    private final Logger logger;

    public LogProcessService(Map<String, String> args) {
        this.logger = Logger.getLogger(this.getClass().getName());
        this.parseService = new LogParseService(Double.parseDouble(args.get(MAX_RESPONSE_TIME_KEY)));
        this.logInfoService = new LogInfoService(
                Double.parseDouble(args.get(AVAILABILITY_KEY)),
                Integer.parseInt(args.getOrDefault(MAX_LOAD_KEY, DEFAULT_MAX_LOAD.toString())),
                logger
        );
    }

    public void process() {
        try (var reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            logger.warning("Error reading log file: " + e.getMessage());
        } catch (ParseException e) {
            logger.warning(e.getMessage());
        }

        logInfoService.write();
    }

    private void processLine(String line) throws ParseException {
        if (!parseService.isValidString(line)) {
            return;
        }

        var isValid = !parseService.hasLongResponseTime(line) && !parseService.hasErrorStatus(line);
        var current = parseService.parseDate(line);

        if (logInfoService.isNotFull()) {
            logInfoService.add(current, isValid);
        } else {
            logInfoService.write(current, isValid);
        }
    }
}
