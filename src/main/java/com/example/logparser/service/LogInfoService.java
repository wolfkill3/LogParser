package com.example.logparser.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogInfoService {
    private final double minAvailability;
    private final Logger logger;
    private final int maxLoad;
    private int totalAvailability = 0;
    private int currentLoad = 0;
    private Date start;
    private Date end;

    public LogInfoService(double minAvailability, int maxLoad, Logger logger) {
        this.minAvailability = minAvailability;
        this.maxLoad = maxLoad;
        this.logger = logger;
    }

    public void add(Date current, Boolean isValid) {
        if (currentLoad < maxLoad) {
            if (this.start == null) {
                this.start = current;
            }

            totalAvailability += isValid ? 1 : 0;
            currentLoad++;
            this.end = current;
        } else {
            throw new RuntimeException("Stash is overloaded!");
        }
    }

    public boolean isNotFull() {
        return currentLoad < maxLoad;
    }

    public void write(Date end, Boolean isValid) {
        write();
        add(end, isValid);
    }

    public void write() {
        if (currentLoad > 0) {
            var logString = getLogInfo();

            if (logString.availability() < minAvailability) {
                logger.log(Level.INFO, "Add row: " + logString);
            }

            clear();
        } else {
            throw new RuntimeException("Stash is empty!");
        }
    }

    private double getBucketAvailability() {
        return ((double) totalAvailability / currentLoad) * 100.;
    }


    private LogInfo getLogInfo() {
        return new LogInfo(start, end, getBucketAvailability());
    }

    private void clear() {
        totalAvailability = 0;
        currentLoad = 0;
        start = null;
        end = null;
    }

    public record LogInfo(Date start, Date end, double availability) {
        public String toString() {
            var format = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss");
            return String.format("[%s]        [%s]        %.1f", format.format(start), format.format(end), availability);
        }
    }
}
