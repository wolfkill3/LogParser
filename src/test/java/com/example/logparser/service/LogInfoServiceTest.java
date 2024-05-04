package com.example.logparser.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class LogInfoServiceTest {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Test
    public void addTest() {
        var service = new LogInfoService(99.9, 100, logger);
        Assertions.assertDoesNotThrow(() -> service.add(new Date(), true));
        Assertions.assertDoesNotThrow(() -> service.add(new Date(), false));
    }

    @Test
    public void isNotFullTest() {
        var service = new LogInfoService(99.9, 1, logger);
        Assertions.assertTrue(service.isNotFull());
        service.add(new Date(), true);
        Assertions.assertFalse(service.isNotFull());
    }

    @Test
    public void writeTest() {
        var emptyService = new LogInfoService(99.9, 100, logger);
        Assertions.assertThrows(RuntimeException.class, emptyService::write);

        var notEmptyService = new LogInfoService(99.9, 100, logger);
        notEmptyService.add(new Date(), true);
        Assertions.assertDoesNotThrow(() -> notEmptyService.write());
    }
}