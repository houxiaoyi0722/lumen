package com.sang.common.core;

import org.slf4j.MDC;

public class TraceIdRunnable implements Runnable {
    private final Runnable delegate;
    private final String traceId;

    public TraceIdRunnable(Runnable delegate, String traceId) {
        this.delegate = delegate;
        this.traceId = traceId;
    }

    public void run() {
        MDC.put("traceId", this.traceId);

        try {
            this.delegate.run();
        } finally {
            MDC.remove("traceId");
            MDC.remove("executionTime");
        }

    }
}
