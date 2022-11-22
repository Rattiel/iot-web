package com.greplfa.web.global.helper.logger;

public record TraceStatus(
        Trace trace,
        Long startTimeMs,
        String message) {
}
