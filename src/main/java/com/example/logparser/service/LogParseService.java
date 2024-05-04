package com.example.logparser.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class LogParseService {
    private static final String VALID_STRING_REGEX = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3} - - \\[\\d{2}\\/\\d{2}\\/\\d{4}:\\d{2}:\\d{2}:\\d{2} \\+\\d{4}\\] \"[A-Z]+ .+ HTTP\\/1\\.1\" \\d+ \\d+ \\d+\\.\\d+ \"-\" \".+\" prio:\\d+";
    private static final String DATE_PARSE_REGEX = "\\d{2}/\\d{2}/\\d{4}:\\d{2}:\\d{2}:\\d{2}";
    private static final String LONG_RESPONSE_TIME_REGEX = "\\b(\\d+\\.\\d{6})\\b";
    private static final String ERROR_STATUS_REGEX = "\\b(5[0-9]{2})\\b";
    private final double maxResponseTme;
    private final DateFormat format;

    public LogParseService(double maxResponseTme) {
        this.maxResponseTme = maxResponseTme;
        this.format = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss");
    }

    public Date parseDate(String line) throws ParseException {
        var pattern = Pattern.compile(DATE_PARSE_REGEX);
        var matcher = pattern.matcher(line);

        if (matcher.find()) {
            return format.parse(matcher.group());
        }

        throw new RuntimeException("Can't parse date!");
    }

    public boolean hasErrorStatus(String line) {
        var pattern = Pattern.compile(ERROR_STATUS_REGEX);
        var matcher = pattern.matcher(line);
        return matcher.find();
    }

    public boolean hasLongResponseTime(String line) {
        var pattern = Pattern.compile(LONG_RESPONSE_TIME_REGEX);
        var matcher = pattern.matcher(line);
        return matcher.find() && Double.parseDouble(matcher.group()) > maxResponseTme;
    }

    public boolean isValidString(String line) {
        var pattern = Pattern.compile(VALID_STRING_REGEX);
        var matcher = pattern.matcher(line);
        return matcher.find();
    }
}
