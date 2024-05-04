package com.example.logparser.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LogParseServiceTest {
    private final LogParseService service = new LogParseService(45);

    @ParameterizedTest
    @ValueSource(strings = {
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 500 2 44.510983 \"-\" \"@list-item-updater\" prio:0",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=7ae28555 HTTP/1.1\" 599 2 23.251219 \"-\" \"@list-item-updater\" prio:0",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=e356713 HTTP/1.1\" 503 2 30.164372 \"-\" \"@list-item-updater\" prio:0"
    })
    public void hasErrorStatusTest(String row) {
        Assertions.assertTrue(service.hasErrorStatus(row));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 44.510983 \"-\" \"@list-item-updater\" prio:0",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=7ae28555 HTTP/1.1\" 301 2 23.251219 \"-\" \"@list-item-updater\" prio:0",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=e356713 HTTP/1.1\" 403 2 30.164372 \"-\" \"@list-item-updater\" prio:0"
    })
    public void hasNotErrorStatusTest(String row) {
        Assertions.assertFalse(service.hasErrorStatus(row));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 74.510983 \"-\" \"@list-item-updater\" prio:0",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=7ae28555 HTTP/1.1\" 200 2 93.251219 \"-\" \"@list-item-updater\" prio:0",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=e356713 HTTP/1.1\" 200 2 50.164372 \"-\" \"@list-item-updater\" prio:0"
    })
    public void hasLongResponseTimeTest(String row) {
        Assertions.assertTrue(service.hasLongResponseTime(row));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 30.510983 \"-\" \"@list-item-updater\" prio:0",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=7ae28555 HTTP/1.1\" 200 2 15.251219 \"-\" \"@list-item-updater\" prio:0",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=e356713 HTTP/1.1\" 200 2 10.164372 \"-\" \"@list-item-updater\" prio:0"
    })
    public void hasNotLongResponseTimeTest(String row) {
        Assertions.assertFalse(service.hasLongResponseTime(row));
    }


    @ParameterizedTest
    @ValueSource(strings = {
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=6076537c HTTP/1.1\" 200 2 30.510983 \"-\" \"@list-item-updater\" prio:0",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=7ae28555 HTTP/1.1\" 200 2 15.251219 \"-\" \"@list-item-updater\" prio:0",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=e356713 HTTP/1.1\" 200 2 10.164372 \"-\" \"@list-item-updater\" prio:0"
    })
    public void isValidStringTest(String row) {
        Assertions.assertTrue(service.isValidString(row));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "192.168.32.181 - - [14/06/2017:16:47:02 +1000] \"PUT /rest/v1.4/documents?zone=default&_rid=e356713 HTTP/1.1\" 200 2  \"-\" \"@list-item-updater\" prio:0"
    })
    public void isNotValidStringTest(String row) {
        Assertions.assertFalse(service.isValidString(row));
    }
}