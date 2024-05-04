package com.example.logparser.service;

import com.example.logparser.states.LogInfoState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.logging.Logger;

class LogInfoStateTest {
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Test
    public void addTest() {
        var state = new LogInfoState(99.9, 100, logger);
        Assertions.assertDoesNotThrow(() -> state.add(new Date(), true));
        Assertions.assertDoesNotThrow(() -> state.add(new Date(), false));
    }

    @Test
    public void isNotFullTest() {
        var state = new LogInfoState(99.9, 1, logger);
        Assertions.assertTrue(state.isNotFull());
        state.add(new Date(), true);
        Assertions.assertFalse(state.isNotFull());
    }

    @Test
    public void writeTest() {
        var emptyState = new LogInfoState(99.9, 100, logger);
        Assertions.assertThrows(RuntimeException.class, emptyState::write);

        var notEmptyState = new LogInfoState(99.9, 100, logger);
        notEmptyState.add(new Date(), true);
        Assertions.assertDoesNotThrow(() -> notEmptyState.write());
    }
}