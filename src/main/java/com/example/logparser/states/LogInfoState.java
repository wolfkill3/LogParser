package com.example.logparser.states;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogInfoState {
    private final double minAvailability;
    private final DateFormat format;
    private final Logger logger;
    private final int maxLoad;
    private int totalAvailability = 0;
    private int currentLoad = 0;
    private Date start;
    private Date end;

    public LogInfoState(double minAvailability, int maxLoad, Logger logger) {
        this.minAvailability = minAvailability;
        this.maxLoad = maxLoad;
        this.logger = logger;
        this.format = new SimpleDateFormat("dd/MM/yyyy:hh:mm:ss");
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
            var availability = getBucketAvailability();

            if (availability < minAvailability) {
                logger.log(Level.INFO, String.format("[%s]        [%s]        %.1f", format.format(start), format.format(end), availability));
            }

            clear();
        } else {
            throw new RuntimeException("Stash is empty!");
        }
    }

    private double getBucketAvailability() {
        return ((double) totalAvailability / currentLoad) * 100.;
    }

    private void clear() {
        totalAvailability = 0;
        currentLoad = 0;
        start = null;
        end = null;
    }
}
