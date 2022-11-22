package com.greplfa.web.global.helper.logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public interface Logger {
    @Around("@annotation(LogPoint)")
    Object log(ProceedingJoinPoint proceedingJoinPoint) throws Throwable;

    TraceStatus begin(String message);

    void end(TraceStatus status);

    void exception(TraceStatus status, Exception e);
}
