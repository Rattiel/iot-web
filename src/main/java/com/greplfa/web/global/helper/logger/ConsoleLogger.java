package com.greplfa.web.global.helper.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class ConsoleLogger implements Logger {
    private static final String START_PREFIX = "-->";

    private static final String COMPLETE_PREFIX = "<--";

    private static final String EXCEPTION_PREFIX = "<X-";

    private final ThreadLocal<Trace> traceHolder = new ThreadLocal<>();

    @Override
    public Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String methodName = proceedingJoinPoint.getSignature().getName();
        String className = proceedingJoinPoint.getTarget().getClass().getSimpleName();

        TraceStatus status = begin(className + "." + methodName);

        Object object = null;
        Exception exception = null;

        try {
            object = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            exception = e;
        }

        if (exception != null) {
            exception(status, exception);

            throw exception;
        } else {
            end(status);
        }

        return object;
    }

    @Override
    public TraceStatus begin(String message) {
        syncTrace();
        Trace trace = traceHolder.get();
        Long startTimeMs = System.currentTimeMillis();
        log.info("[{}] {}{}()", trace.getId(), addSpace(START_PREFIX, trace.getLevel()), message);
        return new TraceStatus(trace, startTimeMs, message);
    }

    private void syncTrace() {
        Trace trace = traceHolder.get();
        if (trace == null) {
            traceHolder.set(new Trace());
        } else {
            traceHolder.set(trace.createNextTrace());
        }
    }

    @Override
    public void end(TraceStatus status) {
        complete(status, null);
    }

    @Override
    public void exception(TraceStatus status, Exception e) {
        complete(status, e);
    }

    private void complete(TraceStatus status, Exception e) {
        Long stopTimeMs = System.currentTimeMillis();
        long resultTimeMs = stopTimeMs - status.startTimeMs();
        Trace trace = status.trace();
        if (e == null) {
            log.info("[{}] {}{}() time={}ms", trace.getId(), addSpace(COMPLETE_PREFIX, trace.getLevel()), status.message(), resultTimeMs);
        } else {
            log.info("[{}] {}{}() time={}ms exception={}", trace.getId(), addSpace(EXCEPTION_PREFIX, trace.getLevel()), status.message(), resultTimeMs, e.getClass().getSimpleName());
        }

        releaseTrace();
    }

    private void releaseTrace() {
        Trace trace = traceHolder.get();

        if (trace.isFirstLevel()) {
            traceHolder.set(null);
        } else {
            traceHolder.set(trace.createPreviousTrace());
        }
    }

    private String addSpace(String prefix, int level) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < level; i++) {
            stringBuilder.append(i == level - 1 ? "|" + prefix : "|    ");
        }

        return stringBuilder.toString();
    }
}
